package com.zhrenjie04.alex.manager.service;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhrenjie04.alex.core.AbstractGenericService;
import com.zhrenjie04.alex.core.JsonResult;
import com.zhrenjie04.alex.core.User;
import com.zhrenjie04.alex.manager.dao.PositionDao;
import com.zhrenjie04.alex.manager.domain.Position;
import com.zhrenjie04.alex.util.IdGenerator2;
/**
 * @author 张人杰
 */
@Service("positionService")
public class PositionServiceImpl extends AbstractGenericService<Position,PositionDao> implements PositionService {

	@Autowired
	PositionDao positionDao;

	@Override
	protected PositionDao getDao() {
		return positionDao;
	}	
	@Override
	public JsonResult pageQueryAll(HashMap<String, Object> params, User sessionUser) {
		if(sessionUser.getCurrentJob().getGroupId() == null) {
			return JsonResult.failure(JsonResult.CODE_PREREQUISITE_NOT_SATISFIED,"您的groupId为空，不能返回相应账号信息");
		}
		if(sessionUser.hasPrivilege("manager:position.back.page-query-all-groups")&&ROOT_GROUP_ID.equals(sessionUser.getGroupId())&&ROOT_GROUP_ID.equals(sessionUser.getCurrentJob().getGroupId())) {
			//返回所有职位权限
		}else if(sessionUser.hasPrivilege("manager:position.back.page-query-self-group")){
			//只返回本集团职位的权限
			params.put("groupId", sessionUser.getCurrentJob().getGroupId());
		}else {
			//不返回数据
			params.put("groupId", -1);
		}
		return super.pageQueryAll(params, sessionUser);
	}
	@Override
	public JsonResult deleteAll(HashMap<String, Object> params, User sessionUser) {
		if(!ROOT_GROUP_ID.equals(sessionUser.getCurrentJob().getGroupId())) {
			//非根系统用户只能删除自己所在集团的职位
			params.put("groupId", sessionUser.getCurrentJob().getGroupId());
		}
		String[] ids=(String[])params.get("ids");
		for(String id:ids) {
			if(id.equals(sessionUser.getCurrentJob().getPositionId())) {
				throw new RuntimeException("不能删除自己的职位");
			}
		}
		return super.deleteAll(params, sessionUser);
	}
	@Override
	public JsonResult insertObject(Position object, User sessionUser) {
		object.setPositionId(IdGenerator2.nextIdBase52String());
		//只能添加当前职务所属集团的职位
		object.setGroupId(sessionUser.getCurrentJob().getGroupId());
		HashMap<String,Object>params=new HashMap<String, Object>(16);
		params.put("positionName", object.getPositionName());
		params.put("positionCode", object.getPositionCode());
		params.put("groupId", sessionUser.getCurrentJob().getGroupId());
		if(getDao().count(params)>0) {
			throw new RuntimeException("职位名称或职位代码已存在");
		}
		return super.insertObject(object, sessionUser);
	}
	@Override
	public JsonResult lock(Position position,User sessionUser) {
		if(sessionUser.getCurrentJob().getPositionId().equals(position.getPositionId())) {
			return JsonResult.failure(JsonResult.CODE_PREREQUISITE_NOT_SATISFIED, "不能锁定自己的职位");
		}
		position.setIsLocked(true);
		Long count = getDao().updateLocked(position);
		if(count>0) {
			return JsonResult.success("锁定成功");
		} else {
			return JsonResult.failure(JsonResult.CODE_REQUEST_INPUT_ERROR, "锁定失败，您的输入不合法");
		}
	}
	@Override
	public JsonResult unlock(Position position,User sessionUser) {
		if(sessionUser.getCurrentJob().getPositionId().equals(position.getPositionId())) {
			return JsonResult.failure(JsonResult.CODE_PREREQUISITE_NOT_SATISFIED, "不能解锁自己的职位");
		}
		position.setIsLocked(false);
		Long count = getDao().updateLocked(position);
		if(count>0) {
			return JsonResult.success("解锁成功");
		} else {
			return JsonResult.failure(JsonResult.CODE_REQUEST_INPUT_ERROR, "锁定失败，您的输入不合法");
		}
	}
}
