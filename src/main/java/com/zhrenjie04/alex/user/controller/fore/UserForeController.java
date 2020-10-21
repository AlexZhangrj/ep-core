package com.zhrenjie04.alex.user.controller.fore;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zhrenjie04.alex.core.JsonResult;
import com.zhrenjie04.alex.core.Permission;
import com.zhrenjie04.alex.core.ResponseJsonWithFilter;
import com.zhrenjie04.alex.core.ResponseJson;
import com.zhrenjie04.alex.core.User;
import com.zhrenjie04.alex.user.service.UserService;
import com.zhrenjie04.alex.util.Md5Util;
import com.zhrenjie04.alex.util.SessionUtil;

/**
 * @author 张人杰
 */
@Controller
@RequestMapping("/user/fore")
@Permission("fore")
public class UserForeController {
	private final static Logger logger = LoggerFactory.getLogger(UserForeController.class);

	@Autowired
	UserService userService;

	@RequestMapping(value = "/user-info", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@Permission("modify-user-info")
	@ResponseJson
	public JsonResult modifyUserInfo(HttpServletRequest request, @RequestBody @Validated User object,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			StringBuilder sb = new StringBuilder();
			int count = 0;
			for (FieldError error : bindingResult.getFieldErrors()) {
				if ("password".equals(error.getField()) && object.getNotChangePassword()) {
					continue;
				}
				++count;
				sb.append(error.getDefaultMessage()).append(";");
			}
			if (count > 0) {
				sb.deleteCharAt(sb.length() - 1);
				throw new RuntimeException(sb.toString());
			}
		}
		User currentUser=(User)request.getAttribute("user");
		if (!currentUser.getUserId().equals(object.getUserId())) {
			throw new RuntimeException("您不能修改其他人的个人信息");
		}
		if (!currentUser.getUsername().equals(object.getUsername())) {
			throw new RuntimeException("用户名不能修改");
		}
		// 验证旧密码
		User oldUser = userService.queryObjectById(object.getUserId());
		if (oldUser == null || oldUser.getIsLocked() || oldUser.getIsDeleted()) {
			throw new RuntimeException("您的账号已被锁定，不能修改");
		}
		if (!Md5Util.encrypt(object.getOldPassword() + oldUser.getSalt()).equals(oldUser.getPassword())) {
			throw new RuntimeException("您输入的旧密码不正确");
		}
		// 更新
		userService.updateObject(object);
		return JsonResult.success();
	}
	/**
	 * 用于PC客户端搜索好友
	 * @param request
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/search-user", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@Permission("search-user")
	@ResponseJsonWithFilter(type = User.class, include = "userId,username,realname,nickname,portraitUrl,birthday,gender")
	public JsonResult searchUser(HttpServletRequest request, @RequestBody HashMap<String, Object> params) {
		if (StringUtils.isEmpty((String) params.get("username")) && StringUtils.isEmpty((String) params.get("realname"))
				&& StringUtils.isEmpty((String) params.get("nickname"))
				&& StringUtils.isEmpty((String) params.get("cellphone"))
				&& StringUtils.isEmpty((String) params.get("email"))
				&& StringUtils.isEmpty((String) params.get("birthday"))) {
			throw new RuntimeException("请输入搜索条件！");
		}
		User sessionUser=(User)request.getAttribute("user");
		if (StringUtils.isEmpty((String) params.get("username"))) {
			params.remove("username");
		} else {
			params.put("username", ((String) params.get("username")).replaceAll("%", ""));
			if (StringUtils.isEmpty((String) params.get("username"))) {
				params.remove("username");
			}
		}
		if (!StringUtils.isEmpty((String) params.get("realname"))) {
			params.put("realname", ((String) params.get("realname")).replaceAll("%", ""));
			if (!StringUtils.isEmpty((String) params.get("realname"))) {
				params.put("orderBy", "realname");
				params.put("orderType", "asc");
			} else {
				params.remove("realname");
			}
		} else {
			params.remove("realname");
		}
		if (!StringUtils.isEmpty((String) params.get("nickname"))) {
			params.put("nickname", ((String) params.get("nickname")).replaceAll("%", ""));
			if (!StringUtils.isEmpty((String) params.get("nickname"))) {
				params.put("orderBy", "nickname");
				params.put("orderType", "asc");
			} else {
				params.remove("realname");
			}
		} else {
			params.remove("nickname");
		}
		if (StringUtils.isEmpty((String) params.get("email"))) {
			params.remove("email");
		} else {
			params.put("email", ((String) params.get("email")).replaceAll("%", ""));
			if (StringUtils.isEmpty((String) params.get("email"))) {
				params.remove("email");
			}
		}
		if (StringUtils.isEmpty((String) params.get("cellphone"))) {
			params.remove("cellphone");
		} else {
			params.put("cellphone", ((String) params.get("cellphone")).replaceAll("%", ""));
			if (StringUtils.isEmpty((String) params.get("cellphone"))) {
				params.remove("cellphone");
			}
		}
		if (StringUtils.isEmpty((String) params.get("birthday"))) {
			params.remove("birthday");
		} else {
			params.put("birthday", ((String) params.get("birthday")).replaceAll("%", ""));
			if (StringUtils.isEmpty((String) params.get("birthday"))) {
				params.remove("birthday");
			}
		}
		if (StringUtils.isEmpty((String) params.get("username")) && StringUtils.isEmpty((String) params.get("realname"))
				&& StringUtils.isEmpty((String) params.get("nickname"))
				&& StringUtils.isEmpty((String) params.get("cellphone"))
				&& StringUtils.isEmpty((String) params.get("email"))
				&& StringUtils.isEmpty((String) params.get("birthday"))) {
			throw new RuntimeException("请输入搜索条件！");
		}
		params.put("search", true);
		return userService.pageSearchAll(params, sessionUser);
	}

	/**
	 * 用于手机客户端搜索好友
	 * @param request
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/search-user-mobile", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@Permission("search-user")
	@ResponseJsonWithFilter(type = User.class, include = "userId,username,realname,nickname,portraitUrl,birthday,gender,email")
	public JsonResult searchUserMobile(HttpServletRequest request, @RequestBody HashMap<String, Object> params) {
		if (StringUtils.isEmpty((String) params.get("keyword"))) {
			throw new RuntimeException("请输入搜索条件！");
		}
		params.put("pageNo",1);//手机端只显示前5条可添加为好友的用户信息
		User sessionUser=(User)request.getAttribute("user");
		return userService.pageSearchAll(params, sessionUser);
	}

	@RequestMapping(value = "/user/{id}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	@Permission("search-user")
	@ResponseJsonWithFilter(type = User.class, include = "userId,username,realname,nickname,email,portraitUrl,birthday,gender")
	public JsonResult getUserInfo(HttpServletRequest request, @PathVariable(name = "id") String id) {
		User currentUser=(User)request.getAttribute("user");
		HashMap<String, Object> params = new HashMap<>();
		params.put("id", id);
		return userService.queryObject(params, currentUser);
	}
}
