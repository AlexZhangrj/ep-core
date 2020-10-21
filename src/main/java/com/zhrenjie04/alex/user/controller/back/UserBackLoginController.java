package com.zhrenjie04.alex.user.controller.back;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.google.code.kaptcha.Producer;
import com.zhrenjie04.alex.core.FilterWithNoneFiltered;
import com.zhrenjie04.alex.core.Identity;
import com.zhrenjie04.alex.core.JsonResult;
import com.zhrenjie04.alex.core.MenuItem;
import com.zhrenjie04.alex.core.Permission;
import com.zhrenjie04.alex.core.ResponseJson;
import com.zhrenjie04.alex.core.ResponseJsonWithFilter;
import com.zhrenjie04.alex.core.User;
import com.zhrenjie04.alex.core.exception.PrerequisiteNotSatisfiedException;
import com.zhrenjie04.alex.core.exception.UnauthorizedException;
import com.zhrenjie04.alex.user.dao.UserDao;
import com.zhrenjie04.alex.user.service.UserService;
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

	private static final Logger logger = LoggerFactory.getLogger(UserBackLoginController.class);

	@Autowired
	private Producer captchaProducer;
	@Autowired
	UserService userService;
	@Autowired
	UserDao userDao;
	@Value("${session.session-keep-time-seconds}")
	private int sessionKeepTimeSeconds = 10;

	@RequestMapping(value = "/login/validate-code", method = RequestMethod.GET)
	@Permission("login.do-login")
	public void genValidateCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		response.setDateHeader("Expires", 0);
		response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
		response.addHeader("Cache-Control", "post-check=0, pre-check=0");
		response.setHeader("Pragma", "no-cache");
		response.setContentType("image/jpeg");
		String capText = captchaProducer.createText();
//		SessionUtil.setSession(request, "backLoginCapText", capText);
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
//		String capText = SessionUtil.getSession(request, "backLoginCapText", String.class);
//		SessionUtil.setSession(request, "backLoginCapText", "");
		String sid=(String)request.getAttribute("sid");
		String capText = RedisUtil.get("validate-code:"+sid);
		RedisUtil.set("validate-code:"+sid, "", 3*60);
		if (capText != null && !capText.equals(account.getCaptcha()) || "".equals(capText) || capText == null) {
			throw new PrerequisiteNotSatisfiedException("验证码不正确，请点击验证码刷新");
		}
		HashMap<String, Object> params = new HashMap<String, Object>(16);
		params.put("username", account.getUsername());
		User user = userDao.queryObject(params);
		if (user == null) {
			throw new PrerequisiteNotSatisfiedException("该用户不存在");
			// 密码经过盐值加密判断：
		} else if (account.getPassword() != null && !"".equals(account.getPassword())
				&& Md5Util.encrypt(account.getPassword() + user.getSalt()).equals(user.getPassword())) {
			// 获取jobs列表
			user.setJobs(userService.queryJobs(user));
			// 指定第一个job
			if (user.getJobs() != null && !user.getJobs().isEmpty()) {
				Identity job = user.getJobs().get(0);
				user.setCurrentJobId(job.getJobId());
				user.setPrivilegeCodes(userDao.queryUserPrivilegeCodes(job));
				user.setMenuLinks(userService.queryUserMenuLinks(job));
				user.setCurrentRoleIds(userDao.queryUserRoleIds(job));
			}
//			String sid = "s-" + UUID.randomUUID();
//			RedisUtil.set(sid, JsonUtil.stringify(user), sessionKeepTimeSeconds);
//			String userSids = "user-sids-" + user.getUserId();
//			RedisUtil.sadd(userSids, sessionKeepTimeSeconds, sid);
//			JsonResult result = new JsonResultSuccess();
//			result.put("authorization", sid);
//			result.put("user", user);
//			Cookie cookie = new Cookie(AuthorizationInterceptor.AUTHORIZATION_HEADER_KEY, sid);
//			cookie.setPath("/");
//			cookie.setMaxAge(-1);
//			response.addCookie(cookie);
//			return result;
			JsonResult result = JsonResult.success();
			user.getOtherParams().put("lastRefreshTokenTime", new Date().getTime());
			user.getOtherParams().put("endLineTime", new Date().getTime()+3*24*3600*1000);
			user.setPassword("");
			user.setSalt("");
//			result.put("token", JwtUtil.encode(JsonUtil.stringify(user)));
			SessionUtil.setSessionUser(request, user);
			result.put("user", user);
			return result;
		} else {
			throw new PrerequisiteNotSatisfiedException("密码错误");
		}
	}
	@RequestMapping(value = "/check-login", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	@Permission("login.do-login")
	public void checkLogin(@RequestParam(name="fromUrl")String fromUrl, @RequestParam(name="token",required=false)String token, @RequestParam(name="logined",required=false)Boolean logined, HttpServletRequest request, HttpServletResponse response) throws IOException {
		User user=(User)request.getAttribute("user");
		if(user==null) {
			if(token!=null&&!"".equals(token)){
				String sid=RedisUtil.get(token);
				RedisUtil.del(token);
				//写客户端cookie
				Cookie cookie = new Cookie("sid", sid);
				cookie.setPath("/");
				cookie.setMaxAge(-1);
				response.addCookie(cookie);
				response.sendRedirect(fromUrl);
			}else{
				if(fromUrl!=null) {
					if(fromUrl.indexOf("?")>-1) {
						response.sendRedirect(fromUrl+"&notLogin=true");
					}else {
						response.sendRedirect(fromUrl+"?notLogin=true");
					}
				}else {
					response.sendRedirect("https://www.up-task.com");
				}
			}
		}else {
			if(token!=null&&!"".equals(token)){
				String sid=RedisUtil.get(token);
				RedisUtil.del(token);
				//写客户端cookie
				Cookie cookie = new Cookie("sid", sid);
				cookie.setPath("/");
				cookie.setMaxAge(-1);
				response.addCookie(cookie);
				response.sendRedirect(fromUrl);
			}else if(logined!=null&&logined==true){
				response.sendRedirect(fromUrl);
			}else{
				String sid=(String)request.getAttribute("sid");
				token="user-"+user.getUserId()+"-token-"+UUID.randomUUID().toString();
				RedisUtil.set(token, sid);
				//公用redis session服务器的单点登录方式
				if(fromUrl.indexOf("?")>-1) {
					fromUrl=fromUrl+"&logined=true";
				}else {
					fromUrl=fromUrl+"?logined=true";
				}
				String hrefPrefix=fromUrl.substring(0, fromUrl.indexOf(".com")+4);
				response.sendRedirect(hrefPrefix+"/user/back/check-login?fromUrl="+URLEncoder.encode(fromUrl, "utf-8")+"&logined=true&token="+token);
			}
		}
	}
	@RequestMapping(value = "/login/do-logout", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	@Permission("login.do-logout")
	@ResponseJsonWithFilter(type = FilterWithNoneFiltered.class)
	public JsonResult tree(HttpServletRequest request) {
		SessionUtil.killSession(request);
		return JsonResult.success();
	}

	@RequestMapping(value = "/index", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	@Permission("login.upload-file")
	public String index() {
		return "index";
	}

	@RequestMapping(value = "/login/upload-file", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@Permission("login.upload-file")
	@ResponseJson
	public JsonResult uploadFile(@RequestParam("imageFile") MultipartFile imageFile) {
		FileOutputStream fos = null;
		try {
			byte[] bytes = imageFile.getBytes();
			File file = new File("D:\\aaa.jpg");
			fos = new FileOutputStream(file);
			fos.write(bytes);
		} catch (IOException e) {
			logger.error("uploadFile", e);
		} finally {
			try {
				if (fos != null) {
					fos.close();
				}
			} catch (IOException e) {
				logger.error("uploadFile fos.close()", e);
			}
		}
		return JsonResult.success();
	}
	@RequestMapping(value = "/current-user", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	@Permission("login.do-login")
	@ResponseJsonWithFilter(type = User.class, include = "userId,username,realname,nickname,email,cellphone,portraitUrl,birthday,gender")
	public JsonResult getCurrentUser(HttpServletRequest request, HttpServletResponse response) {
//		User user=(User)request.getAttribute("user");
//		JsonResult result = new JsonResultSuccess();
//		result.put("user", user);
//		result.put("sid", request.getAttribute("sid"));
//		return result;
		User user=(User)request.getAttribute("user");
		if(user==null) {
			throw new UnauthorizedException("你尚未登录");
		}
		Identity currentJob=user.getCurrentJob();
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
		user.getOtherParams().put("lastRefreshTokenTime", new Date().getTime());
		user.getOtherParams().put("endLineTime", new Date().getTime()+3*24*3600*1000);
		JsonResult result = JsonResult.success();
		result.put("user", user);
		SessionUtil.setSessionUser(request, user);
//		result.put("token", JwtUtil.encode(JsonUtil.stringify(user)));
		return result;
	}
}
