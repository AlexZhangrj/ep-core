package com.zhrenjie04.alex.manager.controller.back;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zhrenjie04.alex.core.FilterWithNoneFiltered;
import com.zhrenjie04.alex.core.JsonResult;
import com.zhrenjie04.alex.core.Permission;
import com.zhrenjie04.alex.core.ResponseJsonWithFilter;
import com.zhrenjie04.alex.core.TClazzLocal;
import com.zhrenjie04.alex.core.User;
import com.zhrenjie04.alex.manager.service.JobService;
import com.zhrenjie04.alex.util.SessionUtil;
/**
 * @author 张人杰
 */
@Controller
@RequestMapping("/user/back/job")
@Permission("job.back")
public class JobBackController {

	protected static final Long MAX_PAGESIZE = 200L;
	protected static final Long MIN_PAGESIZE = 10L;
	protected static final String ROOT_GROUP_ID = "0";
	protected static final String ROOT_ORG_ID = "0";

	@Autowired
	JobService jobService;
	
	@RequestMapping(value = "/page/role-{roleId}/search-{keyword}/order-by-{orderBy}/order-type-{orderType}/page-no/{pageNo}/page-size/{pageSize}", method = RequestMethod.GET, produces="application/json;charset=UTF-8")
	@Permission("manage-role-jobs.query")	
	@ResponseJsonWithFilter(type=User.class,include="userId,username,realname,nickname,email,cellphone,gender,isLocked,birthday,otherResults")
	public JsonResult pageQueryAllWithRoleId(HttpServletRequest request,@PathVariable(name="roleId",required=true) String roleId,@PathVariable(name="keyword",required=true) String keyword,@PathVariable(name="orderBy",required=false) String orderBy,@PathVariable(name="orderType",required=false) String orderType,@PathVariable(name="pageNo",required=true) Long pageNo,@PathVariable(name="pageSize",required=true) Long pageSize) {
		User user=(User)request.getAttribute("user");
		HashMap<String,Object>params=new HashMap<String, Object>(16);
		params.put("keyword", keyword);
		params.put("roleId", roleId);
		String orderByFrontend=orderBy;
		switch(orderBy) {
		case "jobId":
			orderBy="juop.job_id";
			break;
		case "userId":
			orderBy="u.user_id";
			break;
		case "username":
			orderBy="username";
			break;
		case "realname":
			orderBy="realname";
			break;
		case "nickname":
			orderBy="nickname";
			break;
		case "email":
			orderBy="email";
			break;
		case "cellphone":
			orderBy="cellphone";
			break;
		case "gender":
			orderBy="gender";
			break;
		case "isLocked":
			orderBy="u.is_locked";
			break;
		default:
			orderBy="u.user_id";
			orderByFrontend="userId";
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
		TClazzLocal.set(User.class);
		JsonResult result=jobService.pageQueryAll(params, user);
		if (user.hasPrivilege("manager:user.back.page-query-all-groups")
				&& ROOT_GROUP_ID.equals(user.getGroupId())
				&& ROOT_GROUP_ID.equals(user.getCurrentJob().getGroupId())) {
		}else {
			List<User> list=(List<User>)result.get("list");
			for(int i=0;i<list.size();i++) {
				if(list.get(i).getRealname()!=null&&list.get(i).getRealname().length()>0) {
					list.get(i).setRealname("*"+list.get(i).getRealname().substring(1));
				}
			}
		}
		return result;
	}
	@RequestMapping(value = "/page/position-{positionId}/search-{keyword}/order-by-{orderBy}/order-type-{orderType}/page-no/{pageNo}/page-size/{pageSize}", method = RequestMethod.GET, produces="application/json;charset=UTF-8")
	@Permission("manage-position-jobs-roles.query")	
	@ResponseJsonWithFilter(type=User.class,include="userId,username,realname,nickname,email,cellphone,gender,isLocked,birthday,otherResults")
	public JsonResult pageQueryAllWithPositionId(HttpServletRequest request,@PathVariable(name="positionId",required=true) String positionId,@PathVariable(name="keyword",required=true) String keyword,@PathVariable(name="orderBy",required=false) String orderBy,@PathVariable(name="orderType",required=false) String orderType,@PathVariable(name="pageNo",required=true) Long pageNo,@PathVariable(name="pageSize",required=true) Long pageSize) {
		User user=(User)request.getAttribute("user");
		HashMap<String,Object>params=new HashMap<String, Object>(16);
		params.put("keyword", keyword);
		params.put("positionId", positionId);
		String orderByFrontend=orderBy;
		switch(orderBy) {
		case "jobId":
			orderBy="juop.job_id";
			break;
		case "userId":
			orderBy="u.user_id";
			break;
		case "username":
			orderBy="username";
			break;
		case "realname":
			orderBy="realname";
			break;
		case "nickname":
			orderBy="nickname";
			break;
		case "email":
			orderBy="email";
			break;
		case "cellphone":
			orderBy="cellphone";
			break;
		case "gender":
			orderBy="gender";
			break;
		case "isLocked":
			orderBy="u.is_locked";
			break;
		default:
			orderBy="u.user_id";
			orderByFrontend="userId";
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
		TClazzLocal.set(User.class);
		JsonResult result=jobService.pageQueryAll(params, user);
		if (user.hasPrivilege("manager:user.back.page-query-all-groups")
				&& ROOT_GROUP_ID.equals(user.getGroupId())
				&& ROOT_GROUP_ID.equals(user.getCurrentJob().getGroupId())) {
		}else {
			List<User> list=(List<User>)result.get("list");
			for(int i=0;i<list.size();i++) {
				if(list.get(i).getRealname()!=null&&list.get(i).getRealname().length()>0) {
					list.get(i).setRealname("*"+list.get(i).getRealname().substring(1));
				}
			}
		}
		return result;

	}
	@RequestMapping(value = "/page/org-{orgId}/search-{keyword}/order-by-{orderBy}/order-type-{orderType}/page-no/{pageNo}/page-size/{pageSize}", method = RequestMethod.GET, produces="application/json;charset=UTF-8")
	@Permission("manage-org-jobs-roles.query")	
	@ResponseJsonWithFilter(type=User.class,include="userId,username,realname,nickname,email,cellphone,gender,isLocked,birthday,otherResults")
	public JsonResult pageQueryAllWithOrgId(HttpServletRequest request,@PathVariable(name="orgId",required=true) String orgId,@PathVariable(name="keyword",required=true) String keyword,@PathVariable(name="orderBy",required=false) String orderBy,@PathVariable(name="orderType",required=false) String orderType,@PathVariable(name="pageNo",required=true) Long pageNo,@PathVariable(name="pageSize",required=true) Long pageSize) {
		User user=(User)request.getAttribute("user");
		HashMap<String,Object>params=new HashMap<String, Object>(16);
		params.put("keyword", keyword);
		params.put("orgId", orgId);
		String orderByFrontend=orderBy;
		switch(orderBy) {
		case "jobId":
			orderBy="juop.job_id";
			break;
		case "userId":
			orderBy="u.user_id";
			break;
		case "username":
			orderBy="username";
			break;
		case "realname":
			orderBy="realname";
			break;
		case "nickname":
			orderBy="nickname";
			break;
		case "email":
			orderBy="email";
			break;
		case "cellphone":
			orderBy="cellphone";
			break;
		case "gender":
			orderBy="gender";
			break;
		case "isLocked":
			orderBy="u.is_locked";
			break;
		default:
			orderBy="u.user_id";
			orderByFrontend="userId";
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
		TClazzLocal.set(User.class);
		JsonResult result=jobService.pageQueryAll(params, user);
		if (user.hasPrivilege("manager:user.back.page-query-all-groups")
				&& ROOT_GROUP_ID.equals(user.getGroupId())
				&& ROOT_GROUP_ID.equals(user.getCurrentJob().getGroupId())) {
		}else {
			List<User> list=(List<User>)result.get("list");
			for(int i=0;i<list.size();i++) {
				if(list.get(i).getRealname()!=null&&list.get(i).getRealname().length()>0) {
					list.get(i).setRealname("*"+list.get(i).getRealname().substring(1));
				}
			}
		}
		return result;
	}
	@RequestMapping(value = "/page/user-{userId}/search-{keyword}/order-by-{orderBy}/order-type-{orderType}/page-no/{pageNo}/page-size/{pageSize}", method = RequestMethod.GET, produces="application/json;charset=UTF-8")
	@Permission("manage-user-jobs-roles.query")	
	@ResponseJsonWithFilter(type=User.class,include="userId,username,realname,nickname,email,cellphone,gender,isLocked,birthday,otherResults")
	public JsonResult pageQueryAllWithUserId(HttpServletRequest request,@PathVariable(name="userId",required=true) String userId,@PathVariable(name="keyword",required=true) String keyword,@PathVariable(name="orderBy",required=false) String orderBy,@PathVariable(name="orderType",required=false) String orderType,@PathVariable(name="pageNo",required=true) Long pageNo,@PathVariable(name="pageSize",required=true) Long pageSize) {
		User user=(User)request.getAttribute("user");
		HashMap<String,Object>params=new HashMap<String, Object>(16);
		params.put("keyword", keyword);
		params.put("userId", userId);
		String orderByFrontend=orderBy;
		switch(orderBy) {
		case "jobId":
			orderBy="juop.job_id";
			break;
		case "userId":
			orderBy="u.user_id";
			break;
		case "username":
			orderBy="username";
			break;
		case "realname":
			orderBy="realname";
			break;
		case "nickname":
			orderBy="nickname";
			break;
		case "email":
			orderBy="email";
			break;
		case "cellphone":
			orderBy="cellphone";
			break;
		case "gender":
			orderBy="gender";
			break;
		case "isLocked":
			orderBy="u.is_locked";
			break;
		default:
			orderBy="u.user_id";
			orderByFrontend="userId";
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
		TClazzLocal.set(User.class);
		JsonResult result=jobService.pageQueryAll(params, user);
		if (user.hasPrivilege("manager:user.back.page-query-all-groups")
				&& ROOT_GROUP_ID.equals(user.getGroupId())
				&& ROOT_GROUP_ID.equals(user.getCurrentJob().getGroupId())) {
		}else {
			List<User> list=(List<User>)result.get("list");
			for(int i=0;i<list.size();i++) {
				if(list.get(i).getRealname()!=null&&list.get(i).getRealname().length()>0) {
					list.get(i).setRealname("*"+list.get(i).getRealname().substring(1));
				}
			}
		}
		return result;
	}
	@RequestMapping(value = "/page/search-{keyword}/order-by-{orderBy}/order-type-{orderType}/page-no/{pageNo}/page-size/{pageSize}", method = RequestMethod.GET, produces="application/json;charset=UTF-8")
	@Permission("integration-browse")	
	@ResponseJsonWithFilter(type=User.class,include="userId,username,realname,nickname,email,cellphone,gender,isLocked,birthday,otherResults")
	public JsonResult pageQueryAll(HttpServletRequest request,@PathVariable(name="keyword",required=true) String keyword,@PathVariable(name="orderBy",required=false) String orderBy,@PathVariable(name="orderType",required=false) String orderType,@PathVariable(name="pageNo",required=true) Long pageNo,@PathVariable(name="pageSize",required=true) Long pageSize) {
		User user=(User)request.getAttribute("user");
		HashMap<String,Object>params=new HashMap<String, Object>(16);
		params.put("keyword", keyword);
		String orderByFrontend=orderBy;
		switch(orderBy) {
		case "jobId":
			orderBy="juop.job_id";
			break;
		case "userId":
			orderBy="u.user_id";
			break;
		case "username":
			orderBy="username";
			break;
		case "realname":
			orderBy="realname";
			break;
		case "nickname":
			orderBy="nickname";
			break;
		case "email":
			orderBy="email";
			break;
		case "cellphone":
			orderBy="cellphone";
			break;
		case "gender":
			orderBy="gender";
			break;
		case "isLocked":
			orderBy="u.is_locked";
			break;
		default:
			orderBy="u.user_id";
			orderByFrontend="userId";
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
		TClazzLocal.set(User.class);
		JsonResult result=jobService.pageQueryAll(params, user);
		if (user.hasPrivilege("manager:user.back.page-query-all-groups")
				&& ROOT_GROUP_ID.equals(user.getGroupId())
				&& ROOT_GROUP_ID.equals(user.getCurrentJob().getGroupId())) {
		}else {
			List<User> list=(List<User>)result.get("list");
			for(int i=0;i<list.size();i++) {
				if(list.get(i).getRealname()!=null&&list.get(i).getRealname().length()>0) {
					list.get(i).setRealname("*"+list.get(i).getRealname().substring(1));
				}
			}
		}
		return result;
	}
	@RequestMapping(value = "/by-ids/{ids}", method = RequestMethod.DELETE, produces = "application/json;charset=UTF-8")
	@Permission("manage-role-jobs.delete")
	@ResponseJsonWithFilter(type = FilterWithNoneFiltered.class)
	public JsonResult deleteByIds(HttpServletRequest request,
			@PathVariable(name = "ids", required = true) String idsStr) {
		String[] ids = idsStr.split("\\-");
		User user=(User)request.getAttribute("user");
		return jobService.deleteUserJobRoles(ids, user);
	}
}
