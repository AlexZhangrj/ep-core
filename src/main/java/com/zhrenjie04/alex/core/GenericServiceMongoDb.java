package com.zhrenjie04.alex.core;

import java.util.HashMap;
import java.util.List;

import org.springframework.data.mongodb.core.query.Criteria;

/**
 * @author 张人杰 zhrenjie04@126.com
 */
public interface GenericServiceMongoDb<T extends AbstractGenericEntity> {

	/**
	 * 求总数
	 * 
	 * @param params
	 * @param sessionUser
	 * @return
	 */
	JsonResult count(User sessionUser, Criteria ...criterias);

	/**
	 * 按条件返回所有数据
	 * 
	 * @param params
	 * @param sessionUser
	 * @return
	 */
	JsonResult queryAll(User sessionUser,String orderBy,String orderType,Criteria ...criterias);

	/**
	 * 返回分页查询数据
	 * 
	 * @param params
	 * @param sessionUser
	 * @return
	 */
	JsonResult pageQueryAll(User sessionUser, String orderBy, String orderType, int pageNo, int pageSize, Criteria ...criterias);

	/**
	 * 按条件返回单条数据
	 * 
	 * @param params
	 * @param sessionUser
	 * @return
	 */
	JsonResult queryObject(User sessionUser,Criteria ...criterias);

	/**
	 * 根据id查询单条数据
	 * 
	 * @param id
	 * @param sessionUser
	 * @return
	 */
	JsonResult queryObjectById(User sessionUser, String pkName, String id);

	/**
	 * 根据id查询单条数据
	 * 
	 * @param id
	 * @return
	 */
	T queryObjectById(String pkName, String id);

	/**
	 * 插入数据,返回json结果
	 * 
	 * @param object
	 * @param sessionUser
	 * @return
	 */
	JsonResult insertObject(User sessionUser, T object);

	/**
	 * 更新单条数据，返回json结果
	 * 
	 * @param object
	 * @param sessionUser
	 * @return
	 */
	JsonResult updateObject(User sessionUser, HashMap<String,Object> newPropertyValueMap, Criteria ...criterias);

	/**
	 * 更新单条数据，返回json结果
	 * 
	 * @param object
	 * @param sessionUser
	 * @return
	 */
	JsonResult patchObject(User sessionUser, HashMap<String,Object> newPropertyValueMap, Criteria ...criterias);

	/**
	 * 删除单挑数据，返回json结果
	 * 
	 * @param params
	 * @param sessionUser
	 * @return
	 */
	JsonResult deleteAll(User sessionUser, Criteria ...criterias);

	/**
	 * 更新所有数据，返回json结果
	 * 
	 * @param params
	 * @param sessionUser
	 * @return
	 */
	JsonResult updateAll(User sessionUser, HashMap<String,Object> newPropertyValueMap, Criteria ...criterias);

	/**
	 * 求总数
	 * 
	 * @param params
	 * @return
	 */
	Long count(Criteria ...criterias);

	/**
	 * 按条件返回所有数据
	 * 
	 * @param params
	 * @return
	 */
	List<T> queryAll(String orderBy,String orderType,Criteria ...criterias);

	/**
	 * 返回分页查询数据
	 * 
	 * @param params
	 * @return
	 */
	List<T> pageQueryAll(String orderBy, String orderType, int pageNo, int pageSize, Criteria ...criterias);

	/**
	 * 按条件返回单条数据
	 * 
	 * @param params
	 * @return
	 */
	T queryObject(Criteria ...criterias);

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
	Long updateObject(HashMap<String,Object> newPropertyValueMap, Criteria ...criterias);

	/**
	 * 更新单条数据，返回json结果
	 * 
	 * @param object
	 * @return
	 */
	Long patchObject(HashMap<String,Object> newPropertyValueMap, Criteria ...criterias);

	/**
	 * 删除多条数据，返回json结果
	 * 
	 * @param params
	 * @return
	 */
	Long deleteAll(String lastModifierId, String lastModifierName, Criteria... criterias);

	/**
	 * 更新所有数据，返回json结果
	 * 
	 * @param params
	 * @return
	 */
	Long updateAll(HashMap<String,Object> newPropertyValueMap, Criteria ...criterias);
	
	/**
	 * 删除单条数据
	 * 
	 * @param id 主键id
	 * @return
	 */
	Long deleteById(String pkName,String id,String lastModifierId,String lastModifierName);
}