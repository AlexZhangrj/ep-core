package com.zhrenjie04.alex.user.dao;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.zhrenjie04.alex.core.GenericDao;
import com.zhrenjie04.alex.core.Identity;
import com.zhrenjie04.alex.core.MenuItem;
import com.zhrenjie04.alex.core.User;

/**
 * @author 张人杰
 */
@Repository("userDao")
public interface UserDao extends GenericDao<User> {
	/**
	 * 查询用户权限代码
	 * 
	 * @param job
	 * @return
	 */
	LinkedList<String> queryUserPrivilegeCodes(Identity job);

	/**
	 * 查询用户角色ids用于用户权限判断
	 * 
	 * @param job
	 * @return
	 */
	LinkedList<String> queryUserRoleIds(Identity job);

	/**
	 * 更新用户帐号锁定状态
	 * 
	 * @param user
	 * @return
	 */
	Long updateLocked(User user);

	/**
	 * 查询用户菜单
	 * 
	 * @param job
	 * @return
	 */
	LinkedList<MenuItem> queryUserMenuLinks(Identity job);

	/**
	 * 求总数
	 * 
	 * @param params
	 * @return
	 */
	Long countSearch(HashMap<String, Object> params);

	/**
	 * 按条件返回所有数据
	 * 
	 * @param params
	 * @return
	 */
	List<User> pageSearchAll(HashMap<String, Object> params);
	/**
	 * 插入用户集团关系表
	 * @param params
	 * @return
	 */
	Long insertUserGroupRL(HashMap<String, Object> params);
	/**
	 * 删除用户集团关系表
	 * @param params
	 * @return
	 */
	Long deleteUserGroupRL(HashMap<String, Object> params);
}
