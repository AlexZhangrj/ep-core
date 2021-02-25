package com.zhrenjie04.alex.example;

import com.zhrenjie04.alex.core.*;
import com.zhrenjie04.alex.core.domain.User;
import com.zhrenjie04.alex.example.domain.UserVoForExampleRegister;
import com.zhrenjie04.alex.util.SessionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

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

	@RequestMapping(value = "/login/get-current-user-with-exlude", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	@Permission("register")
	@ResponseJson
	public JsonResult register(@RequestBody @Valid UserVoForExampleRegister account, BindingResult bindingResult, HttpServletRequest request, HttpServletResponse response){
		if (bindingResult.hasErrors()) {
			String messages = bindingResult.getAllErrors()
					.stream()
					.map(ObjectError::getDefaultMessage)
					.reduce((m1, m2) -> m1 + "；" + m2)
					.orElse("参数输入有误！");
			throw new IllegalArgumentException(messages);
		}
		JsonResult rt = JsonResult.success();
		rt.put("user", account);
		return rt;
	}
}
