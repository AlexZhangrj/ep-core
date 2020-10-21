package com.zhrenjie04.alex.manager.controller.back;

import java.util.LinkedList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.zhrenjie04.alex.core.FilterWithNoneFiltered;
import com.zhrenjie04.alex.core.Job;
import com.zhrenjie04.alex.core.JsonResult;
import com.zhrenjie04.alex.core.MenuItem;
import com.zhrenjie04.alex.core.Permission;
import com.zhrenjie04.alex.core.ResponseJsonWithFilter;
import com.zhrenjie04.alex.core.User;
import com.zhrenjie04.alex.manager.dao.UserDao;
import com.zhrenjie04.alex.manager.service.UserService;
import com.zhrenjie04.alex.util.SessionUtil;
/**
 * @author 张人杰
 */
@RestController
@RequestMapping("/user/back")
@Permission("menus")
public class MenuBackController {
	
	@Autowired
	UserService userService;
	@Autowired
	UserDao userDao;
	
	@RequestMapping(value = "/jobs", method = RequestMethod.GET, produces="application/json;charset=UTF-8")
	@Permission("query-jobs")
	@ResponseJsonWithFilter(type=FilterWithNoneFiltered.class)
	public JsonResult jobs(HttpServletRequest request) {
//		User user=(User)request.getAttribute("user");
//		JsonResult result=new JsonResultSuccess();
//		result.put("jobs", user.getJobs());
//		result.put("currentJobId", user.getCurrentJobId());
//		return result;
		User user=(User)request.getAttribute("user");
		Job currentJob=user.getCurrentJob();
		// 以下刷新权限
		// 获取jobs列表
		user.setJobs(userService.queryJobs(user));
		// 指定currentJob
		if (user.getJobs() != null && !user.getJobs().isEmpty()&&user.getJobs().contains(currentJob)) {
			user.setCurrentJobId(currentJob.getJobId());
			user.setPrivilegeCodes(userDao.queryUserPrivilegeCodes(currentJob));
			user.setMenuLinks(userService.queryUserMenuLinks(currentJob));
			user.setCurrentRoleIds(userDao.queryUserRoleIds(currentJob));
		} else {
			user.setCurrentJobId("0");
			user.setPrivilegeCodes(new LinkedList<String>());
			user.setMenuLinks(new LinkedList<MenuItem>());
			user.setCurrentRoleIds(new LinkedList<String>());
		}
//		user.getOtherParams().put("lastRefreshTokenTime", new Date().getTime());
//		user.getOtherParams().put("endLineTime", new Date().getTime()+3*24*3600*1000);
		JsonResult result = JsonResult.success();
		result.put("jobs", user.getJobs());
		result.put("currentJobId", user.getCurrentJobId());
//		result.put("token", JwtUtil.encode(JsonUtil.stringify(user)));
		SessionUtil.setSessionUser(request, user);
		return result;

	}
	@RequestMapping(value = "/menu-links", method = RequestMethod.GET, produces="application/json;charset=UTF-8")
	@Permission("query-menu-codes")
	@ResponseJsonWithFilter(type=FilterWithNoneFiltered.class)
	public JsonResult sidebarMenus(HttpServletRequest request) {
		User user=(User)request.getAttribute("user");
		JsonResult result=JsonResult.success();
		result.put("menuLinks", user.getMenuLinks());
		return result;
	}

	@RequestMapping(value = "/menu-codes", method = RequestMethod.GET, produces="application/json;charset=UTF-8")
	@Permission("query-menu-codes")
	@ResponseJsonWithFilter(type=FilterWithNoneFiltered.class)
	public JsonResult menuCodes(HttpServletRequest request) {
		User user=(User)request.getAttribute("user");
		JsonResult result=JsonResult.success();
		result.put("menus", user.getPrivilegeCodes());
		return result;
	}

	@RequestMapping(value = "/switch-job/{jobId}", method = RequestMethod.GET, produces="application/json;charset=UTF-8")
	@Permission("switch-job")
	@ResponseJsonWithFilter(type=FilterWithNoneFiltered.class)
	public JsonResult switchJob(HttpServletRequest request,@PathVariable(name="jobId",required=true)String jobId) {
//		User user=(User)request.getAttribute("user");
//		Job job=new Job();
//		job.setJobId(jobId);
//		if(user.getJobs()!=null&&user.getJobs().contains(job)) {
//			user.setCurrentJobId(jobId);
//			job.setUserId(user.getUserId());
//			user.setPrivilegeCodes(userDao.queryUserPrivilegeCodes(job));
//			user.setMenuLinks(userService.queryUserMenuLinks(job));
//			user.setCurrentRoleIds(userDao.queryUserRoleIds(job));
//			SessionUtil.setSessionUser(request, user);
//			JsonResult result=new JsonResultSuccess();
//			result.put("menus", user.getPrivilegeCodes());
//			result.put("menuLinks", user.getMenuLinks());
//			return result;
//		}else {
//			JsonResult result=new JsonResultFailure(JsonResult.CODE_REQUEST_INPUT_ERROR,"此用户不存在此jobId："+jobId);
//			return result;
//		}
		User user=(User)request.getAttribute("user");
		Job job=new Job();
		job.setJobId(jobId);
		// 以下刷新权限
		// 获取jobs列表
		user.setJobs(userService.queryJobs(user));
		// 指定currentJob
		if (user.getJobs() != null && !user.getJobs().isEmpty()&&user.getJobs().contains(job)) {
			user.setCurrentJobId(jobId);
			job.setUserId(user.getUserId());
			user.setPrivilegeCodes(userDao.queryUserPrivilegeCodes(job));
			user.setMenuLinks(userService.queryUserMenuLinks(job));
			user.setCurrentRoleIds(userDao.queryUserRoleIds(job));
//			SessionUtil.setSessionUser(request, user);
			JsonResult result=JsonResult.success();
			result.put("menus", user.getPrivilegeCodes());
			result.put("menuLinks", user.getMenuLinks());
//			result.put("token", JwtUtil.encode(JsonUtil.stringify(user)));
			SessionUtil.setSessionUser(request, user);
			return result;
		}else {
			return JsonResult.failure(JsonResult.CODE_REQUEST_INPUT_ERROR,"此用户不存在此jobId："+jobId);
		}
	}
}
