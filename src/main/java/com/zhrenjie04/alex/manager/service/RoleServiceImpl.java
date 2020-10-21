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
import com.zhrenjie04.alex.manager.dao.RoleDao;
import com.zhrenjie04.alex.manager.domain.Role;
import com.zhrenjie04.alex.util.IdGenerator2;
/**
 * @author 张人杰
 */
@Service("roleService")
public class RoleServiceImpl extends AbstractGenericService<Role,RoleDao> implements RoleService {

	@Autowired
	RoleDao roleDao;

	@Override
	protected RoleDao getDao() {
		return roleDao;
	}
	@Override
	public JsonResult pageQueryAll(HashMap<String, Object> params, User sessionUser) {
		if("-1".equals(params.get("parentId"))||params.get("parentId")==null||"".equals(params.get("parentId"))) {
			//返回当前用户的所有角色
			params.remove("parentId");
			params.put("ids", sessionUser.getCurrentRoleIds());
		}else {
			//判断请求是否在当前用户范围内
			HashMap<String,Object>userParams=new HashMap<String, Object>(16);
			userParams.put("roleId", params.get("parentId"));
			Role role=roleDao.queryObject(userParams);
			boolean isInPath=false;
			for(String userRoleId:sessionUser.getCurrentRoleIds()) {
				if(role.getIdPath().contains("/"+userRoleId+"/")||"0".equals(userRoleId)) {
					isInPath=true;
					break;
				}
			}
			if(!isInPath) {
				return JsonResult.failure(JsonResult.CODE_REQUEST_INPUT_ERROR, "传入参数不正确，未在当前用户的角色范围内");
			}
		}
		Long total=getDao().count(params);
		Long pageNo=(Long)params.get("pageNo");
		Long pageSize=(Long)params.get("pageSize");
		params.put("start", (pageNo-1)*pageSize);
		params.put("length", pageSize);
		List<Role> list=getDao().pageQueryAll(params);
		JsonResult result = JsonResult.success();
		result.put("total", total);
		result.put("list", list);
		result.put("pageNo", pageNo);
		result.put("pageSize", pageSize);
		result.put("orderBy", params.get("orderByFrontend"));
		result.put("orderType", params.get("orderType"));
		if(pageSize!=null){
			result.put("totalPages", (total/pageSize)+(total%pageSize>0?1:0));
		}
//		result.put("totalPages", 1001);
		return result;
	}

	@Override
	public JsonResult path(String roleId,User user) {
		HashMap<String,Object>params=new HashMap<String, Object>(16);
		params.put("roleId", roleId);
		Role role=roleDao.queryObject(params);
		String idPath=role.getIdPath();
		boolean isUsersSubRoleId=false;
		if(idPath!=null) {
			for(String userRoleId:user.getCurrentRoleIds()) {
				if(idPath.contains("/"+userRoleId+"/")||"0".equals(userRoleId)) {
					isUsersSubRoleId=true;
					break;
				}
			}
		}
		if(isUsersSubRoleId) {
			JsonResult result =JsonResult.success();
			result.put("roleName", role.getRoleName());
			if("/".equals(idPath)){
				String[] ids={"0"};
				params.clear();
				params.put("ids",ids);
			}else{
				String[] ids=idPath.split("\\/");
				params.clear();
				params.put("ids",ids);
			}
			params.put("orderByIdPathAsc", true);
			//过滤根角色
			List<Role> rolePath=roleDao.queryAll(params);
			boolean isRemovingParentIdPath=true;
			while(!rolePath.isEmpty()&&isRemovingParentIdPath) {
				if(!user.getCurrentRoleIds().contains(rolePath.get(0).getRoleId())) {
					rolePath.remove(0);
					isRemovingParentIdPath=true;
				}else {
					isRemovingParentIdPath=false;
				}
			}
			result.put("rolePath",rolePath);
			return result;
		}
		return JsonResult.failure(JsonResult.CODE_REQUEST_INPUT_ERROR, "请求的角色id不属于当前用户管理范围");
	}
	@Override
	public JsonResult trees(User user) {
		List<Role>trees=new LinkedList<Role>();
		if(user.getCurrentRoleIds()!=null) {
			for(String roleId:user.getCurrentRoleIds()) {
				Role tree=tree(roleId);
				if(tree!=null) {
					trees.add(tree);
				}
			}
		}
		JsonResult result=JsonResult.success();
		result.put("trees",trees);
		return result;
	}
	/**
	 * 获取角色森林
	 * @param orgId
	 * @return
	 */
	private Role tree(String roleId) {
		HashMap<String,Object>params=new HashMap<String, Object>(16);
		params.put("treeChildren", true);
		params.put("roleId", roleId);
		Role userRole=roleDao.queryObject(params);
		if(ROOT_ROLE_ID.equals(roleId)||ROOT_GROUP_ID.equals(userRole.getGroupId())) {
			//改进点：对于根系统用户：不构建全系统角色树，只显示根集团的机构树
			params.put("groupId", "0");
		}
		List<Role>roles=roleDao.queryAll(params);
		
		Role tree=new Role();
		HashMap<String,Role>roleMap=new HashMap<String,Role>(16);
		if(roles!=null){
			boolean hasFound=false;
			Iterator<Role>it=roles.iterator();
			while(it.hasNext()) {
				Role role=it.next();
				if(role.getRoleId().equals(roleId)){
					//根节点
					tree.setRoleId(role.getRoleId());
					tree.setRoleName(role.getRoleName());
					tree.setRoleCode(role.getRoleCode());
					roleMap.put(role.getRoleId(), tree);
					hasFound=true;
					it.remove();
					break;
				}
			}
			if(hasFound==false){
				return null;
			}
			while(hasFound){
				//存在几层，扫几遍
				hasFound=false;
				it=roles.iterator();
				while(it.hasNext()) {
					Role role=it.next();
					if(roleMap.get(role.getParentId())!=null){
						hasFound=true;
						if(roleMap.get(role.getParentId()).getChildren()==null){
							roleMap.get(role.getParentId()).setChildren(new LinkedList<Role>());
						}
						roleMap.get(role.getParentId()).getChildren().add(role);
						roleMap.put(role.getRoleId(), role);
						it.remove();
					}
				}
			}
		}
		return tree;
	}
	@Override
	public JsonResult insertObject(Role object, User sessionUser) {
		if(object.getRoleName()==null||"".equals(object.getRoleName())) {
			return JsonResult.failure(JsonResult.CODE_REQUEST_INPUT_ERROR, "请输入角色名称");
		}
		//判断parent角色是在用户角色树中
		HashMap<String,Object>params=new HashMap<String, Object>(16);
		params.put("roleId", object.getParentId());
		Role parentRole=roleDao.queryObject(params);
		String idPath=parentRole.getIdPath();
		boolean isUsersSubRoleId=false;
		if(idPath!=null) {
			for(String userRoleId:sessionUser.getCurrentRoleIds()) {
				if(idPath.contains("/"+userRoleId+"/")||"0".equals(userRoleId)) {
					isUsersSubRoleId=true;
					break;
				}
			}
		}
		if(isUsersSubRoleId) {
			object.setRoleId(IdGenerator2.nextIdBase52String());
			object.setIdPath(idPath+object.getRoleId()+"/");
			object.setGroupId(sessionUser.getCurrentJob().getGroupId());//哪个身份创建的角色在这个身份所在的集团下
			object.setCreaterId(sessionUser.getUserId());
			object.setCreaterName(sessionUser.getRealname());
			roleDao.insertObject(object);
			JsonResult result = JsonResult.success();
			result.put("message", "添加成功");
			return result;
		}
		return JsonResult.failure(JsonResult.CODE_REQUEST_INPUT_ERROR, "请求的父角色id不属于当前用户管理范围");
	}

	@Override
	public JsonResult deleteAll(HashMap<String, Object> params, User sessionUser) {
		String[] ids=(String[])params.get("ids");
		for(String id:ids){
			if(sessionUser.getCurrentRoleIds().contains(id)){
				JsonResult result = JsonResult.failure(JsonResult.CODE_PREREQUISITE_NOT_SATISFIED,"不能删除自己的角色！");
				return result;
			}
		}
		List<Role>needDeleteRoles=getDao().queryAll(params);
		List<String>idPaths=new LinkedList<String>();
		for(Role needDeleteRole:needDeleteRoles) {
			idPaths.add(needDeleteRole.getIdPath());
		}
		params.put("idPaths", idPaths);
		Long totalDeletedCount=0L;
		for(String roleId:sessionUser.getCurrentRoleIds()){
			HashMap<String,Object>queryParams=new HashMap<String, Object>(16);
			queryParams.put("roleId", roleId);
			Role role=getDao().queryObject(queryParams);
			params.put("userRoleIdPath", role.getIdPath());
			params.put("lastModifierId", sessionUser.getUserId());
			params.put("lastModifierName", sessionUser.getRealname());
			Long count=getDao().deleteAll(params);
			totalDeletedCount+=count;
		}
		if(totalDeletedCount>0){
			JsonResult result=JsonResult.success("操作成功！");
			return result;
		}else{
			JsonResult result = JsonResult.failure(JsonResult.CODE_PREREQUISITE_NOT_SATISFIED,"没有删除任何数据！");
			return result;
		}
	}
	@CacheEvict(value="users", allEntries=true)
	@Override
	public JsonResult updateObject(Role object, User sessionUser) {
		if(object.getRoleName()==null||"".equals(object.getRoleName())) {
			return JsonResult.failure(JsonResult.CODE_REQUEST_INPUT_ERROR, "请输入角色名称");
		}
		//判断parent角色是否在用户角色树中
		HashMap<String,Object>params=new HashMap<String, Object>(16);
		params.put("roleId", object.getRoleId());
		Role role=roleDao.queryObject(params);
		String idPath=role.getIdPath();
		boolean isUsersSubRoleId=false;
		if(idPath!=null) {
			for(String userRoleId:sessionUser.getCurrentRoleIds()) {
				if(idPath.contains("/"+userRoleId+"/")||"0".equals(userRoleId)) {
					isUsersSubRoleId=true;
					break;
				}
			}
		}
		if(isUsersSubRoleId) {
			role.setLastModifierId(sessionUser.getUserId());
			role.setLastModifierName(sessionUser.getUsername());
			role.setRoleCode(object.getRoleCode());
			role.setRoleName(object.getRoleName());
			roleDao.updateObject(role);
			JsonResult result = JsonResult.success();
			result.put("message", "修改成功");
			return result;
		}else {
			return JsonResult.failure(JsonResult.CODE_REQUEST_INPUT_ERROR, "请求的角色id不属于当前用户管理范围");
		}
	}
	@Override
	@Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout=60000,rollbackFor=Exception.class)
	public JsonResult moveTo(String roleId, LinkedList<String> roleIds, User sessionUser) {
		//1、判断目标roleId是否属于当前用户角色树范围
		HashMap<String,Object>params=new HashMap<String, Object>(16);
		params.put("roleId", roleId);
		Role targetRole=roleDao.queryObject(params);
		String targetRoleIdPath=targetRole.getIdPath();
		boolean isUsersSubRoleId=false;
		if(targetRoleIdPath!=null) {
			for(String userRoleId:sessionUser.getCurrentRoleIds()) {
				if(targetRoleIdPath.contains("/"+userRoleId+"/")||"0".equals(userRoleId)) {
					isUsersSubRoleId=true;
					break;
				}
			}
		}
		if(!isUsersSubRoleId) {
			return JsonResult.failure(JsonResult.CODE_REQUEST_INPUT_ERROR, "请求的目标角色不属于当前用户管理范围");
		}
		//2、判断移动对象是否属于当前用户角色树范围
		params.clear();
		params.put("ids", roleIds);
		if(roleIds==null) {
			return JsonResult.failure(JsonResult.CODE_REQUEST_INPUT_ERROR, "没有请求移动任何角色");
		}
		List<Role> roles=roleDao.queryAll(params);
		isUsersSubRoleId=true;
		for(Role role:roles) {
			isUsersSubRoleId=false;
			for(String userRoleId:sessionUser.getCurrentRoleIds()) {
				if(role.getIdPath().contains("/"+userRoleId+"/")||"0".equals(userRoleId)) {
					isUsersSubRoleId=true;
					break;
				}
			}
			if(isUsersSubRoleId==false) {
				break;
			}
		}
		if(!isUsersSubRoleId) {
			return JsonResult.failure(JsonResult.CODE_REQUEST_INPUT_ERROR, "请求的被移动角色不属于当前用户管理范围");
		}
		//3、判断目标角色是否与被移动对象是否属于同一个集团
		boolean isSameGroup=true;
		for(Role role:roles) {
			if(!role.getGroupId().equals(targetRole.getGroupId())) {
				isSameGroup=false;
				break;
			}
		}
		if(!isSameGroup) {//role不能移动到根，因为groupId没发控制
			return JsonResult.failure(JsonResult.CODE_REQUEST_INPUT_ERROR, "只能移动相同集团内的角色");
		}
		//4、判断目标角色是否不属于被移动对象的父节点的下属节点
		boolean existLoop=false;
		for(Role role:roles) {
			if(targetRole.getIdPath().startsWith(role.getIdPath())) {
				existLoop=true;
				break;
			}
		}
		if(existLoop) {
			return JsonResult.failure(JsonResult.CODE_REQUEST_INPUT_ERROR, "存在死循环");
		}
		//5、移动被移动对象
		for(Role role:roles) {
			HashMap<String,Object>subNodesParams=new HashMap<String, Object>(16);
			subNodesParams.put("oldIdPathStart",role.getIdPath());
			subNodesParams.put("newIdPathStart",targetRoleIdPath+role.getRoleId()+"/");
			roleDao.updateMovingSubNodes(subNodesParams);
			role.setIdPath(targetRoleIdPath+role.getRoleId()+"/");
			role.setParentId(targetRole.getRoleId());
			roleDao.updateMoving(role);
		}
		return JsonResult.success("移动成功");
	}
	@CacheEvict(value="users", allEntries=true)
	@Override
	public JsonResult lock(Role object, User sessionUser) {
		HashMap<String,Object>params=new HashMap<String, Object>(16);
		params.put("roleId", object.getRoleId());
		Role role=roleDao.queryObject(params);
		String idPath=role.getIdPath();
		boolean isUsersSubRoleId=false;
		if(idPath!=null) {
			for(String userRoleId:sessionUser.getCurrentRoleIds()) {
				if(idPath.contains("/"+userRoleId+"/")||"0".equals(userRoleId)) {
					isUsersSubRoleId=true;
					break;
				}
			}
		}
		for(String userRoleId:sessionUser.getCurrentRoleIds()) {
			// 不能锁定/解锁自己的角色
			if(userRoleId.equals(object.getRoleId())) {
				isUsersSubRoleId=false;
				return JsonResult.failure(JsonResult.CODE_PREREQUISITE_NOT_SATISFIED,"不能锁定自己的角色");
			}
		}
		if(isUsersSubRoleId) {
			object.setIsLocked(true);
			Long count=roleDao.updateLocked(object);
			if(count>0) {
				return JsonResult.success("锁定成功");
			}else {
				return JsonResult.failure(JsonResult.CODE_PREREQUISITE_NOT_SATISFIED,"锁定失败，输入参数不合法");
			}
		}else {
			return JsonResult.failure(JsonResult.CODE_PREREQUISITE_NOT_SATISFIED,"锁定失败，输入参数不合法");
		}
	}
	@CacheEvict(value="users", allEntries=true)
	@Override
	public JsonResult unlock(Role object, User sessionUser) {
		HashMap<String,Object>params=new HashMap<String, Object>(16);
		params.put("roleId", object.getRoleId());
		Role role=roleDao.queryObject(params);
		String idPath=role.getIdPath();
		boolean isUsersSubRoleId=false;
		if(idPath!=null) {
			for(String userRoleId:sessionUser.getCurrentRoleIds()) {
				if(idPath.contains("/"+userRoleId+"/")||"0".equals(userRoleId)) {
					isUsersSubRoleId=true;
					break;
				}
			}
		}
		for(String userRoleId:sessionUser.getCurrentRoleIds()) {
			// 不能锁定/解锁自己的角色
			if(userRoleId.equals(object.getRoleId())) {
				isUsersSubRoleId=false;
				return JsonResult.failure(JsonResult.CODE_PREREQUISITE_NOT_SATISFIED,"不能解锁自己的角色");
			}
		}
		if(isUsersSubRoleId) {
			object.setIsLocked(false);
			Long count=roleDao.updateLocked(object);
			if(count>0) {
				return JsonResult.success("解锁成功");
			}else {
				return JsonResult.failure(JsonResult.CODE_PREREQUISITE_NOT_SATISFIED,"解锁失败，输入参数不合法");
			}
		}else {
			return JsonResult.failure(JsonResult.CODE_PREREQUISITE_NOT_SATISFIED,"解锁失败，输入参数不合法");
		}
	}
}
