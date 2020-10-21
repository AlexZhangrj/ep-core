package com.zhrenjie04.alex.manager.controller.back;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zhrenjie04.alex.core.AbstractGenericController;
import com.zhrenjie04.alex.core.FilterWithNoneFiltered;
import com.zhrenjie04.alex.core.JsonResult;
import com.zhrenjie04.alex.core.Permission;
import com.zhrenjie04.alex.core.ResponseJson;
import com.zhrenjie04.alex.core.ResponseJsonWithFilter;
import com.zhrenjie04.alex.core.TClazzLocal;
import com.zhrenjie04.alex.core.User;
import com.zhrenjie04.alex.manager.dao.UserDao;
import com.zhrenjie04.alex.manager.service.UserService;

/**
 * @author 张人杰
 */
@Controller
@RequestMapping("/user/back/user")
@Permission("user.back")
public class UserBackController extends AbstractGenericController<User, UserDao, UserService> {

	@Autowired
	UserService userService;

	@Override
	protected UserService getService() {
		return userService;
	}

	@RequestMapping(value = "/search-by-username/{username}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	@Permission("page-query")
	@ResponseJsonWithFilter(type = User.class, include = "userId,username,realname,nickname,email,cellphone,gender,isLocked,birthday,portraitUrl,groupId,createdTime,otherResults")
	public JsonResult searchByUsername(HttpServletRequest request,
			@PathVariable(name = "username", required = true) String username) {
		User user=(User)request.getAttribute("user");
		HashMap<String, Object> params = new HashMap<String, Object>(16);
		params.put("username", username);
		User object = userService.queryObject(params);
		if (user.hasPrivilege("manager:user.back.page-query-all-groups")
				&& ROOT_GROUP_ID.equals(user.getGroupId())
				&& ROOT_GROUP_ID.equals(user.getCurrentJob().getGroupId())) {
		}else {
			if(object.getRealname().length()>0) {
				object.setRealname('*'+object.getRealname().substring(1));
			}
		}
		TClazzLocal.set(tClazz);
		JsonResult result=new JsonResult();
		result.put("user", object);
		return result;
	}

	@RequestMapping(value = "/search-by-cellphone/{cellphone}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	@Permission("page-query")
	@ResponseJsonWithFilter(type = User.class, include = "userId,username,realname,nickname,email,cellphone,gender,isLocked,birthday,portraitUrl,groupId,createdTime,otherResults")
	public JsonResult searchBycellphone(HttpServletRequest request,
			@PathVariable(name = "cellphone", required = true) String cellphone) {
		User user=(User)request.getAttribute("user");
		HashMap<String, Object> params = new HashMap<String, Object>(16);
		params.put("cellphone", cellphone);
		User object = userService.queryObject(params);
		JsonResult result=new JsonResult();
		if (user.hasPrivilege("manager:user.back.page-query-all-groups")
				&& ROOT_GROUP_ID.equals(user.getGroupId())
				&& ROOT_GROUP_ID.equals(user.getCurrentJob().getGroupId())) {
		}else {
			if(object.getRealname().length()>0) {
				object.setRealname('*'+object.getRealname().substring(1));
			}
		}
		TClazzLocal.set(tClazz);
		result.put("user", object);
		return result;
	}

	@Override
	@RequestMapping(value = "/page/search-{keyword}/order-by-{orderBy}/order-type-{orderType}/page-no/{pageNo}/page-size/{pageSize}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	@Permission("page-query")
	@ResponseJsonWithFilter(type = User.class, include = "userId,username,realname,nickname,email,cellphone,gender,isLocked,birthday,portraitUrl,groupId,createdTime,otherResults,bigDecimalTag")
	public JsonResult pageQueryAll(HttpServletRequest request,
			@PathVariable(name = "keyword", required = true) String keyword,
			@PathVariable(name = "orderBy", required = false) String orderBy,
			@PathVariable(name = "orderType", required = false) String orderType,
			@PathVariable(name = "pageNo", required = true) Long pageNo,
			@PathVariable(name = "pageSize", required = true) Long pageSize) {
		User user=(User)request.getAttribute("user");
		HashMap<String, Object> params = new HashMap<String, Object>(16);
		params.put("keyword", keyword);
		String orderByFrontend = orderBy;
		switch (orderBy) {
		case "userId":
			orderBy = "u.user_id";
			break;
		case "username":
			orderBy = "username";
			break;
		case "realname":
			orderBy = "realname";
			break;
		case "nickname":
			orderBy = "nickname";
			break;
		case "email":
			orderBy = "email";
			break;
		case "cellphone":
			orderBy = "cellphone";
			break;
		case "gender":
			orderBy = "gender";
			break;
		case "isLocked":
			orderBy = "is_locked";
			break;
		case "createdTime":
			orderBy = "created_time";
			break;
		default:
			orderBy = "u.user_id";
			orderByFrontend = "userId";
		}
		switch (orderType) {
		case "asc":
			orderType = "asc";
			break;
		case "desc":
			orderType = "desc";
			break;
		default:
			orderType = "asc";
		}
		params.put("orderByFrontend", orderByFrontend);
		params.put("orderBy", orderBy);
		params.put("orderType", orderType);
		params.put("pageNo", pageNo < 1 ? 1L : pageNo);
		if (pageSize > MAX_PAGESIZE) {
			pageSize = MAX_PAGESIZE;
		}
		if (pageSize < MIN_PAGESIZE) {
			pageSize = MIN_PAGESIZE;
		}
		params.put("pageSize", pageSize);
		TClazzLocal.set(tClazz);
		JsonResult result=getService().pageQueryAll(params, user);
		//本公司的人的手机号、真实姓名、头像，本公司的管理员都能看
		//公司添加账号需要改为：录入手机号，发送邀请
//		if (user.hasPrivilege("manager:user.back.page-query-all-groups")
//				&& ROOT_GROUP_ID.equals(user.getGroupId())
//				&& ROOT_GROUP_ID.equals(user.getCurrentJob().getGroupId())) {
//		}else {
//			List<User> list=(List<User>)result.get("list");
//			for(int i=0;i<list.size();i++) {
//				if(list.get(i).getRealname()!=null&&list.get(i).getRealname().length()>0) {
//					list.get(i).setRealname("*"+list.get(i).getRealname().substring(1));
//				}
//			}
//		}		
		return result;
	}

	@Override
	@RequestMapping(value = "/", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@Permission("add")
	@ResponseJson
	public JsonResult insertObject(HttpServletRequest request, @RequestBody @Validated User object,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			StringBuilder sb = new StringBuilder();
			for (FieldError error : bindingResult.getFieldErrors()) {
				sb.append(error.getDefaultMessage()).append(";");
			}
			sb.deleteCharAt(sb.length() - 1);
			throw new RuntimeException(sb.toString());
		}
		User user=(User)request.getAttribute("user");
		TClazzLocal.set(tClazz);
		return userService.insertObject(object, user);
	}

	@Override
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = "application/json;charset=UTF-8")
	@Permission("modify")
	@ResponseJsonWithFilter(type = FilterWithNoneFiltered.class)
	public JsonResult updateObject(HttpServletRequest request, @PathVariable(name = "id", required = true) String id,
			@RequestBody @Validated User object, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			StringBuilder sb = new StringBuilder();
			for (FieldError error : bindingResult.getFieldErrors()) {
				sb.append(error.getDefaultMessage()).append(";");
			}
			sb.deleteCharAt(sb.length() - 1);
			throw new RuntimeException(sb.toString());
		}
		User user=(User)request.getAttribute("user");
		TClazzLocal.set(tClazz);
		if (!ROOT_GROUP_ID.equals(user.getCurrentJob().getGroupId())) {
			// 非根系统用户只能修改本集团帐号
			object.setGroupId(user.getCurrentJob().getGroupId());
		}
		return getService().updateObject(object, user);
	}

	@RequestMapping(value = "/{id}/lock", method = RequestMethod.PUT, produces = "application/json;charset=UTF-8")
	@Permission("lock")
	@ResponseJsonWithFilter(type = FilterWithNoneFiltered.class)
	public JsonResult lock(HttpServletRequest request, @PathVariable(name = "id", required = true) String id) {
		User user=(User)request.getAttribute("user");
		TClazzLocal.set(tClazz);
		User object = new User();
		object.setUserId(id);
		object.setIsLocked(true);
		if (!ROOT_GROUP_ID.equals(user.getCurrentJob().getGroupId())) {
			// 非根系统用户只能修改本集团帐号
			object.setGroupId(user.getCurrentJob().getGroupId());
		}
		return getService().lock(object, user);
	}

	@RequestMapping(value = "/{id}/unlock", method = RequestMethod.PUT, produces = "application/json;charset=UTF-8")
	@Permission("unlock")
	@ResponseJsonWithFilter(type = FilterWithNoneFiltered.class)
	public JsonResult unlock(HttpServletRequest request, @PathVariable(name = "id", required = true) String id) {
		User user=(User)request.getAttribute("user");
		TClazzLocal.set(tClazz);
		User object = new User();
		object.setUserId(id);
		object.setIsLocked(false);
		if (!ROOT_GROUP_ID.equals(user.getCurrentJob().getGroupId())) {
			// 非根系统用户只能修改本集团帐号
			object.setGroupId(user.getCurrentJob().getGroupId());
		}
		return getService().unlock(object, user);
	}
}
