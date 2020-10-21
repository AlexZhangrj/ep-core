package com.zhrenjie04.alex.manager.controller.fore;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.code.kaptcha.Producer;
import com.zhrenjie04.alex.core.JsonResult;
import com.zhrenjie04.alex.core.Permission;
import com.zhrenjie04.alex.core.ResponseJsonWithFilter;
import com.zhrenjie04.alex.core.User;
import com.zhrenjie04.alex.manager.service.UserService;
import com.zhrenjie04.alex.util.RedisUtil;
import com.zhrenjie04.alex.util.SessionUtil;

/**
 * @author 张人杰
 */
@Controller
@RequestMapping("/user/fore")
@Permission("fore")
public class RegisterForeController {
	private final static Logger logger = LoggerFactory.getLogger(RegisterForeController.class);

	@Autowired
	UserService userService;
	@Autowired
	private Producer captchaProducer;

	@RequestMapping(value = "/register", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@Permission("register")
	@ResponseJsonWithFilter(type = User.class, include = "userId,username,realname,nickname,email,cellphone,portraitUrl,birthday,gender")
	public JsonResult register(HttpServletRequest request, @RequestBody @Validated User object,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			StringBuilder sb = new StringBuilder();
			for (FieldError error : bindingResult.getFieldErrors()) {
				sb.append(error.getDefaultMessage()).append(";");
			}
			sb.deleteCharAt(sb.length() - 1);
			throw new RuntimeException(sb.toString());
		}
//		String capText = SessionUtil.getSession(request, "capText", String.class);
//		SessionUtil.setSession(request, "capText", "");
		String sid=(String)request.getAttribute("sid");
		String capText = RedisUtil.get("validate-code:"+sid);
		RedisUtil.set("validate-code:"+sid, "", 3*60);
		if (capText != null && !capText.equals(object.getCaptcha()) || "".equals(capText) || capText == null) {
			return JsonResult.failure(JsonResult.CODE_PREREQUISITE_NOT_SATISFIED, "验证码不正确，请点击验证码刷新");
		}
		object.setGroupId("5000000");
		userService.insertObject(object);
		return JsonResult.success();
	}

	@RequestMapping(value = "/register/validate-code", method = RequestMethod.GET)
	@Permission("register")
	public void genValidateCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		response.setDateHeader("Expires", 0);
		response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
		response.addHeader("Cache-Control", "post-check=0, pre-check=0");
		response.setHeader("Pragma", "no-cache");
		response.setContentType("image/jpeg");
		String capText = captchaProducer.createText();
//		SessionUtil.setSession(request, "capText", capText);
		String sid=(String)request.getAttribute("sid");
		RedisUtil.set("validate-code:"+sid, capText, 3*60);
		BufferedImage bi = captchaProducer.createImage(capText);
		ServletOutputStream out = response.getOutputStream();
		ImageIO.write(bi, "jpg", out);
		try {
			out.flush();
		} finally {
//			out.close();
		}
	}
}
