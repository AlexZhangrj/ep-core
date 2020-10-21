package com.zhrenjie04.alex.user.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.transaction.annotation.Transactional;

import com.zhrenjie04.alex.core.GenericService;
import com.zhrenjie04.alex.core.Identity;
import com.zhrenjie04.alex.core.JsonResult;
import com.zhrenjie04.alex.core.MenuItem;
import com.zhrenjie04.alex.core.User;
import com.zhrenjie04.alex.user.dao.UserDao;

/**
 * @author 张人杰
 */
public interface UserService extends GenericService<User, UserDao> {
	/**
	 * 返回当前用户的所有职务
	 * 
	 * @param user
	 * @return
	 */
	List<Identity> queryJobs(User user);

	/**
	 * 锁定账号
	 * 
	 * @param user
	 * @param sessionUser
	 * @return
	 */
	JsonResult lock(User user, User sessionUser);

	/**
	 * 解锁账号
	 * 
	 * @param user
	 * @param sessionUser
	 * @return
	 */
	JsonResult unlock(User user, User sessionUser);

	/**
	 * 返回当前用户的菜单项
	 * 
	 * @param job
	 * @return
	 */
	List<MenuItem> queryUserMenuLinks(Identity job);

	/**
	 * 根据ids返回用户基本信息列表
	 * 
	 * @param ids
	 * @return
	 */
	List<User> queryUsersByIds(List<String> ids);

	/**
	 * 根据id返回用户基本信息
	 * 
	 * @param id
	 * @return
	 */
	User queryUserById(String id);

	/**
	 * 搜索用户功能，专用于用户注册
	 * 
	 * @param params
	 * @param sessionUser
	 * @return
	 */
	JsonResult pageSearchAll(HashMap<String, Object> params, User sessionUser);

	/**
	 * 注意：此方法用于后台配置，会判断用户名是否已存在，若已存在则使用已有账号配置身份信息
	 */
	JsonResult insertObject(User object, User sessionUser);

	/**
	 * 注意：此方法用于注册，会判断用户名是否已存在，若已存在则报错
	 */
	void insertObject(User object);
}
