package com.zhrenjie04.alex.core;

import java.util.HashMap;
import java.util.List;
/**
 * @author 张人杰
 * zhrenjie04@126.com
 */
public interface GenericDao<T extends AbstractGenericEntity> {
	/**
	 * 求总数
	 * @param params
	 * @return
	 */
	Long count(HashMap<String,Object>params);
	/**
	 * 按条件返回所有数据
	 * @param params
	 * @return
	 */
	List<T>queryAll(HashMap<String,Object>params);
	/**
	 * 按条件返回所有数据
	 * @param params
	 * @return
	 */
	List<T>pageQueryAll(HashMap<String,Object>params);
	/**
	 * 按条件返回单条数据
	 * @param params
	 * @return
	 */
	T queryObject(HashMap<String,Object>params);
	/**
	 * 根据id查询单条数据
	 * @param id
	 * @return
	 */
	T queryObjectById(String id);
	/**
	 * 插入数据,返回主键pk的值
	 * @param object
	 * @return
	 */
	void insertObject(T object);
	/**
	 * 更新单条数据
	 * @param object
	 * @return
	 */
	Long updateObject(T object);
	/**
	 * 更新部分属性
	 * @param object
	 * @return
	 */
	Long patchObject(T object);
	/**
	 * 更新所有数据
	 * @param params
	 * @return
	 */
	Long updateAll(HashMap<String,Object>params);
	/**
	 * 所有的删除都用虚拟删除，记录最后操作人员，真实删除的操作请自定义方法
	 * @param params
	 * @return
	 */
	Long deleteAll(HashMap<String,Object>params);
	/**
	 * 所有的删除都用虚拟删除，记录最后操作人员，真实删除的操作请自定义方法
	 * @param params
	 * @return
	 */
	Long deleteById(String id,String lastModifierId,String lastModifierName);
}
