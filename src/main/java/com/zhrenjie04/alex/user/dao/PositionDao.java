package com.zhrenjie04.alex.user.dao;


import org.springframework.stereotype.Repository;

import com.zhrenjie04.alex.core.GenericDao;
import com.zhrenjie04.alex.manager.domain.Position;
/**
 * @author 张人杰
 */
@Repository("positionDao")
public interface PositionDao extends GenericDao<Position>{
	/**
	 * 更新职位锁定状态
	 * @param user
	 * @return
	 */
	Long updateLocked(Position position);
}
