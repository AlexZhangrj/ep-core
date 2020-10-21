package com.zhrenjie04.alex.manager.dao;


import java.util.HashMap;

import org.springframework.stereotype.Repository;

import com.zhrenjie04.alex.core.GenericDao;
import com.zhrenjie04.alex.manager.domain.Role;
/**
 * @author 张人杰
 */
@Repository("roleDao")
public interface RoleDao extends GenericDao<Role>{
	/**
	 * 移动角色
	 * @param object
	 * @return
	 */
	Long updateMoving(Role object);
	/**
	 * 移动子节点
	 * @param params
	 * @return
	 */
	Long updateMovingSubNodes(HashMap<String,Object>params);
	/**
	 * 更新锁定解锁状态
	 * @param role
	 * @return
	 */
	Long updateLocked(Role role);
}
