package com.zhrenjie04.alex.user.dao;


import java.util.HashMap;

import org.springframework.stereotype.Repository;

import com.zhrenjie04.alex.core.GenericDao;
import com.zhrenjie04.alex.manager.domain.Privilege;
/**
 * @author 张人杰
 */
@Repository("privilegeDao")
public interface PrivilegeDao extends GenericDao<Privilege>{
	/**
	 * 移动组织机构
	 * @param object
	 * @return
	 */
	Long updateMoving(Privilege object);
	/**
	 * 移动子节点
	 * @param params
	 * @return
	 */
	Long updateMovingSubNodes(HashMap<String,Object>params);
	/**
	 * 锁定、解锁
	 * @param org
	 * @return
	 */
	Long updateLocked(Privilege org);
}
