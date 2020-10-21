package com.zhrenjie04.alex.user.dao;


import java.util.HashMap;

import org.springframework.stereotype.Repository;

import com.zhrenjie04.alex.core.GenericDao;
import com.zhrenjie04.alex.manager.domain.Org;
/**
 * @author 张人杰
 */
@Repository("orgDao")
public interface OrgDao extends GenericDao<Org>{
	/**
	 * 移动组织机构
	 * @param object
	 * @return
	 */
	Long updateMoving(Org object);
	/**
	 * 移动子节点
	 * @param params
	 * @return
	 */
	Long updateMovingSubNodes(HashMap<String,Object>params);
	/**
	 * 锁定
	 * @param org
	 * @return
	 */
	Long updateLocked(Org org);
	
	Long updateSubComsComIdAndGroupIdToDept(Org org);
}
