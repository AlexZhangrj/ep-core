package com.zhrenjie04.alex.user.dao;

import org.springframework.stereotype.Repository;

import com.zhrenjie04.alex.user.domain.Position;

/**
 * @author 张人杰
 */
@Repository("positionDao")
public interface PositionDao {
	Position queryObjectById(String positionId);
}
