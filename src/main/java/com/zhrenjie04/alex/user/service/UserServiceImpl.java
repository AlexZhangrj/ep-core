package com.zhrenjie04.alex.user.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zhrenjie04.alex.core.AbstractGenericService;
import com.zhrenjie04.alex.core.Identity;
import com.zhrenjie04.alex.core.JsonResult;
import com.zhrenjie04.alex.core.MenuItem;
import com.zhrenjie04.alex.core.User;
import com.zhrenjie04.alex.manager.domain.Config;
import com.zhrenjie04.alex.manager.domain.JobUserOrgPosition;
import com.zhrenjie04.alex.manager.domain.JobUserProjectDeptPosition;
import com.zhrenjie04.alex.user.dao.JobUserOrgPositionDao;
import com.zhrenjie04.alex.user.dao.JobUserProjectDeptPositionDao;
import com.zhrenjie04.alex.user.dao.OrgDao;
import com.zhrenjie04.alex.user.dao.UserDao;
import com.zhrenjie04.alex.util.IdGenerator;
import com.zhrenjie04.alex.util.JsonUtil;
import com.zhrenjie04.alex.util.Md5Util;
import com.zhrenjie04.alex.util.SaltUtil;
import com.zhrenjie04.alex.util.SessionUtil;
import com.zhrenjie04.alex.util.StringUtil;

/**
 * @author 张人杰
 */
@Service("userService")
public class UserServiceImpl extends AbstractGenericService<User, UserDao> implements UserService {

	@Autowired
	UserDao userDao;

	@Override
	protected UserDao getDao() {
		return userDao;
	}

	@Override
	public List<MenuItem> queryUserMenuLinks(Identity job) {
		List<MenuItem> menus = userDao.queryUserMenuLinks(job);
		List<MenuItem> menuLinks = new LinkedList<MenuItem>();
		HashMap<String,MenuItem>menuLinksMap=new HashMap<>();
		// 第一级
		Iterator<MenuItem> it = menus.iterator();
		while (it.hasNext()) {
			MenuItem menu = it.next();
			if ("0".equals(menu.getParentId())) {
				menuLinks.add(menu);
				menu.setType("link");
				menuLinksMap.put(menu.getMenuId(),menu);
				it.remove();
			}
		}
		// 第二级至第n级
		boolean hasFound=true;
		while(hasFound) {
			hasFound=false;
			for (int i=0;i<menus.size();i++) {
				MenuItem menu=menus.get(i);
				if(menu.getParentId()!=null&&menuLinksMap.get(menu.getParentId())!=null) {
					MenuItem parent=menuLinksMap.get(menu.getParentId());
					if(parent.getChildren()==null) {
						parent.setChildren(new LinkedList<MenuItem>());
					}
					menu.setType("sub");
					parent.getChildren().add(menu);
					menuLinksMap.put(menu.getMenuId(), menu);
					menus.remove(i);
					hasFound=true;
					break;
				}
			}
		}
		return menuLinks;
	}

	@Override
	public List<Identity> queryJobs(User user) {
		HashMap<String, Object> params = new HashMap<String, Object>(16);
		params.put("userId", user.getUserId());
		return null;
	}

	@Cacheable(value = "users", key = "#root.target.getCacheKeyPageSearchAll(#params,#sessionUser)", sync = true)
	@Override
	public JsonResult pageSearchAll(HashMap<String, Object> params, User sessionUser) {
		//搜索不限制哪个集团
//		if (sessionUser.getCurrentIdentity().getGroupId() == null) {
//			return JsonResult.failure(JsonResult.CODE_PREREQUISITE_NOT_SATISFIED, "您的groupId为空，不能返回相应账号信息");
//		}
//		if (sessionUser.hasPrivilege("manager:user.back.page-query-all-groups")
//				&& ROOT_GROUP_ID.equals(sessionUser.getGroupId())
//				&& ROOT_GROUP_ID.equals(sessionUser.getCurrentIdentity().getGroupId())) {
//			// 返回所有账号权限
//		} else if (sessionUser.hasPrivilege("manager:user.back.page-query-all-coms")) {
//			// 只返回本集团的账号的权限
//			params.put("groupId", sessionUser.getCurrentIdentity().getGroupId());
//		} else {
//			// 不返回数据
//			params.put("groupId", -1);
////			params.put("groupId", sessionUser.getCurrentIdentity().getGroupId());
//		}
		Long total = userDao.countSearch(params);
		Long pageNo = null;
		try {
			pageNo = Long.valueOf(params.get("pageNo").toString());
		} catch (Exception e) {
		}
		if (pageNo == null) {
			pageNo = 1L;
		}
		Long pageSize = 5L;
		params.put("start", (pageNo - 1) * pageSize);
		params.put("length", pageSize);
		List<User> list = userDao.pageSearchAll(params);
		JsonResult result = JsonResult.success();
		result.put("total", total);
		result.put("list", list);
		result.put("pageNo", pageNo);
		result.put("pageSize", pageSize);
		if (pageSize != null) {
			result.put("totalPages", (total / pageSize) + (total % pageSize > 0 ? 1 : 0));
		}
		return result;
	}

	@Cacheable(value = "users", key = "#root.target.getCacheKeyPageQueryAll(#params,#sessionUser)", sync = true)
	@Override
	public JsonResult pageQueryAll(HashMap<String, Object> params, User sessionUser) {
		if (sessionUser.getCurrentIdentity().getGroupId() == null) {
			return JsonResult.failure(JsonResult.CODE_PREREQUISITE_NOT_SATISFIED, "您的groupId为空，不能返回相应账号信息");
		}
		if (sessionUser.hasPrivilege("manager:user.back.page-query-all-groups")
				&& ROOT_GROUP_ID.equals(sessionUser.getGroupId())
				&& ROOT_GROUP_ID.equals(sessionUser.getCurrentIdentity().getGroupId())) {
			// 返回所有账号权限
		} else if (sessionUser.hasPrivilege("manager:user.back.page-query-all-coms")) {
			// 只返回本集团的账号的权限
			params.put("groupId", sessionUser.getCurrentIdentity().getGroupId());
		} else {
			// 不返回数据
			params.put("groupId", -1);
//			params.put("groupId", sessionUser.getCurrentIdentity().getGroupId());
		}
		return super.pageQueryAll(params, sessionUser);
	}

	public String getCacheKeyPageQueryAll(HashMap<String, Object> params, User sessionUser) {
		return "pq-" + JsonUtil.stringify(params) + "-"
				+ (sessionUser.hasPrivilege("manager:user.back.page-query-all-groups")
						&& ROOT_GROUP_ID.equals(sessionUser.getGroupId())
						&& ROOT_GROUP_ID.equals(sessionUser.getCurrentIdentity().getGroupId()))
				+ "-" + (sessionUser.hasPrivilege("manager:user.back.page-query-all-coms")) + "-"
				+ (sessionUser.getCurrentIdentity().getGroupId());
	}

	public String getCacheKeyPageSearchAll(HashMap<String, Object> params, User sessionUser) {
		return "ps-" + JsonUtil.stringify(params);
	}

	@Caching(evict = { @CacheEvict(value = "users", allEntries = true),
			@CacheEvict(value = "user", allEntries = true) })
	@Override
	@Transactional
	public JsonResult deleteAll(HashMap<String, Object> params, User sessionUser) {
		// 删除用户账号改为删除该用户在本集团内的所有job，及用户集团关系数据
		if (!ROOT_GROUP_ID.equals(sessionUser.getCurrentIdentity().getGroupId())) {
			// 非根系统用户只能删除自己所在集团的用户的所有job
			params.put("groupId", sessionUser.getCurrentIdentity().getGroupId());
		}
		String[] ids = (String[]) params.get("ids");
		for (String id : ids) {
			if (id.equals(sessionUser.getUserId())) {
				throw new RuntimeException("不能删除自己的帐号");
			}
		}
		params.put("lastModifierId", sessionUser.getUserId());
		params.put("lastModifierName", sessionUser.getUsername());
		userDao.deleteAll(params);
		return JsonResult.success("删除成功");
	}

	/**
	 * 注意：此方法用于后台配置，会判断用户名是否已存在，若已存在则使用已有账号配置身份信息
	 */
	@CacheEvict(value = "users", allEntries = true)
	@Override
	@Transactional
	public JsonResult insertObject(User object, User sessionUser) {
		// 密码经过盐值加密
		object.setSalt(SaltUtil.generatSalt());
		object.setPassword(Md5Util.encrypt(object.getPassword() + object.getSalt()));
		object.setUserId(IdGenerator.nextIdBase48String());
		// 只能添加当前职务所属集团的用户
		object.setGroupId(sessionUser.getCurrentIdentity().getGroupId());
		HashMap<String, Object> params = new HashMap<String, Object>(16);
		params.put("username", object.getUsername());
		User user = userDao.queryObject(params);
		if (user != null) {// 用户名相同则只做配置
			object.setUserId(user.getUserId());
		} else {
			super.insertObject(object, sessionUser);
		}
		return JsonResult.success();
	}

	/**
	 * 注意：此方法用于注册，会判断用户名是否已存在，若已存在则报错
	 */
	@CacheEvict(value = "users", allEntries = true)
	@Override
	@Transactional
	public void insertObject(User object) {
		// 密码经过盐值加密
		object.setSalt(SaltUtil.generatSalt());
		object.setPassword(Md5Util.encrypt(object.getPassword() + object.getSalt()));
		object.setUserId(IdGenerator.nextIdBase48String());
		HashMap<String, Object> params = new HashMap<String, Object>(16);
		params.put("username", object.getUsername());
		if (userDao.count(params) > 0) {
			throw new RuntimeException("用户名已存在");
		}
		userDao.insertObject(object);
	}

	@Caching(evict = { @CacheEvict(value = "users", allEntries = true),
			@CacheEvict(value = "user", key = "#object.userId") })
	@Override
	@Transactional
	public JsonResult updateObject(User object, User sessionUser) {
		if (object.getPassword() != null && !"".equals(object.getPassword())) {
			// 密码经过盐值加密
			object.setSalt(SaltUtil.generatSalt());
			object.setPassword(Md5Util.encrypt(object.getPassword() + object.getSalt()));
		}
		return super.updateObject(object, sessionUser);
	}

	@Caching(evict = { @CacheEvict(value = "users", allEntries = true),
			@CacheEvict(value = "user", key = "#object.userId") })
	@Override
	@Transactional
	public Long updateObject(User object) {
		if (object.getPassword() != null && !"".equals(object.getPassword())) {
			// 密码经过盐值加密
			object.setSalt(SaltUtil.generatSalt());
			object.setPassword(Md5Util.encrypt(object.getPassword() + object.getSalt()));
		}
		Long count = getDao().updateObject(object);
		return count;
	}

	@Caching(evict = { @CacheEvict(value = "users", allEntries = true),
			@CacheEvict(value = "user", key = "#user.userId") })
	@Override
	@Transactional
	public JsonResult lock(User user, User sessionUser) {
		if (sessionUser.getUserId().equals(user.getUserId())) {
			return JsonResult.failure(JsonResult.CODE_PREREQUISITE_NOT_SATISFIED, "不能锁定自己");
		}
		SessionUtil.killAllSessionsByUserId(user.getUserId());
		user.setIsLocked(true);
		Long count = userDao.updateLocked(user);
		if (count > 0) {
			return JsonResult.success("锁定成功");
		} else {
			return JsonResult.failure(JsonResult.CODE_REQUEST_INPUT_ERROR, "锁定失败，您的输入不合法");
		}
	}

	@Caching(evict = { @CacheEvict(value = "users", allEntries = true),
			@CacheEvict(value = "user", key = "#user.userId") })
	@Override
	@Transactional
	public JsonResult unlock(User user, User sessionUser) {
		if (sessionUser.getUserId().equals(user.getUserId())) {
			return JsonResult.failure(JsonResult.CODE_PREREQUISITE_NOT_SATISFIED, "不能解锁自己");
		}
		user.setIsLocked(false);
		Long count = userDao.updateLocked(user);
		if (count > 0) {
			return JsonResult.success("解锁成功");
		} else {
			return JsonResult.failure(JsonResult.CODE_REQUEST_INPUT_ERROR, "锁定失败，您的输入不合法");
		}
	}

	@Cacheable(value = "users", key = "#root.target.getCacheKeyQueryUsersByIds(#ids)", sync = true)
	@Override
	public List<User> queryUsersByIds(List<String> ids) {
		HashMap<String, Object> params = new HashMap<>();
		params.put("ids", ids);
		return userDao.queryAll(params);
	}

	public String getCacheKeyQueryUsersByIds(List<String> ids) {
		return StringUtil.join(ids);
	}

	@Cacheable(value = "user", key = "#id")
	@Override
	public User queryUserById(String id) {
		return userDao.queryObjectById(id);
	}
}
