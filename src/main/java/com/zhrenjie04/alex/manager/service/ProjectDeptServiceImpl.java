package com.zhrenjie04.alex.manager.service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhrenjie04.alex.core.AbstractGenericService;
import com.zhrenjie04.alex.core.JsonResult;
import com.zhrenjie04.alex.core.User;
import com.zhrenjie04.alex.manager.dao.OrgDao;
import com.zhrenjie04.alex.manager.dao.ProjectDeptDao;
import com.zhrenjie04.alex.manager.domain.Com;
import com.zhrenjie04.alex.manager.domain.Org;
import com.zhrenjie04.alex.manager.domain.ProjectDept;
import com.zhrenjie04.alex.util.IdGenerator2;
/**
 * @author 张人杰
 */
@Service("projectDeptService")
public class ProjectDeptServiceImpl extends AbstractGenericService<ProjectDept,ProjectDeptDao> implements ProjectDeptService {

	@Autowired
	ProjectDeptDao projectDeptDao;

	@Autowired
	OrgDao orgDao;

	@Override
	protected ProjectDeptDao getDao() {
		return projectDeptDao;
	}
	@Override
	public JsonResult trees(User user) {
		if(user.hasPrivilege("manager:project-dept.back.trees-all")&&ROOT_ORG_ID.equals(user.getCurrentJob().getOrgId())){
			//根事业部管理
			HashMap<String,Object>params=new HashMap<String, Object>(16);
			List<ProjectDept>projectDepts=projectDeptDao.treesAll(params);
			List<Com>coms=new LinkedList<Com>();
			HashMap<String,Com>comsMap=new HashMap<String,Com>(16);
			for(ProjectDept projectDept:projectDepts) {
				if(comsMap.get((String)projectDept.getOtherResults().get("orgId"))!=null) {
					comsMap.get((String)projectDept.getOtherResults().get("orgId")).getProjectDepts().add(projectDept);
				}else {
					Com com=new Com();
					com.setComId((String)projectDept.getOtherResults().get("orgId"));
					com.setComCode((String)projectDept.getOtherResults().get("orgCode"));
					com.setComName((String)projectDept.getOtherResults().get("orgName"));
					coms.add(com);
					comsMap.put(com.getComId(),com);
					com.setProjectDepts(new LinkedList<ProjectDept>());
					com.getProjectDepts().add(projectDept);
				}
			}
			JsonResult result=JsonResult.success();
			result.put("coms", coms);
			return result;
		}else if(user.hasPrivilege("manager:project-dept.back.com-subcoms-all-prjdpts")) {
			//返回本公司及下属公司所有事业部
			HashMap<String,Object>params=new HashMap<String, Object>(16);
			params.put("comId", user.getCurrentJob().getComId());
			//1、查询orgId这个机构所在公司的所有下属公司
			//2、返回所有这些公司的项目部及公司名称
			List<ProjectDept>projectDepts=projectDeptDao.treesComAndSubCom(params);
			List<Com>coms=new LinkedList<Com>();
			HashMap<String,Com>comsMap=new HashMap<String,Com>(16);
			for(ProjectDept projectDept:projectDepts) {
				if(comsMap.get((String)projectDept.getOtherResults().get("orgId"))!=null) {
					comsMap.get((String)projectDept.getOtherResults().get("orgId")).getProjectDepts().add(projectDept);
				}else {
					Com com=new Com();
					com.setComId((String)projectDept.getOtherResults().get("orgId"));
					com.setComCode((String)projectDept.getOtherResults().get("orgCode"));
					com.setComName((String)projectDept.getOtherResults().get("orgName"));
					coms.add(com);
					comsMap.put(com.getComId(),com);
					com.setProjectDepts(new LinkedList<ProjectDept>());
					com.getProjectDepts().add(projectDept);
				}
			}
			JsonResult result=JsonResult.success();
			result.put("coms", coms);
			return result;
		}else if(user.hasPrivilege("manager:project-dept.back.com-all-prjdpts")) {
			//返回本公司所有事业部
			HashMap<String,Object>params=new HashMap<String, Object>(16);
			params.put("comId", user.getCurrentJob().getComId());
			//1、查询orgId这个机构所在公司的所有下属公司
			//2、返回所有这些公司的项目部及公司名称
			List<ProjectDept>projectDepts=projectDeptDao.treesCom(params);
			List<Com>coms=new LinkedList<Com>();
			HashMap<String,Com>comsMap=new HashMap<String,Com>(16);
			for(ProjectDept projectDept:projectDepts) {
				if(comsMap.get((String)projectDept.getOtherResults().get("orgId"))!=null) {
					comsMap.get((String)projectDept.getOtherResults().get("orgId")).getProjectDepts().add(projectDept);
				}else {
					Com com=new Com();
					com.setComId((String)projectDept.getOtherResults().get("orgId"));
					com.setComCode((String)projectDept.getOtherResults().get("orgCode"));
					com.setComName((String)projectDept.getOtherResults().get("orgName"));
					coms.add(com);
					comsMap.put(com.getComId(),com);
					com.setProjectDepts(new LinkedList<ProjectDept>());
					com.getProjectDepts().add(projectDept);
				}
			}
			JsonResult result=JsonResult.success();
			result.put("coms", coms);
			return result;
		}else if(user.hasPrivilege("manager:project-dept.back.my-prjdpts")) {
			//查询自己所在事业部
			HashMap<String,Object>params=new HashMap<String, Object>(16);
			params.put("projectDeptId", user.getCurrentJob().getProjectDeptId());
			List<ProjectDept>projectDepts=projectDeptDao.treesMy(params);
			List<Com>coms=new LinkedList<Com>();
			HashMap<String,Com>comsMap=new HashMap<String,Com>(16);
			for(ProjectDept projectDept:projectDepts) {
				if(comsMap.get((String)projectDept.getOtherResults().get("orgId"))!=null) {
					comsMap.get((String)projectDept.getOtherResults().get("orgId")).getProjectDepts().add(projectDept);
				}else {
					Com com=new Com();
					com.setComId((String)projectDept.getOtherResults().get("orgId"));
					com.setComCode((String)projectDept.getOtherResults().get("orgCode"));
					com.setComName((String)projectDept.getOtherResults().get("orgName"));
					coms.add(com);
					comsMap.put(com.getComId(),com);
					com.setProjectDepts(new LinkedList<ProjectDept>());
					com.getProjectDepts().add(projectDept);
				}
			}
			JsonResult result=JsonResult.success();
			result.put("coms", coms);
			return result;
		}else {
			return JsonResult.failure(JsonResult.CODE_INTERNAL_SERVER_ERROR, "无权获取事业部信息");
		}
	}
	@Override
	public JsonResult pageQueryAll(HashMap<String, Object> params, User sessionUser) {
		if(sessionUser.getCurrentJob().getGroupId() == null) {
			return JsonResult.failure(JsonResult.CODE_PREREQUISITE_NOT_SATISFIED,"您的groupId为空，不能返回相应事业部信息");
		}
		if(sessionUser.hasPrivilege("manager:project-dept.back.page-query-all-groups")&&ROOT_GROUP_ID.equals(sessionUser.getGroupId())&&ROOT_GROUP_ID.equals(sessionUser.getCurrentJob().getGroupId())) {
			//返回所有事业部权限
		}else if(sessionUser.hasPrivilege("manager:project-dept.back.page-query-all-coms")){
			//只返回本集团的事业部的权限
			params.put("groupId", sessionUser.getCurrentJob().getGroupId());
		}else if(sessionUser.hasPrivilege("manager:project-dept.back.page-query")){
			//只返回本公司的事业部的权限
			params.put("groupId", sessionUser.getCurrentJob().getGroupId());
			params.put("comId", sessionUser.getCurrentJob().getComId());
		}else {
			//不返回数据
			params.put("groupId", -1);
		}
		return super.pageQueryAll(params, sessionUser);
	}
	@Override
	public JsonResult insertObject(ProjectDept object, User sessionUser) {
		if(object.getProjectDeptName()==null||"".equals(object.getProjectDeptName())) {
			return JsonResult.failure(JsonResult.CODE_REQUEST_INPUT_ERROR, "请输入事业部名称");
		}
		object.setProjectDeptId(IdGenerator2.nextIdBase52String());
		if(sessionUser.getPrivilegeCodes().contains("manager:project-dept.back.add-all-groups")&&ROOT_GROUP_ID.equals(sessionUser.getGroupId())) {
			//添加任何集团事业部
			//从所属公司获取所属集团
			String comId=object.getComId();
			HashMap<String,Object>params=new HashMap<String, Object>(16);
			params.put("orgId", comId);
			Org com=orgDao.queryObject(params);
			object.setGroupId(com.getGroupId());
		}else if(sessionUser.getPrivilegeCodes().contains("manager:project-dept.back.add-self-group")) {
			//添加本集团事业部
			String comId=object.getComId();
			HashMap<String,Object>params=new HashMap<String, Object>(16);
			params.put("orgId", comId);
			Org com=orgDao.queryObject(params);
			if(com.getGroupId().equals(sessionUser.getCurrentJob().getGroupId())) {
				object.setGroupId(com.getGroupId());
			}else {
				return JsonResult.failure(JsonResult.CODE_REQUEST_INPUT_ERROR, "只能添加所在集团的事业部");
			}
		}else if(sessionUser.getPrivilegeCodes().contains("manager:project-dept.back.add-self-com")) {
			//添加本公司事业部
			if(object.getComId().equals(sessionUser.getCurrentJob().getComId())&&object.getGroupId().equals(sessionUser.getCurrentJob().getGroupId())) {
			}else {
				return JsonResult.failure(JsonResult.CODE_REQUEST_INPUT_ERROR, "只能添加所在公司的事业部");
			}
		}else {
			return JsonResult.failure(JsonResult.CODE_REQUEST_INPUT_ERROR, "您没有添加该事业部的权限");
		}
		projectDeptDao.insertObject(object);
		JsonResult result = JsonResult.success();
		result.put("message", "添加成功");
		return result;
	}

	@Override
	public JsonResult deleteAll(HashMap<String, Object> params, User sessionUser) {
		//未完成
		Long totalDeletedCount=0L;
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
	public JsonResult updateObject(ProjectDept object, User sessionUser) {
		if(object.getProjectDeptName()==null||"".equals(object.getProjectDeptName())) {
			return JsonResult.failure(JsonResult.CODE_REQUEST_INPUT_ERROR, "请输入事业部名称");
		}
		if(sessionUser.getPrivilegeCodes().contains("manager:project-dept.back.modify-all-groups")&&ROOT_GROUP_ID.equals(sessionUser.getGroupId())) {
			//添加任何集团事业部
			//从所属公司获取所属集团
			String comId=object.getComId();
			HashMap<String,Object>params=new HashMap<String, Object>(16);
			params.put("orgId", comId);
			Org com=orgDao.queryObject(params);
			object.setGroupId(com.getGroupId());
		}else if(sessionUser.getPrivilegeCodes().contains("manager:project-dept.back.modify-self-group")) {
			//添加本集团事业部
			HashMap<String,Object>queryParams=new HashMap<String, Object>(16);
			queryParams.put("projectDeptId",object.getProjectDeptId());
			ProjectDept oldProjectDept=projectDeptDao.queryObject(queryParams);
			if(!oldProjectDept.getGroupId().equals(sessionUser.getCurrentJob().getGroupId())) {
				return JsonResult.failure(JsonResult.CODE_REQUEST_INPUT_ERROR, "只能修改所在集团的事业部");
			}
			String comId=object.getComId();
			HashMap<String,Object>params=new HashMap<String, Object>(16);
			params.put("orgId", comId);
			Org com=orgDao.queryObject(params);
			if(com.getGroupId().equals(sessionUser.getCurrentJob().getGroupId())) {
				object.setGroupId(com.getGroupId());
			}else {
				return JsonResult.failure(JsonResult.CODE_REQUEST_INPUT_ERROR, "只能修改所在集团的事业部");
			}
		}else if(sessionUser.getPrivilegeCodes().contains("manager:project-dept.back.modify-self-com")) {
			//添加本公司事业部
			HashMap<String,Object>queryParams=new HashMap<String, Object>(16);
			queryParams.put("projectDeptId",object.getProjectDeptId());
			ProjectDept oldProjectDept=projectDeptDao.queryObject(queryParams);
			if(!oldProjectDept.getGroupId().equals(sessionUser.getCurrentJob().getGroupId())||
					!oldProjectDept.getComId().equals(sessionUser.getCurrentJob().getComId())) {
				return JsonResult.failure(JsonResult.CODE_REQUEST_INPUT_ERROR, "只能修改所在公司的事业部");
			}
			if(object.getComId().equals(sessionUser.getCurrentJob().getComId())&&object.getGroupId().equals(sessionUser.getCurrentJob().getGroupId())) {
			}else {
				return JsonResult.failure(JsonResult.CODE_REQUEST_INPUT_ERROR, "只能修改所在公司的事业部");
			}
		}else {
			return JsonResult.failure(JsonResult.CODE_REQUEST_INPUT_ERROR, "您没有修改该事业部的权限");
		}
		projectDeptDao.updateObject(object);
		JsonResult result = JsonResult.success();
		result.put("message", "修改成功");
		return result;
	}

}
