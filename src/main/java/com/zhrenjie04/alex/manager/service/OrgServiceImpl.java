package com.zhrenjie04.alex.manager.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zhrenjie04.alex.core.AbstractGenericService;
import com.zhrenjie04.alex.core.Job;
import com.zhrenjie04.alex.core.JsonResult;
import com.zhrenjie04.alex.core.User;
import com.zhrenjie04.alex.manager.dao.OrgDao;
import com.zhrenjie04.alex.manager.domain.Org;
import com.zhrenjie04.alex.util.IdGenerator2;
/**
 * @author 张人杰
 */
@Service("orgService")
public class OrgServiceImpl extends AbstractGenericService<Org,OrgDao> implements OrgService {

	@Autowired
	OrgDao orgDao;

	@Override
	protected OrgDao getDao() {
		return orgDao;
	}
	
	@Override
	public JsonResult tree(User user) {
		String jobId=user.getCurrentJobId();
		for(Job job:user.getJobs()){
			if(job.getJobId().equals(jobId)){
				if(Job.JOB_TYPE_ORG_POSITION_JOB.equals(job.getJobType())){
					return tree(job.getOrgId(),job.getGroupId());
				}
			}
		}
		return JsonResult.failure(JsonResult.CODE_INTERNAL_SERVER_ERROR, "非组织机构内职务无法获取机构树");
	}
	/**
	 * 获取下属机构树
	 * @param orgId
	 * @return
	 */
	private JsonResult tree(String orgId, String groupId) {
		HashMap<String,Object>params=new HashMap<String, Object>(16);
		params.put("treeChildren", true);
		params.put("orgId", orgId);
		params.put("groupId", groupId);
		List<Org>orgs=orgDao.queryAll(params);
		
		Org tree=new Org();
		HashMap<String,Org>orgMap=new HashMap<String,Org>(16);
		if(orgs!=null){
			boolean hasFound=false;
			Iterator<Org> it = orgs.iterator();
			while(it.hasNext()) {
				Org org=it.next();
				if(org.getOrgId().equals(orgId)){
					//根节点
					tree.setOrgId(org.getOrgId());
					tree.setOrgName(org.getOrgName());
					tree.setOrgCode(org.getOrgCode());
					orgMap.put(org.getOrgId(), tree);
					hasFound=true;
					it.remove();
					break;
				}
			}
			if(hasFound==false){
				return JsonResult.failure(JsonResult.CODE_INTERNAL_SERVER_ERROR, "组织机构不存在");
			}
			while(hasFound){
				//存在几层，扫几遍
				hasFound=false;
				it = orgs.iterator();
				while(it.hasNext()) {
					Org org=it.next();
					if(orgMap.get(org.getParentId())!=null){
						hasFound=true;
						if(orgMap.get(org.getParentId()).getChildren()==null){
							orgMap.get(org.getParentId()).setChildren(new LinkedList<Org>());
						}
						orgMap.get(org.getParentId()).getChildren().add(org);
						orgMap.put(org.getOrgId(), org);
						it.remove();
					}
				}
			}
		}
		JsonResult result=JsonResult.success();
		result.put("tree", tree);
		return result;
	}
	@Override
	public JsonResult pageQueryAll(HashMap<String, Object> params, User sessionUser) {
		if("-1".equals(params.get("parentId"))||params.get("parentId")==null||"".equals(params.get("parentId"))) {
			//返回当前用户的所属组织机构(一个职位只能属于一个组织机构)
			params.remove("parentId");
			params.put("id", sessionUser.getCurrentJob().getOrgId());
		}else {
			//判断请求是否在当前用户范围内
			HashMap<String,Object>userParams=new HashMap<String, Object>(16);
			userParams.put("orgId", params.get("parentId"));
			Org org=orgDao.queryObject(userParams);
			boolean isInPath=false;
			for(String userRoleId:sessionUser.getCurrentRoleIds()) {
				if(org.getIdPath().contains("/"+sessionUser.getCurrentJob().getOrgId()+"/")||"0".equals(sessionUser.getCurrentJob().getOrgId())||"0".equals(userRoleId)) {
					isInPath=true;
					break;
				}
			}
			if(!isInPath) {
				return JsonResult.failure(JsonResult.CODE_REQUEST_INPUT_ERROR, "传入参数不正确，未在当前用户的组织机构范围内");
			}
		}
		Long total=getDao().count(params);
		Long pageNo=(Long)params.get("pageNo");
		Long pageSize=(Long)params.get("pageSize");
		params.put("start", (pageNo-1)*pageSize);
		params.put("length", pageSize);
		List<Org> list=getDao().pageQueryAll(params);
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
		return result;
	}

	@Override
	public JsonResult path(String orgId,User user) {
		HashMap<String,Object>params=new HashMap<String, Object>(16);
		params.put("orgId", orgId);
		Org org=orgDao.queryObject(params);
		String idPath=org.getIdPath();
		boolean isUsersSubOrgId=false;
		if(idPath!=null) {
			for(String userRoleId:user.getCurrentRoleIds()) {
				if(org.getIdPath().contains("/"+user.getCurrentJob().getOrgId()+"/")||"0".equals(user.getCurrentJob().getOrgId())||"0".equals(userRoleId)) {
					isUsersSubOrgId=true;
					break;
				}
			}
		}
		if(isUsersSubOrgId) {
			JsonResult result =JsonResult.success();
			result.put("orgName", org.getOrgName());
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
			//过滤根系统
			List<Org> orgPath=orgDao.queryAll(params);
			boolean isRemovingParentIdPath=true;
			while(!orgPath.isEmpty()&&isRemovingParentIdPath) {
				if(!user.getCurrentJob().getOrgId().equals(orgPath.get(0).getOrgId())) {
					orgPath.remove(0);
					isRemovingParentIdPath=true;
				}else {
					isRemovingParentIdPath=false;
				}
			}
			result.put("orgPath",orgPath);
			return result;
		}
		return JsonResult.failure(JsonResult.CODE_REQUEST_INPUT_ERROR, "请求的组织机构id不属于当前用户管理范围");
	}
	@Override
	public JsonResult insertObject(Org object, User sessionUser) {
		if(object.getOrgName()==null||"".equals(object.getOrgName())) {
			return JsonResult.failure(JsonResult.CODE_REQUEST_INPUT_ERROR, "请输入组织机构名称");
		}
		object.setOrgId(IdGenerator2.nextIdBase52String());
		//判断parent角色是在用户角色树中
		HashMap<String,Object>params=new HashMap<String, Object>(16);
		params.put("orgId", object.getParentId());
		Org parentOrg=orgDao.queryObject(params);
		if("COM".equals(object.getOrgType())||"GROUP".equals(object.getOrgType())) {
			object.setComId(object.getOrgId());
		}else {
			object.setComId(parentOrg.getComId());
		}
		String idPath=parentOrg.getIdPath();
		boolean isUsersSubOrgId=false;
		if(idPath!=null) {
			for(String userRoleId:sessionUser.getCurrentRoleIds()) {
				if(idPath.contains("/"+sessionUser.getCurrentJob().getOrgId()+"/")||ROOT_ORG_ID.equals(parentOrg.getOrgId())&&ROOT_ORG_ID.equals(sessionUser.getCurrentJob().getOrgId())||ROOT_ROLE_ID.equals(userRoleId)) {
					isUsersSubOrgId=true;
					break;
				}
			}
		}
		if(isUsersSubOrgId) {
			if(ROOT_ORG_ID.equals(parentOrg.getOrgId())) {
				//第一级的才能设定为后台集团，且第一级必须是集团
				object.setGroupId(object.getOrgId());
				object.setOrgType("GROUP");
			}else {
				object.setGroupId(parentOrg.getGroupId());
				object.setOrgType("COM");
			}
			object.setIdPath(idPath+object.getOrgId()+"/");
			object.setCreaterId(sessionUser.getUserId());
			object.setCreaterName(sessionUser.getRealname());
			orgDao.insertObject(object);
			JsonResult result = JsonResult.success();
			result.put("message", "添加成功");
			return result;
		}
		return JsonResult.failure(JsonResult.CODE_REQUEST_INPUT_ERROR, "请求的父组织机构id不属于当前用户管理范围");
	}

	/**
	 * 级联删除
	 */
	@Override
	public JsonResult deleteAll(HashMap<String, Object> params, User sessionUser) {
		String[] ids=(String[])params.get("ids");
		for(String id:ids){
			for(Job job:sessionUser.getJobs()) {
				if(id.equals(job.getOrgId())){
					JsonResult result = JsonResult.failure(JsonResult.CODE_PREREQUISITE_NOT_SATISFIED,"不能删除自己所属的组织机构！");
					return result;
				}
			}
		}
		List<Org>needDeleteOrgs=getDao().queryAll(params);
		List<String>idPaths=new LinkedList<String>();
		for(Org needDeleteOrg:needDeleteOrgs) {
			idPaths.add(needDeleteOrg.getIdPath());
		}
		params.put("idPaths", idPaths);
		Long totalDeletedCount=0L;
		HashMap<String,Object>queryParams=new HashMap<String, Object>(16);
		queryParams.put("orgId", sessionUser.getCurrentJob().getOrgId());
		Org org=getDao().queryObject(queryParams);
		params.put("userOrgIdPath", org.getIdPath());
		params.put("lastModifierId", sessionUser.getUserId());
		params.put("lastModifierName", sessionUser.getRealname());
		Long count=getDao().deleteAll(params);
		totalDeletedCount+=count;
		if(totalDeletedCount>0){
			JsonResult result=JsonResult.success("操作成功！");
			return result;
		}else{
			JsonResult result = JsonResult.failure(JsonResult.CODE_PREREQUISITE_NOT_SATISFIED,"没有删除任何数据！");
			return result;
		}
	}
	@Override
	public JsonResult updateObject(Org object, User sessionUser) {
		if(object.getOrgName()==null||"".equals(object.getOrgName())) {
			return JsonResult.failure(JsonResult.CODE_REQUEST_INPUT_ERROR, "请输入组织机构名称");
		}
		//判断parent角色是否在用户角色树中
		HashMap<String,Object>params=new HashMap<String, Object>(16);
		params.put("orgId", object.getOrgId());
		Org org=orgDao.queryObject(params);
		String idPath=org.getIdPath();
		boolean isUsersSubOrgId=false;
		if(idPath!=null) {
			for(String roleId:sessionUser.getCurrentRoleIds()) {
				if(idPath.contains("/"+sessionUser.getCurrentJob().getOrgId()+"/")||"0".equals(org.getOrgId())&&"0".equals(sessionUser.getCurrentJob().getOrgId())||"0".equals(roleId)) {
					isUsersSubOrgId=true;
					break;
				}
			}
		}
		if(isUsersSubOrgId) {
			org.setOrgName(object.getOrgName());
			org.setOfficeContact(object.getOfficeContact());
			org.setOfficeFax(object.getOfficeFax());
			org.setOfficeLocation(object.getOfficeLocation());
			org.setOfficePostcode(object.getOfficePostcode());
			org.setOfficeTel(object.getOfficeTel());
			org.setLastModifierId(sessionUser.getUserId());
			org.setLastModifierName(sessionUser.getUsername());
			if(!org.getOrgType().equals(object.getOrgType())) {
				if("DEPT".equals(object.getOrgType())) {//选项有集团、公司和部门
					if(org.getGroupId().equals(org.getOrgId())) {
						//1级公司不能变部门
						return JsonResult.failure(JsonResult.CODE_REQUEST_INPUT_ERROR, "1级公司不能变部门");
					}else {
						//公司变部门
						params.clear();
						params.put("orgId", org.getParentId());
						Org parentOrg=orgDao.queryObject(params);
						org.setComId(parentOrg.getComId());
						org.setOrgType("DEPT");
						orgDao.updateObject(org);
						orgDao.updateSubComsComIdAndGroupIdToDept(org);
					}
				}else if("COM".equals(object.getOrgType())) {
					if(org.getGroupId().equals(org.getOrgId())) {
						//1级公司(集团)不能变普通二、三级公司
						return JsonResult.failure(JsonResult.CODE_REQUEST_INPUT_ERROR, "1级公司(集团)不能变普通二、三级公司");
					}else {
						//部门变公司
						org.setComId(org.getOrgId());
						org.setOrgType("COM");
						orgDao.updateObject(org);
						orgDao.updateSubComsComIdAndGroupIdToDept(org);
					}
				}else if("GROUP".equals(object.getOrgType())) {
					//非1级公司不能修改为集团
					return JsonResult.failure(JsonResult.CODE_REQUEST_INPUT_ERROR, "非1级公司不能修改为集团");
				}
			}else {
				orgDao.updateObject(org);
			}
			JsonResult result = JsonResult.success();
			result.put("message", "修改成功");
			return result;
		}else {
			return JsonResult.failure(JsonResult.CODE_REQUEST_INPUT_ERROR, "请求的组织机构id不属于当前用户管理范围");
		}
	}
	@Override
	@Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout=60000,rollbackFor=Exception.class)
	public JsonResult moveTo(String orgId, LinkedList<String> orgIds, User sessionUser) {
		//1、判断目标orgId是否属于当前用户组织机构范围
		HashMap<String,Object>params=new HashMap<String, Object>(16);
		params.put("orgId", orgId);
		Org targetOrg=orgDao.queryObject(params);
		String targetOrgIdPath=targetOrg.getIdPath();
		boolean isUsersSubOrgId=false;
		if(targetOrgIdPath!=null) {
			for(String userRoleId:sessionUser.getCurrentRoleIds()) {
				if(targetOrgIdPath.contains("/"+sessionUser.getCurrentJob().getOrgId()+"/")||"0".equals(targetOrg.getOrgId())&&"0".equals(sessionUser.getCurrentJob().getOrgId())||"0".equals(userRoleId)) {
					//只有当前身份所属为根组织或根角色的时候才能移动为第一级集团
					isUsersSubOrgId=true;
					break;
				}
			}
		}
		if(!isUsersSubOrgId) {
			return JsonResult.failure(JsonResult.CODE_REQUEST_INPUT_ERROR, "请求的目标角色不属于当前用户管理范围");
		}
		//2、判断被移动对象是否属于当前用户组织机构树范围
		params.clear();
		if(orgIds==null) {
			return JsonResult.failure(JsonResult.CODE_REQUEST_INPUT_ERROR, "没有请求移动任何组织机构");
		}
		params.put("ids", orgIds);
		List<Org> orgs=orgDao.queryAll(params);
		isUsersSubOrgId=true;
		for(Org org:orgs) {
			isUsersSubOrgId=false;
			for(String userRoleId:sessionUser.getCurrentRoleIds()) {
				//被移动对象不能是根机构
				if(org.getIdPath().contains("/"+sessionUser.getCurrentJob().getOrgId()+"/")||"0".equals(userRoleId)) {
					isUsersSubOrgId=true;
					break;
				}
			}
			if(isUsersSubOrgId==false) {
				break;
			}
		}
		if(!isUsersSubOrgId) {
			return JsonResult.failure(JsonResult.CODE_REQUEST_INPUT_ERROR, "请求的被移动组织机构不属于当前用户管理范围");
		}
		//3、判断目标机构是否与被移动对象是否属于同一个集团
		boolean isSameGroup=true;
		for(Org org:orgs) {
			if(!org.getGroupId().equals(targetOrg.getGroupId())) {
				isSameGroup=false;
				break;
			}
		}
		if(!isSameGroup&&!ROOT_ORG_ID.equals(targetOrg.getOrgId())) {
			return JsonResult.failure(JsonResult.CODE_REQUEST_INPUT_ERROR, "只能移动相同集团内的角色");
		}
		//4、判断目标角色是否不属于被移动对象的父节点的下属节点
		boolean existLoop=false;
		for(Org org:orgs) {
			if(targetOrg.getIdPath().startsWith(org.getIdPath())) {
				existLoop=true;
				break;
			}
		}
		if(existLoop) {
			return JsonResult.failure(JsonResult.CODE_REQUEST_INPUT_ERROR, "存在死循环");
		}
		//5、移动被移动对象
		for(Org org:orgs) {
			HashMap<String,Object>subNodesParams=new HashMap<String, Object>(16);
			subNodesParams.put("oldIdPathStart",org.getIdPath());
			subNodesParams.put("newIdPathStart",targetOrgIdPath+org.getOrgId()+"/");
			orgDao.updateMovingSubNodes(subNodesParams);
			org.setIdPath(targetOrgIdPath+org.getOrgId()+"/");
			org.setParentId(targetOrg.getOrgId());
			orgDao.updateMoving(org);
			//移动以后全部子节点均变为部门
			if(ROOT_ORG_ID.equals(targetOrg.getOrgId())) {
				org.setGroupId(org.getOrgId());
				org.setComId(org.getOrgId());
				org.setOrgType("GROUP");
				orgDao.updateObject(org);
				orgDao.updateSubComsComIdAndGroupIdToDept(org);
			}else {
				org.setGroupId(targetOrg.getGroupId());
				org.setComId(targetOrg.getComId());
				orgDao.updateSubComsComIdAndGroupIdToDept(org);
			}
		}
		return JsonResult.success("移动成功");
	}
	@Override
	public JsonResult lock(Org object, User sessionUser) {
		HashMap<String,Object>params=new HashMap<String, Object>(16);
		params.put("orgId", object.getOrgId());
		Org org=orgDao.queryObject(params);
		String idPath=org.getIdPath();
		boolean isUsersSubOrgId=false;
		if(idPath!=null) {
			if(idPath.contains("/"+sessionUser.getCurrentJob().getOrgId()+"/")||ROOT_GROUP_ID.equals(sessionUser.getGroupId())) {
				isUsersSubOrgId=true;
			}
		}
		// 不能锁定/解锁自己的组织机构
		if(object.getOrgId().equals(sessionUser.getCurrentJob().getOrgId())) {
			isUsersSubOrgId=false;
			return JsonResult.failure(JsonResult.CODE_PREREQUISITE_NOT_SATISFIED,"不能锁定自己的组织机构");
		}
		if(isUsersSubOrgId) {
			object.setIsLocked(true);
			Long count=orgDao.updateLocked(object);
			if(count>0) {
				return JsonResult.success("锁定成功");
			}else {
				return JsonResult.failure(JsonResult.CODE_PREREQUISITE_NOT_SATISFIED,"锁定失败，输入参数不合法");
			}
		}else {
			return JsonResult.failure(JsonResult.CODE_PREREQUISITE_NOT_SATISFIED,"锁定失败，输入参数不合法");
		}
	}
	@Override
	public JsonResult unlock(Org object, User sessionUser) {
		HashMap<String,Object>params=new HashMap<String, Object>(16);
		params.put("orgId", object.getOrgId());
		Org org=orgDao.queryObject(params);
		String idPath=org.getIdPath();
		boolean isUsersSubOrgId=false;
		if(idPath!=null) {
			if(idPath.contains("/"+sessionUser.getCurrentJob().getOrgId()+"/")||ROOT_GROUP_ID.equals(sessionUser.getGroupId())) {
				isUsersSubOrgId=true;
			}
		}
		// 不能锁定/解锁自己的组织机构
		if(object.getOrgId().equals(sessionUser.getCurrentJob().getOrgId())) {
			isUsersSubOrgId=false;
			return JsonResult.failure(JsonResult.CODE_PREREQUISITE_NOT_SATISFIED,"不能解锁自己的组织机构");
		}
		if(isUsersSubOrgId) {
			object.setIsLocked(false);
			Long count=orgDao.updateLocked(object);
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
