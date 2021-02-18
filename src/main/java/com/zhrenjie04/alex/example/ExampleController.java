package com.zhrenjie04.alex.example;

import com.zhrenjie04.alex.core.JsonResult;
import com.zhrenjie04.alex.core.Permission;
import com.zhrenjie04.alex.core.ResponseJsonWithFilter;
import com.zhrenjie04.alex.core.User;
import com.zhrenjie04.alex.util.SessionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 张人杰
 */
@Controller
@RequestMapping("/api/user")
@Permission("api")
@Slf4j
public class ExampleController {

	@RequestMapping(value = "/login/get-current-user", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	@Permission("get-current-user")
	@ResponseJsonWithFilter(type = User.class, include = "userId,username,realname,nickname,email,cellphone,portraitUrl,birthday,gender,"
			+ "identities,orgIdentities,currentIdentityId,currentPrivilegeCodes,currentRoleIds,currentIdentity")
	public JsonResult getCurrentUserInfo(HttpServletRequest request, HttpServletResponse response) throws InterruptedException {
		User user=SessionUtil.getSessionUser(request);
		JsonResult rt = JsonResult.success();
		rt.put("user", user);
		return rt;
	}
	@RequestMapping(value = "/login/get-current-user-with-exlude", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	@Permission("get-current-user")
	@ResponseJsonWithFilter(type = User.class, exclude = "password,oldPassword")
	public JsonResult getCurrentUserInfoWithExclude(HttpServletRequest request, HttpServletResponse response) throws InterruptedException {
		User user=SessionUtil.getSessionUser(request);
		JsonResult rt = JsonResult.success();
		rt.put("user", user);
		return rt;
	}
}
