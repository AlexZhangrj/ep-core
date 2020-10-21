package com.zhrenjie04.alex.manager.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zhrenjie04.alex.core.AbstractGenericService;
import com.zhrenjie04.alex.core.JsonResult;
import com.zhrenjie04.alex.core.User;
import com.zhrenjie04.alex.core.exception.CrisisError;
import com.zhrenjie04.alex.manager.dao.PrivilegeDao;
import com.zhrenjie04.alex.manager.dao.RoleDao;
import com.zhrenjie04.alex.manager.dao.RolePrivilegeDao;
import com.zhrenjie04.alex.manager.domain.Privilege;
import com.zhrenjie04.alex.manager.domain.Role;
import com.zhrenjie04.alex.manager.domain.RolePrivilege;
import com.zhrenjie04.alex.util.IdGenerator2;

/**
 * @author 张人杰
 */
@Service("privilegeService")
public class PrivilegeServiceImpl extends AbstractGenericService<Privilege, PrivilegeDao> implements PrivilegeService {

	@Autowired
	PrivilegeDao privilegeDao;
	@Autowired
	RoleDao roleDao;
	@Autowired
	RolePrivilegeDao rolePrivilegeDao;
	
	@Override
	protected PrivilegeDao getDao() {
		return privilegeDao;
	}

	/**
	 * 获取下属机构树
	 * 
	 * @param privilegeId
	 * @return
	 */
	@Override
	public JsonResult trees(User user) {
		HashMap<String, Object> params = new HashMap<String, Object>(16);
		params.put("orderBySortValueAsc", true);
		List<Privilege> privileges = privilegeDao.queryAll(params);

		List<Privilege> trees = new LinkedList<Privilege>();
		HashMap<String, Privilege> privilegeMap = new HashMap<String, Privilege>(16);
		if (privileges != null) {
			boolean hasFound = false;
			Iterator<Privilege> it = privileges.iterator();
			while (it.hasNext()) {
				Privilege privilege = it.next();
				if (privilege.getParentId().equals("0")) {
					// 根节点
					Privilege tree = new Privilege();
					tree.setPrivilegeId(privilege.getPrivilegeId());
					tree.setPrivilegeName(privilege.getPrivilegeName());
					tree.setPrivilegeCode(privilege.getPrivilegeCode());
					privilegeMap.put(privilege.getPrivilegeId(), tree);
					hasFound = true;
					it.remove();
					trees.add(tree);
				}
			}
			if (hasFound == false) {
				return JsonResult.failure(JsonResult.CODE_INTERNAL_SERVER_ERROR, "根节点不存在");
			}
			while (hasFound) {
				// 存在几层，扫几遍
				hasFound = false;
				it = privileges.iterator();
				while (it.hasNext()) {
					Privilege privilege = it.next();
					if (privilegeMap.get(privilege.getParentId()) != null) {
						hasFound = true;
						if (privilegeMap.get(privilege.getParentId()).getChildren() == null) {
							privilegeMap.get(privilege.getParentId()).setChildren(new LinkedList<Privilege>());
						}
						privilegeMap.get(privilege.getParentId()).getChildren().add(privilege);
						privilegeMap.put(privilege.getPrivilegeId(), privilege);
						it.remove();
					}
				}
			}
		}
		JsonResult result = JsonResult.success();
		result.put("trees", trees);
		return result;
	}

	@Override
	public JsonResult pageQueryAll(HashMap<String, Object> params, User sessionUser) {
		if ("-1".equals(params.get("parentId")) || params.get("parentId") == null
				|| "".equals(params.get("parentId"))) {
			params.put("parentId", "0");
		}
		Long total = getDao().count(params);
		Long pageNo = (Long) params.get("pageNo");
		Long pageSize = (Long) params.get("pageSize");
		params.put("start", (pageNo - 1) * pageSize);
		params.put("length", pageSize);
		List<Privilege> list = getDao().pageQueryAll(params);
		JsonResult result = JsonResult.success();
		result.put("total", total);
		result.put("list", list);
		result.put("pageNo", pageNo);
		result.put("pageSize", pageSize);
		result.put("orderBy", params.get("orderByFrontend"));
		result.put("orderType", params.get("orderType"));
		if (pageSize != null) {
			result.put("totalPages", (total / pageSize) + (total % pageSize > 0 ? 1 : 0));
		}
		return result;
	}

	@Override
	public JsonResult path(String privilegeId, User user) {
		HashMap<String, Object> params = new HashMap<String, Object>(16);
		params.put("privilegeId", privilegeId);
		Privilege privilege = privilegeDao.queryObject(params);
		String idPath = privilege.getIdPath();
		JsonResult result = JsonResult.success();
		result.put("privilegeName", privilege.getPrivilegeName());
		String[] ids = idPath.split("\\/");
		params.clear();
		params.put("ids", ids);
		params.put("orderByIdPathAsc", true);
		List<Privilege> privilegePath = privilegeDao.queryAll(params);
		result.put("privilegePath", privilegePath);
		return result;
	}

	@CacheEvict(value="users", allEntries=true)
	@Override
	public JsonResult insertObject(Privilege object, User sessionUser) {
		if (object.getPrivilegeName() == null || "".equals(object.getPrivilegeName())) {
			return JsonResult.failure(JsonResult.CODE_REQUEST_INPUT_ERROR, "请输入权限项名称");
		}
		object.setPrivilegeId(IdGenerator2.nextIdBase52String());
		if("-1".equals(object.getParentId())) {
			object.setParentId("0");
			object.setIdPath("/" + object.getPrivilegeId() + "/");
		}else {
			HashMap<String, Object> params = new HashMap<String, Object>(16);
			params.put("privilegeId", object.getParentId());
			Privilege parentPrivilege = privilegeDao.queryObject(params);
			String idPath = parentPrivilege.getIdPath();
			object.setIdPath(idPath + object.getPrivilegeId() + "/");
		}
		object.setCreaterId(sessionUser.getUserId());
		object.setCreaterName(sessionUser.getRealname());
		privilegeDao.insertObject(object);
		JsonResult result = JsonResult.success();
		result.put("message", "添加成功");
		return result;
	}

	/**
	 * 级联删除
	 */
	@CacheEvict(value="users", allEntries=true)
	@Override
	public JsonResult deleteAll(HashMap<String, Object> params, User sessionUser) {
		List<Privilege>needDeletePrivileges=getDao().queryAll(params);
		List<String>idPaths=new LinkedList<String>();
		for(Privilege needDeletePrivilege:needDeletePrivileges) {
			idPaths.add(needDeletePrivilege.getIdPath());
		}
		params.put("idPaths", idPaths);
		params.put("lastModifierId", sessionUser.getUserId());
		params.put("lastModifierName", sessionUser.getRealname());
		Long count = getDao().deleteAll(params);
		if (count > 0) {
			JsonResult result = JsonResult.success("操作成功！");
			return result;
		} else {
			JsonResult result = JsonResult.failure(JsonResult.CODE_PREREQUISITE_NOT_SATISFIED, "没有删除任何数据！");
			return result;
		}
	}
	/**
	 * 修改不移动，移动不修改
	 */
	@CacheEvict(value="users", allEntries=true)
	@Override
	public JsonResult updateObject(Privilege object, User sessionUser) {
		if (object.getPrivilegeName() == null || "".equals(object.getPrivilegeName())) {
			return JsonResult.failure(JsonResult.CODE_REQUEST_INPUT_ERROR, "请输入组织机构名称");
		}
		String idPath = "";
		if("0".equals(object.getParentId())||"-1".equals(object.getParentId())) {
			object.setParentId("0");
			idPath="/";
		}else {
			HashMap<String, Object> params = new HashMap<String, Object>(16);
			params.put("privilegeId", object.getParentId());
			Privilege parentPrivilege = privilegeDao.queryObject(params);
			idPath = parentPrivilege.getIdPath();
		}
		object.setIdPath(idPath + object.getPrivilegeId() + "/");
		object.setLastModifierId(sessionUser.getUserId());
		object.setLastModifierName(sessionUser.getRealname());
		privilegeDao.updateObject(object);
		JsonResult result = JsonResult.success();
		result.put("message", "修改成功");
		return result;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 60, rollbackFor = Exception.class)
	public JsonResult moveTo(String privilegeId, LinkedList<String> privilegeIds, User sessionUser) {
		HashMap<String, Object> params = new HashMap<String, Object>(16);
		params.put("privilegeId", privilegeId);
		Privilege targetPrivilege = privilegeDao.queryObject(params);
		String targetPrivilegeIdPath = targetPrivilege.getIdPath();
		params.clear();
		if (privilegeIds == null) {
			return JsonResult.failure(JsonResult.CODE_REQUEST_INPUT_ERROR, "没有请求移动任何权限项");
		}
		params.put("ids", privilegeIds);
		List<Privilege> privileges = privilegeDao.queryAll(params);
		// 判断目标角色是否不属于被移动对象的父节点的下属节点
		boolean existLoop = false;
		for (Privilege privilege : privileges) {
			if (targetPrivilege.getIdPath().startsWith(privilege.getIdPath())) {
				existLoop = true;
				break;
			}
		}
		if (existLoop) {
			return JsonResult.failure(JsonResult.CODE_REQUEST_INPUT_ERROR, "存在死循环");
		}
		// 移动被移动对象
		for (Privilege privilege : privileges) {
			HashMap<String, Object> subNodesParams = new HashMap<String, Object>(16);
			subNodesParams.put("oldIdPathStart", privilege.getIdPath());
			subNodesParams.put("newIdPathStart", targetPrivilegeIdPath + privilege.getPrivilegeId() + "/");
			privilegeDao.updateMovingSubNodes(subNodesParams);
			privilege.setIdPath(targetPrivilegeIdPath + privilege.getPrivilegeId() + "/");
			privilege.setParentId(targetPrivilege.getPrivilegeId());
			privilegeDao.updateMoving(privilege);
		}
		return JsonResult.success("移动成功");
	}

	@CacheEvict(value="users", allEntries=true)
	@Override
	public JsonResult lock(Privilege object, User sessionUser) {
		HashMap<String, Object> params = new HashMap<String, Object>(16);
		params.put("privilegeId", object.getPrivilegeId());
		object.setIsLocked(true);
		Long count = privilegeDao.updateLocked(object);
		if (count > 0) {
			return JsonResult.success("锁定成功");
		} else {
			return JsonResult.failure(JsonResult.CODE_PREREQUISITE_NOT_SATISFIED, "锁定失败，输入参数不合法");
		}
	}

	@CacheEvict(value="users", allEntries=true)
	@Override
	public JsonResult unlock(Privilege object, User sessionUser) {
		HashMap<String, Object> params = new HashMap<String, Object>(16);
		params.put("privilegeId", object.getPrivilegeId());
		object.setIsLocked(false);
		Long count = privilegeDao.updateLocked(object);
		if (count > 0) {
			return JsonResult.success("解锁成功");
		} else {
			return JsonResult.failure(JsonResult.CODE_PREREQUISITE_NOT_SATISFIED, "解锁失败，输入参数不合法");
		}
	}
	@Override
	public JsonResult loadRoleOriginalPrivileges(String roleId, User sessionUser) {
		HashMap<String, Object> params = new HashMap<String, Object>(16);
		params.put("roleId", roleId);
		Role role = roleDao.queryObject(params);
		List<String>ids=sessionUser.getCurrentRoleIds();
		params.clear();
		params.put("ids", ids);
		List<Role>roles=roleDao.queryAll(params);
		boolean found=false;
		for(Role userRole:roles) {
			if(role.getIdPath().startsWith(userRole.getIdPath())||"0".equals(userRole.getRoleId())) {
				found=true;
				break;
			}
		}
		if(found) {
			params.clear();
			params.put("roleId", roleId);
			List<Privilege>privileges = privilegeDao.queryAll(params);
			JsonResult result=JsonResult.success();
			result.put("privileges", privileges);
			return result;
		}else {
			throw new CrisisError("您无权读取非自己管理的角色配置");
		}
	}
	@Override
	public JsonResult loadCurrentUserJobPrivileges(User sessionUser) {
		HashMap<String, Object> params = new HashMap<String, Object>(16);
		params.put("userId", sessionUser.getUserId());
		params.put("jobId", sessionUser.getCurrentJobId());
		List<Privilege>privileges = privilegeDao.queryAll(params);
		JsonResult result=JsonResult.success();
		result.put("privileges", privileges);
		return result;
	}
	@CacheEvict(value="users", allEntries=true)
	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 60, rollbackFor = Exception.class)
	public JsonResult configRolePrivileges(String roleId, List<String> privilegeIds, User sessionUser) {
		HashMap<String, Object> params = new HashMap<String, Object>(16);
		params.put("roleId", roleId);
		Role role = roleDao.queryObject(params);
		List<String>ids=sessionUser.getCurrentRoleIds();
		if(ids.contains(roleId)) {
			throw new CrisisError("您无权修改自己的角色配置");
		}
		params.clear();
		params.put("ids", ids);
		List<Role>roles=roleDao.queryAll(params);
		boolean found=false;
		for(Role userRole:roles) {
			if(role.getIdPath().startsWith(userRole.getIdPath())||"0".equals(userRole.getRoleId())) {
				found=true;
				break;
			}
		}
		if(!found) {
			throw new CrisisError("您无权修改非自己管理的角色配置");
		}
		params.clear();
		params.put("ids", privilegeIds);
		List<Privilege> privileges = privilegeDao.queryAll(params);
		found=false;
		for(Privilege privilge:privileges) {
			if(sessionUser.getPrivilegeCodes().contains(privilge.getPrivilegeCode())) {
				found=true;
			}else {
				found=false;
				break;
			}
		}
		if(!found) {
			throw new CrisisError("您无权授权非自己的权限");
		}
		params.clear();
		params.put("roleId", roleId);
		params.put("lastModifierId", sessionUser.getUserId());
		params.put("lastModifierName", sessionUser.getRealname());
		rolePrivilegeDao.deleteAll(params);
		for(String privilegeId:privilegeIds) {
			RolePrivilege object=new RolePrivilege();
			object.setId(IdGenerator2.nextIdBase52String());
			object.setRoleId(roleId);
			object.setPrivilegeId(privilegeId);
			object.setCreaterId(sessionUser.getUserId());
			object.setCreaterName(sessionUser.getRealname());
			rolePrivilegeDao.insertObject(object);
		}
		JsonResult result=JsonResult.success();
		return result;
	}
}
