package com.zhrenjie04.alex.manager.service;

import java.util.LinkedList;
import java.util.List;

import com.zhrenjie04.alex.core.GenericService;
import com.zhrenjie04.alex.core.JsonResult;
import com.zhrenjie04.alex.core.User;
import com.zhrenjie04.alex.manager.domain.Privilege;
import com.zhrenjie04.alex.manager.dao.PrivilegeDao;
/**
 * @author 张人杰
 */
public interface PrivilegeService extends GenericService<Privilege,PrivilegeDao>{
	/**
	 * 返回权限树
	 * @param user
	 * @return
	 */
	JsonResult trees(User user);
	/**
	 * 返回权限面包线
	 * @param privilegeId
	 * @param user
	 * @return
	 */
	JsonResult path(String privilegeId,User user);
	/**
	 * 移动权限
	 * @param privilegeId
	 * @param privilegeIds
	 * @param sessionUser
	 * @return
	 */
	JsonResult moveTo(String privilegeId,LinkedList<String> privilegeIds, User sessionUser);
	/**
	 * 锁定权限
	 * @param object
	 * @param sessionUser
	 * @return
	 */
	JsonResult lock(Privilege object,User sessionUser);
	/**
	 * 解锁权限
	 * @param object
	 * @param sessionUser
	 * @return
	 */
	JsonResult unlock(Privilege object,User sessionUser);
	/**
	 * 加载某个角色的权限项
	 * @param roleId
	 * @param sessionUser
	 * @return
	 */
	JsonResult loadRoleOriginalPrivileges(String roleId,User sessionUser);
	/**
	 * 加载当前用户当前job的所有权限项
	 * @param sessionUser
	 * @return
	 */
	JsonResult loadCurrentUserJobPrivileges(User sessionUser);
	/**
	 * 保存角色权限配置
	 * @param roleId
	 * @param privilegeIds
	 * @param sessionUser
	 * @return
	 */
	JsonResult configRolePrivileges(String roleId,List<String> privilegeIds,User sessionUser);

}
