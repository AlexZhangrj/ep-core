package com.zhrenjie04.alex.manager.service;

import java.util.LinkedList;

import com.zhrenjie04.alex.core.GenericService;
import com.zhrenjie04.alex.core.JsonResult;
import com.zhrenjie04.alex.core.User;
import com.zhrenjie04.alex.manager.domain.Org;
import com.zhrenjie04.alex.manager.dao.OrgDao;
/**
 * @author 张人杰
 */
public interface OrgService extends GenericService<Org,OrgDao>{
	/**
	 * 返回组织机构树
	 * @param user
	 * @return
	 */
	JsonResult tree(User user);
	/**
	 * 返回组织机构面包线
	 * @param orgId
	 * @param user
	 * @return
	 */
	JsonResult path(String orgId,User user);
	/**
	 * 移动组织机构
	 * @param orgId
	 * @param orgIds
	 * @param sessionUser
	 * @return
	 */
	JsonResult moveTo(String orgId,LinkedList<String> orgIds, User sessionUser);
	/**
	 * 锁定组织机构
	 * @param object
	 * @param sessionUser
	 * @return
	 */
	JsonResult lock(Org object,User sessionUser);
	/**
	 * 解锁组织机构
	 * @param object
	 * @param sessionUser
	 * @return
	 */
	JsonResult unlock(Org object,User sessionUser);
}
