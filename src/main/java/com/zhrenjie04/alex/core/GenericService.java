package com.zhrenjie04.alex.core;

import com.zhrenjie04.alex.core.domain.User;

import java.util.HashMap;
import java.util.List;

/**
 * @author 张人杰 zhrenjie04@126.com
 */
public interface GenericService<T extends AbstractGenericEntity, D extends GenericDao<T>> {

	/**
	 * 求总数
	 * 
	 * @param params
	 * @param sessionUser
	 * @return
	 */
	JsonResult count(HashMap<String, Object> params, User sessionUser);

	/**
	 * 按条件返回所有数据
	 * 
	 * @param params
	 * @param sessionUser
	 * @return
	 */
	JsonResult queryAll(HashMap<String, Object> params, User sessionUser);

	/**
	 * 返回分页查询数据
	 * 
	 * @param params
	 * @param sessionUser
	 * @return
	 */
	JsonResult pageQueryAll(HashMap<String, Object> params, User sessionUser);

	/**
	 * 按条件返回单条数据
	 * 
	 * @param params
	 * @param sessionUser
	 * @return
	 */
	JsonResult queryObject(HashMap<String, Object> params, User sessionUser);

	/**
	 * 根据id查询单条数据
	 * 
	 * @param id
	 * @param sessionUser
	 * @return
	 */
	JsonResult queryObjectById(String id, User sessionUser);

	/**
	 * 根据id查询单条数据
	 * 
	 * @param id
	 * @return
	 */
	T queryObjectById(String id);

	/**
	 * 插入数据,返回json结果
	 * 
	 * @param object
	 * @param sessionUser
	 * @return
	 */
	JsonResult insertObject(T object, User sessionUser);

	/**
	 * 更新单条数据，返回json结果
	 * 
	 * @param object
	 * @param sessionUser
	 * @return
	 */
	JsonResult updateObject(T object, User sessionUser);

	/**
	 * 更新单条数据，返回json结果
	 * 
	 * @param object
	 * @param sessionUser
	 * @return
	 */
	JsonResult patchObject(T object, User sessionUser);

	/**
	 * 删除单挑数据，返回json结果
	 * 
	 * @param params
	 * @param sessionUser
	 * @return
	 */
	JsonResult deleteAll(HashMap<String, Object> params, User sessionUser);

	/**
	 * 更新所有数据，返回json结果
	 * 
	 * @param params
	 * @param sessionUser
	 * @return
	 */
	JsonResult updateAll(HashMap<String, Object> params, User sessionUser);

	/**
	 * 求总数
	 * 
	 * @param params
	 * @return
	 */
	Long count(HashMap<String, Object> params);

	/**
	 * 按条件返回所有数据
	 * 
	 * @param params
	 * @return
	 */
	List<T> queryAll(HashMap<String, Object> params);

	/**
	 * 返回分页查询数据
	 * 
	 * @param params
	 * @return
	 */
	List<T> pageQueryAll(HashMap<String, Object> params);

	/**
	 * 按条件返回单条数据
	 * 
	 * @param params
	 * @return
	 */
	T queryObject(HashMap<String, Object> params);

	/**
	 * 插入数据,返回json结果
	 * 
	 * @param object
	 * @return
	 */
	void insertObject(T object);

	/**
	 * 更新单条数据，返回json结果
	 * 
	 * @param object
	 * @return
	 */
	Long updateObject(T object);

	/**
	 * 更新单条数据，返回json结果
	 * 
	 * @param object
	 * @return
	 */
	Long patchObject(T object);

	/**
	 * 删除多条数据，返回json结果
	 * 
	 * @param params
	 * @return
	 */
	Long deleteAll(HashMap<String, Object> params);

	/**
	 * 更新所有数据，返回json结果
	 * 
	 * @param params
	 * @return
	 */
	Long updateAll(HashMap<String, Object> params);
	
	/**
	 * 删除单条数据
	 * 
	 * @param id 主键id
	 * @return
	 */
	Long deleteById(String id,String lastModifierId,String lastModifierName);
}