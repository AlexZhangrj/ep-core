package com.zhrenjie04.alex.core;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.zhrenjie04.alex.util.IdGenerator;

/**
 * @author 张人杰
 */
public abstract class AbstractGenericService<T extends AbstractGenericEntity, D extends GenericDao<T>>
		implements GenericService<T, D> {

	protected static final String ROOT_GROUP_ID = "0";
	protected static final String ROOT_ORG_ID = "0";
	protected static final String ROOT_ROLE_ID = "0";

	/**
	 * 用于具体Service实例调用具体Dao实例
	 * 
	 * @return
	 */
	abstract protected D getDao();

	protected Logger getLogger() {
		return logger;
	}

	protected Logger logger;

	/**
	 * 求总数
	 */
	@Override
	public JsonResult count(HashMap<String, Object> params, User sessionUser) {
		params.put("userPrivilegeCodes", sessionUser.getPrivilegeCodes());
		params.put("userGroupId", sessionUser.getCurrentIdentity().getGroupId());
		Long count = getDao().count(params);
		JsonResult result = JsonResult.success();
		result.put("total", count);
		return result;
	}

	/**
	 * 按条件返回所有数据
	 * 
	 * @param params
	 * @return
	 */
	@Override
	public JsonResult queryAll(HashMap<String, Object> params, User sessionUser) {
		params.put("userPrivilegeCodes", sessionUser.getPrivilegeCodes());
		params.put("userGroupId", sessionUser.getCurrentIdentity().getGroupId());
		List<T> list = getDao().queryAll(params);
		JsonResult result = JsonResult.success();
		result.put("list", list);
		result.put("orderBy", params.get("orderByFrontend"));
		result.put("orderType", params.get("orderType"));
		return result;
	}

	@Override
	public JsonResult pageQueryAll(HashMap<String, Object> params, User sessionUser) {
		params.put("userPrivilegeCodes", sessionUser.getPrivilegeCodes());
		params.put("userGroupId", sessionUser.getCurrentIdentity().getGroupId());
		Long total = getDao().count(params);
		Long pageNo = (Long) params.get("pageNo");
		Long pageSize = (Long) params.get("pageSize");
		params.put("start", (pageNo - 1) * pageSize);
		params.put("length", pageSize);
		List<T> list = getDao().pageQueryAll(params);
		JsonResult result = JsonResult.success();
		result.put("total", total);
		result.put("list", list);
		result.put("pageNo", pageNo);
		result.put("pageSize", pageSize);
		result.put("orderBy", params.get("orderByFrontend"));
		result.put("orderType", params.get("orderType"));
		if (pageSize != null) {
			result.put("totalPages", (total / pageSize) + (total % pageSize > 0 ? 1 : 0));
		}
		return result;
	}

	/**
	 * 按条件返回单条数据
	 */
	@Override
	public JsonResult queryObject(HashMap<String, Object> params, User sessionUser) {
		params.put("userPrivilegeCodes", sessionUser.getPrivilegeCodes());
		params.put("userGroupId", sessionUser.getCurrentIdentity().getGroupId());
		T object = getDao().queryObject(params);
		JsonResult result = JsonResult.success();
		result.put("object", object);
		return result;
	}

	/**
	 * 根据id查询单条数据
	 */
	@Override
	public JsonResult queryObjectById(String id, User sessionUser) {
		T object = getDao().queryObjectById(id);
		JsonResult result = JsonResult.success();
		result.put("object", object);
		return result;
	}

	/**
	 * 根据id查询单条数据
	 */
	@Override
	public T queryObjectById(String id) {
		return getDao().queryObjectById(id);
	}

	/**
	 * 插入数据,返回主键pk的值
	 */
	@Override
	@Transactional
	public JsonResult insertObject(T object, User sessionUser) {
		object.setPK(IdGenerator.nextIdBase52String());
		object.setCreaterId(sessionUser.getUserId());
		object.setCreaterName(sessionUser.getRealname());
		getDao().insertObject(object);
		JsonResult result = JsonResult.success("插入成功");
		result.put("object", object);
		return result;
	}

	/**
	 * 更新单条数据
	 */
	@Override
	@Transactional
	public JsonResult updateObject(T object, User sessionUser) {
		object.setLastModifierId(sessionUser.getUserId());
		object.setLastModifierName(sessionUser.getRealname());
		Long count = getDao().updateObject(object);
		JsonResult result = JsonResult.success("更新成功");
		result.put("count", count);
		return result;
	}

	/**
	 * 更新部分属性
	 */
	@Override
	@Transactional
	public JsonResult patchObject(T object, User sessionUser) {
		Long count = getDao().patchObject(object);
		JsonResult result = JsonResult.success("更新成功");
		result.put("count", count);
		return result;
	}

	/**
	 * 删除多条数据
	 */
	@Override
	@Transactional
	public JsonResult deleteAll(HashMap<String, Object> params, User sessionUser) {
		params.put("userPrivilegeCodes", sessionUser.getPrivilegeCodes());
		params.put("userGroupId", sessionUser.getCurrentIdentity().getGroupId());
		params.put("lastModifierId", sessionUser.getUserId());
		params.put("lastModifierName", sessionUser.getRealname());
		Long count = getDao().deleteAll(params);
		JsonResult result = JsonResult.success("删除成功");
		result.put("count", count);
		return result;
	}

	/**
	 * 更新多条数据
	 */
	@Override
	@Transactional
	public JsonResult updateAll(HashMap<String, Object> params, User sessionUser) {
		params.put("userPrivilegeCodes", sessionUser.getPrivilegeCodes());
		params.put("userGroupId", sessionUser.getCurrentIdentity().getGroupId());
		params.put("lastModifierId", sessionUser.getUserId());
		params.put("lastModifierName", sessionUser.getRealname());
		Long count = getDao().updateAll(params);
		JsonResult result = JsonResult.success("更新成功");
		result.put("count", count);
		return result;
	}

	/**
	 * 求总数
	 */
	@Override
	public Long count(HashMap<String, Object> params) {
		return getDao().count(params);
	}

	/**
	 * 按条件返回所有数据
	 * 
	 * @param params
	 * @return
	 */
	@Override
	public List<T> queryAll(HashMap<String, Object> params) {
		return getDao().queryAll(params);
	}

	@Override
	public List<T> pageQueryAll(HashMap<String, Object> params) {
		return getDao().pageQueryAll(params);
	}

	/**
	 * 按条件返回单条数据
	 */
	@Override
	public T queryObject(HashMap<String, Object> params) {
		return getDao().queryObject(params);
	}

	/**
	 * 插入数据,返回主键pk的值
	 */
	@Override
	@Transactional
	public void insertObject(T object) {
		getDao().insertObject(object);
	}

	/**
	 * 更新单条数据
	 */
	@Override
	@Transactional
	public Long updateObject(T object) {
		return getDao().updateObject(object);
	}

	/**
	 * 更新部分属性
	 */
	@Override
	@Transactional
	public Long patchObject(T object) {
		return getDao().patchObject(object);
	}

	/**
	 * 删除多条数据
	 */
	@Override
	@Transactional
	public Long deleteAll(HashMap<String, Object> params) {
		return getDao().deleteAll(params);
	}

	/**
	 * 更新多条数据
	 */
	@Override
	@Transactional
	public Long updateAll(HashMap<String, Object> params) {
		return getDao().updateAll(params);
	}
	
	/**
	 * 删除单条数据
	 */
	@Override
	@Transactional
	public Long deleteById(String id,String lastModifierId,String lastModifierName) {
		return getDao().deleteById(id,lastModifierId,lastModifierName);
	}
}