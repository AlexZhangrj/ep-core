package com.zhrenjie04.alex.user.controller.back;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

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
import com.google.common.collect.Sets;
import com.zhrenjie04.alex.core.DbUtil;
import com.zhrenjie04.alex.core.FilterWithNoneFiltered;
import com.zhrenjie04.alex.core.Identity;
import com.zhrenjie04.alex.core.JsonResult;
import com.zhrenjie04.alex.core.Permission;
import com.zhrenjie04.alex.core.ResponseJsonWithFilter;
import com.zhrenjie04.alex.core.User;
import com.zhrenjie04.alex.core.exception.PrerequisiteNotSatisfiedException;
import com.zhrenjie04.alex.core.exception.UnauthorizedException;
import com.zhrenjie04.alex.user.dao.GroupDao;
import com.zhrenjie04.alex.user.dao.IdentityDao;
import com.zhrenjie04.alex.user.dao.IdentityId2RoleIdDao;
import com.zhrenjie04.alex.user.dao.PositionDao;
import com.zhrenjie04.alex.user.dao.Role2PrivilegeDao;
import com.zhrenjie04.alex.user.dao.UserDao;
import com.zhrenjie04.alex.user.domain.EsUserSearchKey;
import com.zhrenjie04.alex.user.domain.Group;
import com.zhrenjie04.alex.user.domain.IdentityId2RoleId;
import com.zhrenjie04.alex.user.domain.Position;
import com.zhrenjie04.alex.user.domain.Privilege;
import com.zhrenjie04.alex.util.PinYinUtil;
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
	IdentityDao identityDao;
	@Autowired
	GroupDao groupDao;
	@Autowired
	PositionDao positionDao;
	@Autowired
	IdentityId2RoleIdDao identityId2RoleIdDao;
	@Autowired
	Role2PrivilegeDao role2PrivilegeDao;
	
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
		userSearchKey1.setUserId("2");
		userSearchKey1.setUsername("admin1");
		userSearchKey1.setCellphone("13900001112");
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
	@ResponseJsonWithFilter(type = User.class, include = "userId,username,realname,nickname,email,cellphone,portraitUrl,birthday,gender,"
			+ "identities,currentIdentityId,currentPrivilegeCodes,currentRoleIds,currentIdentity")
	public JsonResult login(@RequestBody User account, HttpServletRequest request, HttpServletResponse response) throws InterruptedException {
		String sid=(String)request.getAttribute("sid");
		String capText = RedisUtil.get("validate-code:"+sid);
		RedisUtil.set("validate-code:"+sid, "", 3*60);
		if (capText != null && !capText.equals(account.getCaptcha()) || "".equals(capText) || capText == null) {
			throw new PrerequisiteNotSatisfiedException("验证码不正确，请点击验证码刷新");
		}
		String userId="";
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
			EsUserSearchKey userSearchKey=result.getSearchHit(0).getContent();
			userId=userSearchKey.getUserId();
		}else if(account.getCellphone()!=null&&!"".equals(account.getCellphone())) {
			//以手机号，验证码方式登录
			QueryBuilder queryBuilder=QueryBuilders.termQuery("cellphone", account.getCellphone());//全字段匹配：1.使用FieldType.Keyword，2.使用term
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
			EsUserSearchKey userSearchKey=result.getSearchHit(0).getContent();
			userId=userSearchKey.getUserId();
		}else {
			throw new PrerequisiteNotSatisfiedException("目前不支持此种登录方式");
		}
		
		//设置数据源
		Integer hashCode=userId.hashCode();
		DbUtil.setDataSource("userIdKeyDb"+(hashCode%DbUtil.dbCountInGroupMap.get("userIdKeyDb")));
		log.debug(DbUtil.getDataSource());
		//操作数据库
		User user = userDao.queryObjectById(userId);
		if(user != null) {
			if(user.getPassword()!=null&&user.getPassword().equals(account.getPassword())) {
				List<Identity> identities=identityDao.queryListByUserId(userId);
				if(identities.size()>0) {
					List<Thread> ts=new LinkedList<>();
					for(Identity identity:identities) {
						String groupId = identity.getGroupId();
						Thread t=new Thread() {
							@Override
							public void run() {
								DbUtil.setDataSource("groupIdKeyDb"+(groupId.hashCode()%DbUtil.dbCountInGroupMap.get("groupIdKeyDb")));
								log.debug("groupIdKeyDb---{}---{}::::{}",groupId,DbUtil.dbCountInGroupMap.get("groupIdKeyDb"),DbUtil.getDataSource());
								Group group = groupDao.queryObjectById(groupId);
								Position position = positionDao.queryObjectById(identity.getPositionId());
								identity.setGroupName(group.getGroupName());
								identity.setGroupShortName(group.getGroupShortName());
								identity.setPositionName(position.getPositionName());
								DbUtil.remove();
							}
						};
						t.start();
						ts.add(t);
					}
					for(Thread t:ts) {
						t.join();
					}
					identities.sort(new Comparator<Identity>() {
						@Override
						public int compare(Identity o1, Identity o2) {
							var c1=PinYinUtil.getPinYin(o1.getGroupShortName()).compareTo(PinYinUtil.getPinYin(o2.getGroupShortName()));
							if(c1 == 0) {
								var c2 = PinYinUtil.getPinYin(o1.getPositionName()).compareTo(PinYinUtil.getPinYin(o2.getPositionName()));
								if( c2 == 0){
									var c3=PinYinUtil.getPinYin(o1.getGroupName()).compareTo(PinYinUtil.getPinYin(o2.getGroupName()));
									if(c3 == 0) {
										return PinYinUtil.getPinYin(o1.getIdentityId()).compareTo(PinYinUtil.getPinYin(o2.getIdentityId()));
									}else {
										return c3;
									}
								}else {
									return c2;
								}
							}else {
								return c1;
							}
						}
					});
					user.setIdentities(identities);
					user.setCurrentIdentityId(identities.get(0).getIdentityId());
					List<IdentityId2RoleId> ids= identityId2RoleIdDao.queryAllByIdentityId(user.getCurrentIdentityId());
					List<String>currentRoleIds = new LinkedList<>();
					for(IdentityId2RoleId id:ids) {
						currentRoleIds.add(id.getRoleId());
					}
					user.setCurrentRoleIds(currentRoleIds);
					Set<Privilege>privileges = Sets.<Privilege>newConcurrentHashSet();
					ts=new LinkedList<>();
					for(String roleId:currentRoleIds) {
						Thread t=new Thread() {
							@Override
							public void run() {
								DbUtil.setDataSource("roleIdKeyDb"+(roleId.hashCode()%DbUtil.dbCountInGroupMap.get("roleIdKeyDb")));
								log.debug("roleIdKey---{}---{}::::-{}",roleId,DbUtil.dbCountInGroupMap.get("roleIdKeyDb"),DbUtil.getDataSource());
								privileges.addAll(role2PrivilegeDao.queryAllPrivilegesByRoleId(roleId));
								DbUtil.remove();
							}
						};
						t.start();
						ts.add(t);
					}
					for(Thread t:ts) {
						t.join();
					}
					List<String>privilegeCodes=new LinkedList<>();
					for(Privilege p:privileges) {
						privilegeCodes.add(p.getPrivilegeCode());
					}
					user.setCurrentPrivilegeCodes(privilegeCodes);
				}
				JsonResult rt = JsonResult.success();
				SessionUtil.setSessionUser(request, user);
				rt.put("user", user);
				//移除ThreadLoacal变量
				DbUtil.remove();
				return rt;
			}else {
				//移除ThreadLoacal变量
				DbUtil.remove();
				throw new PrerequisiteNotSatisfiedException("密码错误");
			}
		}else {
			//移除ThreadLoacal变量
			DbUtil.remove();
			throw new PrerequisiteNotSatisfiedException("该账号不存在");
		}
	}
	@RequestMapping(value = "/login/get-current-user", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	@Permission("login.get-current-user")
	@ResponseJsonWithFilter(type = User.class, include = "userId,username,realname,nickname,email,cellphone,portraitUrl,birthday,gender,"
			+ "identities,currentIdentityId,currentPrivilegeCodes,currentRoleIds,currentIdentity")
	public JsonResult getCurrentUserInfo(HttpServletRequest request, HttpServletResponse response) throws InterruptedException {
		User user=SessionUtil.getSessionUser(request);
		if(user == null) {
			throw new UnauthorizedException("您尚未登录!");
		}
		JsonResult rt = JsonResult.success();
		rt.put("user", user);
		//移除ThreadLoacal变量
		DbUtil.remove();
		return rt;
	}
	@RequestMapping(value = "/login/do-logout", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	@Permission("login.do-logout")
	@ResponseJsonWithFilter(type = FilterWithNoneFiltered.class)
	public JsonResult logout(HttpServletRequest request) {
		SessionUtil.killSession(request);
		return JsonResult.success();
	}
	
	public static void main(String[] args) {
		//以id的String的hashCode求余
		System.out.println("1".hashCode()%5);
		System.out.println("2".hashCode()%5);
		System.out.println("");
		System.out.println("1".hashCode()%2);
		System.out.println("2".hashCode()%2);
		System.out.println("3".hashCode()%2);
		System.out.println("4".hashCode()%2);
		System.out.println("5".hashCode()%2);
	}
}
