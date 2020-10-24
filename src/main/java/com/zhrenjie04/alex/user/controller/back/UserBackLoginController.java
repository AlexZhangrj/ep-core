package com.zhrenjie04.alex.user.controller.back;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.code.kaptcha.Producer;
import com.zhrenjie04.alex.core.DbUtil;
import com.zhrenjie04.alex.core.JsonResult;
import com.zhrenjie04.alex.core.Permission;
import com.zhrenjie04.alex.core.ResponseJsonWithFilter;
import com.zhrenjie04.alex.core.User;
import com.zhrenjie04.alex.core.exception.PrerequisiteNotSatisfiedException;
import com.zhrenjie04.alex.manager.domain.EsUserSearchKey;
import com.zhrenjie04.alex.user.dao.UserDao;
import com.zhrenjie04.alex.util.RedisUtil;
import com.zhrenjie04.alex.util.SessionUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * @author 张人杰
 */
@Controller
@RequestMapping("/user/back")
@Permission("back")
@Slf4j
public class UserBackLoginController {
	
	@Autowired
	private Producer captchaProducer;

	@Autowired
	UserDao userDao;
	
	@Autowired
	ElasticsearchRestTemplate esTemplate;


	@RequestMapping(value = "/login/validate-code", method = RequestMethod.GET)
	@Permission("login.do-login")
	public void genValidateCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
		EsUserSearchKey userSearchKey=new EsUserSearchKey();
		userSearchKey.setUserId("1");
		userSearchKey.setUsername("admin");
		userSearchKey.setCellphone("13900001111");
		esTemplate.save(userSearchKey);
		EsUserSearchKey userSearchKey1=new EsUserSearchKey();
		userSearchKey.setUserId("1");
		userSearchKey.setUsername("admin1");
		userSearchKey.setCellphone("13900001111");
		esTemplate.save(userSearchKey1);
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
			QueryBuilder queryBuilder=QueryBuilders.termQuery("username", account.getUsername());//全字段匹配：1.使用FieldType.Keyword，2.使用term
			NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder()
	                //查询条件
	                .withQuery(queryBuilder)
	                //分页
	                .withPageable(PageRequest.of(0, 5))
//	                //排序
//	                .withSort(SortBuilders.fieldSort("userId").order(SortOrder.DESC))
//	                //高亮字段显示
//	                .withHighlightFields(new HighlightBuilder.Field("userId"))
	                .build();
			var result = esTemplate.search(nativeSearchQuery, EsUserSearchKey.class);
			if(result.getTotalHits()==0) {
				throw new PrerequisiteNotSatisfiedException("该账号不存在");
			}
			if(result.getTotalHits()>1) {
				throw new PrerequisiteNotSatisfiedException("查到多个同名账号");
			}
			HashMap<String, Object> params = new HashMap<String, Object>(16);
			params.put("username", account.getUsername());
			//设置数据源
			Integer hashCode=account.getUsername().hashCode();
			DbUtil.setDataSource("usernameKeyDb"+(hashCode%DbUtil.dbCountInGroupMap.get("usernameKeyDb")));
			log.debug(DbUtil.getDataSource());
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
		System.out.println("admin".hashCode()%5);
		System.out.println("user1".hashCode()%5);
		System.out.println("user2".hashCode()%5);
	}
}
