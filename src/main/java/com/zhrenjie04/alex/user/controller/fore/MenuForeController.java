package com.zhrenjie04.alex.user.controller.fore;

import com.zhrenjie04.alex.core.*;
import com.zhrenjie04.alex.user.dao.UserDao;
import com.zhrenjie04.alex.user.service.UserService;
import com.zhrenjie04.alex.util.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 张人杰
 */
@RestController
@RequestMapping("/user/fore")
@Permission("fore.menus")
public class MenuForeController {
	
	@Autowired
	UserService userService;
	@Autowired
	UserDao userDao;
	
	@RequestMapping(value = "/jobs", method = RequestMethod.GET, produces="application/json;charset=UTF-8")
	@Permission("query-jobs")
	@ResponseJsonWithFilter(type=FilterWithNoneFiltered.class)
	public JsonResult jobs(HttpServletRequest request) {
		User user=(User)request.getAttribute("user");
		JsonResult result=JsonResult.success();
		result.put("jobs", user.getJobs());
		result.put("currentJobId", user.getCurrentJobId());
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
		User user=(User)request.getAttribute("user");
		Identity job=new Identity();
		job.setJobId(jobId);
		if(user.getJobs()!=null&&user.getJobs().contains(job)) {
			user.setCurrentJobId(jobId);
			job.setUserId(user.getUserId());
			user.setPrivilegeCodes(userDao.queryUserPrivilegeCodes(job));
			user.setMenuLinks(userService.queryUserMenuLinks(job));
			user.setCurrentRoleIds(userDao.queryUserRoleIds(job));
			SessionUtil.setSessionUser(request, user);
			JsonResult result=JsonResult.success();
			result.put("menus", user.getPrivilegeCodes());
			result.put("menuLinks", user.getMenuLinks());
			return result;
		}else {
			return JsonResult.failure(JsonResult.CODE_REQUEST_INPUT_ERROR,"此用户不存在此jobId："+jobId);
		}
	}
	public static void main(String[] args) {
		int a=10;
		String s1 = "123";
		String s2 = "123";
		String s3 = new String("123");
		String s4 = s3.intern();
		System.out.println(String.format("%s,%s,%s,%s,%s",a,s1 == s2, s1 == s3, s1 == s4, s3 == s4));
		String s="    hello    world   abc  ";
		String[] words=s.split("\\s+");
		StringBuilder sb=new StringBuilder();
		for(String word:words) {
			if("".equals(word)) {
				continue;
			}
			char[] chars=word.toCharArray();
			for(int i=chars.length-1;i>=0;i--) {
				sb.append(chars[i]);
			}
			sb.append(" ");
		}
		if(sb.length()>0) {
			sb.deleteCharAt(sb.length()-1);
		}
		System.out.println(sb.toString());
	}
}
