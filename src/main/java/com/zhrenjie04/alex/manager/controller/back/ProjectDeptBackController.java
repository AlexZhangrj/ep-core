package com.zhrenjie04.alex.manager.controller.back;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zhrenjie04.alex.core.AbstractGenericController;
import com.zhrenjie04.alex.core.JsonResult;
import com.zhrenjie04.alex.core.Permission;
import com.zhrenjie04.alex.core.ResponseJsonWithFilter;
import com.zhrenjie04.alex.core.ResponseJsonWithFilters;
import com.zhrenjie04.alex.core.TClazzLocal;
import com.zhrenjie04.alex.core.User;
import com.zhrenjie04.alex.manager.dao.ProjectDeptDao;
import com.zhrenjie04.alex.manager.domain.Com;
import com.zhrenjie04.alex.manager.domain.Org;
import com.zhrenjie04.alex.manager.domain.ProjectDept;
import com.zhrenjie04.alex.manager.service.OrgService;
import com.zhrenjie04.alex.manager.service.ProjectDeptService;
/**
 * @author 张人杰
 */
@Controller
@RequestMapping("/user/back/project-dept")
@Permission("project-dept.back")
public class ProjectDeptBackController extends AbstractGenericController<ProjectDept, ProjectDeptDao, ProjectDeptService>{
	
	@Autowired
	ProjectDeptService projectDeptService;

	@Autowired
	OrgService orgService;
	
	@Override
	protected ProjectDeptService getService() {
		return projectDeptService;
	}
	@Deprecated
	@RequestMapping(value = "/trees", method = RequestMethod.GET, produces="application/json;charset=UTF-8")
	@Permission("trees")
	@ResponseJsonWithFilters({
			@ResponseJsonWithFilter(type=Com.class,include="comId,comName,comCode,projectDepts"),
			@ResponseJsonWithFilter(type=ProjectDept.class,include="projectDeptId,projectDeptName,projectCode")
	})	
	public JsonResult tree(HttpServletRequest request) {
		User user=(User)request.getAttribute("user");
		return projectDeptService.trees(user);
	}
	@Override
	@RequestMapping(value = "/page/search-{keyword}/order-by-{orderBy}/order-type-{orderType}/page-no/{pageNo}/page-size/{pageSize}", method = RequestMethod.GET, produces="application/json;charset=UTF-8")
	@Permission("page-query")	
	@ResponseJsonWithFilter(type=ProjectDept.class,include="projectDeptId,projectDeptName,projectDeptCode,otherResults")
	public JsonResult pageQueryAll(HttpServletRequest request,@PathVariable(name="keyword",required=true) String keyword,@PathVariable(name="orderBy",required=false) String orderBy,@PathVariable(name="orderType",required=false) String orderType,@PathVariable(name="pageNo",required=true) Long pageNo,@PathVariable(name="pageSize",required=true) Long pageSize) {
		User user=(User)request.getAttribute("user");
		HashMap<String,Object>params=new HashMap<String,Object>(16);
		params.put("keyword", keyword);
		String orderByFrontend=orderBy;
		switch(orderBy) {
		case "projectDeptId":
			orderBy="project_dept_id";
			break;
		case "projectDeptName":
			orderBy="convert(project_dept_name using gbk)";
			break;
		case "projectDeptCode":
			orderBy="convert(project_dept_code using gbk)";
			break;
		default:
			orderBy="project_dept_id";
			orderByFrontend="projectDeptId";
		}
		switch(orderType) {
		case "asc":
			orderType="asc";
			break;
		case "desc":
			orderType="desc";
			break;
		default:
			orderType="asc";
		}
		params.put("orderByFrontend", orderByFrontend);
		params.put("orderBy", orderBy);
		params.put("orderType", orderType);
		params.put("pageNo", pageNo<1?1L:pageNo);
		if (pageSize > MAX_PAGESIZE) {
			pageSize = MAX_PAGESIZE;
		}
		if (pageSize < MIN_PAGESIZE) {
			pageSize = MIN_PAGESIZE;
		}
		params.put("pageSize", pageSize);
		TClazzLocal.set(tClazz);
		return getService().pageQueryAll(params, user);
	}
	@RequestMapping(value = "/groups/by-keyword-{keyword}", method = RequestMethod.GET, produces="application/json;charset=UTF-8")
	@Permission("page-query")
	@ResponseJsonWithFilter(type=Org.class,include="orgId,orgName")
	public JsonResult queryGroups(HttpServletRequest request,@PathVariable(name="keyword",required=true) String keyword) {
		User sessionUser=(User)request.getAttribute("user");
		if(ROOT_GROUP_ID.equals(sessionUser.getCurrentJob().getGroupId())&&sessionUser.getPrivilegeCodes().contains("manager:project-dept.back.add-all-groups")) {
			HashMap<String,Object>params=new HashMap<String,Object>(16);
			params.put("toFindGroups", true);
			params.put("groupNameLike", keyword);
			List<Org> groups=orgService.queryAll(params);
			JsonResult result=JsonResult.success();
			result.put("groups", groups);
			return result;
		}else {
			HashMap<String,Object>params=new HashMap<String, Object>(16);
			params.put("orgId", sessionUser.getCurrentJob().getGroupId());
			List<Org> groups=orgService.queryAll(params);
			JsonResult result=JsonResult.success();
			result.put("groups", groups);
			return result;
		}
	}
}
