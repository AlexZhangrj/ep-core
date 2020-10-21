package com.zhrenjie04.alex.manager.service;

import java.util.LinkedList;

import com.zhrenjie04.alex.core.GenericService;
import com.zhrenjie04.alex.core.JsonResult;
import com.zhrenjie04.alex.core.User;
import com.zhrenjie04.alex.manager.domain.Role;
import com.zhrenjie04.alex.manager.dao.RoleDao;
/**
 * @author 张人杰
 */
public interface RoleService extends GenericService<Role,RoleDao>{
	/**
	 * 生产树状结构
	 * @param user
	 * @return
	 */
	JsonResult trees(User user);
	/**
	 * 显示当前角色的角色路径
	 * @param roleId
	 * @param user
	 * @return
	 */
	JsonResult path(String roleId,User user);
	/**
	 * 移动角色
	 * @param roleId
	 * @param roleIds
	 * @param sessionUser
	 * @return
	 */
	JsonResult moveTo(String roleId,LinkedList<String> roleIds, User sessionUser);
	/**
	 * 锁定角色
	 * @param object
	 * @param sessionUser
	 * @return
	 */
	JsonResult lock(Role object,User sessionUser);
	/**
	 * 解锁角色
	 * @param object
	 * @param sessionUser
	 * @return
	 */
	JsonResult unlock(Role object,User sessionUser);
}
