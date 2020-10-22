package com.zhrenjie04.alex.user.controller.back;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.code.kaptcha.Producer;
import com.zhrenjie04.alex.core.DbUtil;
import com.zhrenjie04.alex.core.Identity;
import com.zhrenjie04.alex.core.JsonResult;
import com.zhrenjie04.alex.core.Permission;
import com.zhrenjie04.alex.core.ResponseJsonWithFilter;
import com.zhrenjie04.alex.core.User;
import com.zhrenjie04.alex.core.exception.PrerequisiteNotSatisfiedException;
import com.zhrenjie04.alex.user.dao.UserDao;
import com.zhrenjie04.alex.util.Md5Util;
import com.zhrenjie04.alex.util.RedisUtil;
import com.zhrenjie04.alex.util.SessionUtil;

/**
 * @author 张人杰
 */
@Controller
@RequestMapping("/user/back")
@Permission("back")
public class UserBackLoginController {
	
	@Autowired
	private Producer captchaProducer;

	@Autowired
	UserDao userDao;


	@RequestMapping(value = "/login/validate-code", method = RequestMethod.GET)
	@Permission("login.do-login")
	public void genValidateCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setDateHeader("Expires", 0);
		response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
		response.addHeader("Cache-Control", "post-check=0, pre-check=0");
		response.setHeader("Pragma", "no-cache");
		response.setContentType("image/jpeg");
		String capText = captchaProducer.createText();
		String sid=(String)request.getAttribute("sid");
		RedisUtil.set("validate-code:"+sid, capText, 3*60);
		BufferedImage bi = captchaProducer.createImage(capText);
		ServletOutputStream out = response.getOutputStream();
		ImageIO.write(bi, "jpg", out);
		try {
			out.flush();
		} finally {
			out.close();
		}
	}
	
	@RequestMapping(value = "/login/do-login", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@Permission("login.do-login")
	@ResponseJsonWithFilter(type = User.class, include = "userId,username,realname,nickname,email,cellphone,portraitUrl,birthday,gender")
	public JsonResult login(@RequestBody User account, HttpServletRequest request, HttpServletResponse response) {
		String sid=(String)request.getAttribute("sid");
		String capText = RedisUtil.get("validate-code:"+sid);
		RedisUtil.set("validate-code:"+sid, "", 3*60);
		if (capText != null && !capText.equals(account.getCaptcha()) || "".equals(capText) || capText == null) {
			throw new PrerequisiteNotSatisfiedException("验证码不正确，请点击验证码刷新");
		}
		if(account.getUsername()!=null&&!"".equals(account.getUsername())) {
			//以用户名密码方式登录
			HashMap<String, Object> params = new HashMap<String, Object>(16);
			params.put("username", account.getUsername());
			//设置数据源
			Integer hashCode=account.getUsername().hashCode();
			DbUtil.setDataSource("userNameKeyDb"+(hashCode%DbUtil.dbCountInGroupMap.get("userNameKeyDb")));
			//操作数据库
			User user = userDao.queryObject(params);
			//移除ThreadLoacal变量
			DbUtil.remove();
			if(user != null) {
				if(user.getPassword()!=null&&user.getPassword().equals(account.getPassword())) {
					JsonResult result = JsonResult.success();
					user.getOtherParams().put("lastRefreshTokenTime", new Date().getTime());
					user.getOtherParams().put("endLineTime", new Date().getTime()+3*24*3600*1000);
					user.setPassword("");
					user.setSalt("");
					SessionUtil.setSessionUser(request, user);
					result.put("user", user);
					return result;
				}else {
					throw new PrerequisiteNotSatisfiedException("密码错误");
				}
			}else {
				throw new PrerequisiteNotSatisfiedException("该账号不存在");
			}
		}else if(account.getCellphone()!=null&&!"".equals(account.getCellphone())) {
			//以手机号，验证码方式登录
			throw new PrerequisiteNotSatisfiedException("目前不支持此种登录方式");
		}else {
			throw new PrerequisiteNotSatisfiedException("目前不支持此种登录方式");
		}
	}
	public static void main(String[] args) {
		System.out.println("admin".hashCode());
		System.out.println("admin".hashCode()%2);
	}
}
