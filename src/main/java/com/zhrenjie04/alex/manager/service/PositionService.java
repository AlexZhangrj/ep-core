package com.zhrenjie04.alex.manager.service;

import com.zhrenjie04.alex.core.GenericService;
import com.zhrenjie04.alex.core.JsonResult;
import com.zhrenjie04.alex.core.User;
import com.zhrenjie04.alex.manager.domain.Position;
import com.zhrenjie04.alex.manager.dao.PositionDao;
/**
 * @author 张人杰
 */
public interface PositionService extends GenericService<Position,PositionDao>{
	/**
	 * 锁定职位
	 * @param position
	 * @param sessionUser
	 * @return
	 */
	JsonResult lock(Position position,User sessionUser);
	/**
	 * 解锁职位
	 * @param position
	 * @param sessionUser
	 * @return
	 */
	JsonResult unlock(Position position,User sessionUser);
}
