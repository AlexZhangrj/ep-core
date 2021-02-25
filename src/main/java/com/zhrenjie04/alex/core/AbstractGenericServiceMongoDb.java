package com.zhrenjie04.alex.core;

import java.lang.reflect.ParameterizedType;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.zhrenjie04.alex.core.domain.User;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.transaction.annotation.Transactional;

import com.mongodb.client.result.UpdateResult;
import com.zhrenjie04.alex.util.IdGenerator;

/**
 * @author 张人杰
 */
public abstract class AbstractGenericServiceMongoDb<T extends AbstractGenericEntity>
		implements GenericServiceMongoDb<T> {

	protected static final String ROOT_GROUP_ID = "0";
	protected static final String ROOT_ORG_ID = "0";
	protected static final String ROOT_ROLE_ID = "0";

	abstract protected Logger getLogger();

	@Autowired
	protected MongoTemplate template;

	/**
	 * T的Class对象
	 */
	protected Class<T> tClass;
	protected String collectionName;

	@SuppressWarnings("unchecked")
	public AbstractGenericServiceMongoDb() {
		tClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		collectionName = tClass.getSimpleName();
	}

	/**
	 * 求总数
	 */
	@Override
	public JsonResult count(User sessionUser, Criteria... criterias) {
		Query query = new Query();
		Criteria criteria=Criteria.where("isDeleted").is(false);
		if (criterias != null) {
			criteria.andOperator(criterias);
		}
		query.addCriteria(criteria);
		JsonResult result = JsonResult.success();
		result.put("total", template.count(query, tClass));
		getLogger().debug("count:{}",result.get("total"));
		return result;
	}

	/**
	 * 按条件返回所有数据
	 * 
	 * @param params
	 * @return
	 */
	@Override
	public JsonResult queryAll(User sessionUser, String orderBy, String orderType, Criteria... criterias) {
		Query query = new Query();
		Criteria criteria=Criteria.where("isDeleted").is(false);
		if (criterias != null) {
			criteria.andOperator(criterias);
		}
		query.addCriteria(criteria);
		if(orderBy!=null&&!"".equals(orderBy)) {
			if ("asc".equals(orderType)) {
				query.with(Sort.by(Direction.ASC, orderBy));
			} else {
				query.with(Sort.by(Direction.DESC, orderBy));
			}
		}
		JsonResult result = JsonResult.success();
		result.put("list", template.find(query, tClass));
		result.put("orderBy", orderBy);
		result.put("orderType", orderType);
		getLogger().debug("queryAll");
		return result;
	}

	@Override
	public JsonResult pageQueryAll(User sessionUser, String orderBy, String orderType, int pageNo, int pageSize,
			Criteria... criterias) {
		Query query = new Query();
		Criteria criteria=Criteria.where("isDeleted").is(false);
		if (criterias != null) {
			criteria.andOperator(criterias);
		}
		query.addCriteria(criteria);
		Long total = template.count(query, tClass);
		if(orderBy!=null&&!"".equals(orderBy)) {
			if ("asc".equals(orderType)) {
				query.with(Sort.by(Direction.ASC, orderBy));
			} else {
				query.with(Sort.by(Direction.DESC, orderBy));
			}
		}
		query.with(PageRequest.of(pageNo, pageSize));
		JsonResult result = JsonResult.success();
		result.put("total", total);
		result.put("list", template.find(query, tClass));
		result.put("pageNo", pageNo);
		result.put("pageSize", pageSize);
		result.put("orderBy", orderBy);
		result.put("orderType", orderType);
		result.put("totalPages", (total / pageSize) + (total % pageSize > 0 ? 1 : 0));
		getLogger().debug("pageQueryAll");
		return result;
	}

	/**
	 * 按条件返回单条数据
	 */
	@Override
	public JsonResult queryObject(User sessionUser, Criteria... criterias) {
		Query query = new Query();
		Criteria criteria=Criteria.where("isDeleted").is(false);
		if (criterias != null) {
			criteria.andOperator(criterias);
		}
		query.addCriteria(criteria);
		T object = template.findOne(query, tClass);
		JsonResult result = JsonResult.success();
		result.put("object", object);
		getLogger().debug("queryObject");
		return result;
	}

	/**
	 * 根据id查询单条数据
	 */
	@Override
	public JsonResult queryObjectById(User sessionUser, String pkName, String id) {
		Query query = new Query();
		Criteria criteria=Criteria.where("isDeleted").is(false);
		criteria.andOperator(Criteria.where(pkName).is(id));
		query.addCriteria(criteria);
		T object = template.findOne(query, tClass);
		JsonResult result = JsonResult.success();
		result.put("object", object);
		getLogger().debug("queryObjectById");
		return result;
	}

	/**
	 * 根据id查询单条数据
	 */
	@Override
	public T queryObjectById(String pkName, String id) {
		Query query = new Query();
		Criteria criteria=Criteria.where("isDeleted").is(false);
		criteria.andOperator(Criteria.where(pkName).is(id));
		query.addCriteria(criteria);
		T object = template.findOne(query, tClass);
		getLogger().debug("queryObjectById");
		return object;
	}

	/**
	 * 插入数据,返回主键pk的值
	 */
	@Override
	@Transactional
	public JsonResult insertObject(User sessionUser, T object) {
		object.setPK(IdGenerator.nextIdBase48String());
		object.setIsDeleted(false);
		template.insert(object);
		JsonResult result = JsonResult.success("插入成功");
		result.put("object", object);
		getLogger().debug("insertObject");
		return result;
	}

	/**
	 * 更新单条数据
	 */
	@Override
	@Transactional
	public JsonResult updateObject(User sessionUser, HashMap<String, Object> newPropertyValueMap,
			Criteria... criterias) {
		Query query = new Query();
		Criteria criteria=Criteria.where("isDeleted").is(false);
		if (criterias != null) {
			criteria.andOperator(criterias);
		}
		query.addCriteria(criteria);
		Update update = new Update();
		if (newPropertyValueMap != null) {
			for (String key : newPropertyValueMap.keySet()) {
				update.set(key, newPropertyValueMap.get(key));
			}
		}
		update.set("lastModifierId", sessionUser.getUserId());
		update.set("lastModifierName", sessionUser.getRealname());
		update.set("lastModifiedTime", new Date());
		UpdateResult updateResult = template.updateMulti(query, update, tClass);
		JsonResult result = JsonResult.success("更新成功");
		result.put("count", updateResult.getModifiedCount());
		getLogger().debug("updateObject:{}",updateResult.getModifiedCount());
		return result;
	}

	/**
	 * 更新部分属性
	 */
	@Override
	@Transactional
	public JsonResult patchObject(User sessionUser, HashMap<String, Object> newPropertyValueMap,
			Criteria... criterias) {
		Query query = new Query();
		Criteria criteria=Criteria.where("isDeleted").is(false);
		if (criterias != null) {
			criteria.andOperator(criterias);
		}
		query.addCriteria(criteria);
		Update update = new Update();
		if (newPropertyValueMap != null) {
			for (String key : newPropertyValueMap.keySet()) {
				update.set(key, newPropertyValueMap.get(key));
			}
		}
		update.set("lastModifierId", sessionUser.getUserId());
		update.set("lastModifierName", sessionUser.getRealname());
		update.set("lastModifiedTime", new Date());
		UpdateResult updateResult = template.updateMulti(query, update, tClass);
		JsonResult result = JsonResult.success("更新成功");
		result.put("count", updateResult.getModifiedCount());
		getLogger().debug("patchObject:{}",updateResult.getModifiedCount());
		return result;
	}

	/**
	 * 删除多条数据
	 */
	@Override
	@Transactional
	public JsonResult deleteAll(User sessionUser, Criteria... criterias) {
		Query query = new Query();
		Criteria criteria=Criteria.where("isDeleted").is(false);
		if (criterias != null) {
			criteria.andOperator(criterias);
		}
		query.addCriteria(criteria);
		Update update = new Update();
		update.set("lastModifierId", sessionUser.getUserId());
		update.set("lastModifierName", sessionUser.getRealname());
		update.set("lastModifiedTime", new Date());
		update.set("isDeleted", true);
		UpdateResult updateResult = template.updateMulti(query, update, tClass);
		JsonResult result = JsonResult.success("删除成功");
		result.put("count", updateResult.getModifiedCount());
		getLogger().debug("deleteAll:{}",updateResult.getModifiedCount());
		return result;
	}

	/**
	 * 更新多条数据
	 */
	@Override
	@Transactional
	public JsonResult updateAll(User sessionUser, HashMap<String, Object> newPropertyValueMap, Criteria... criterias) {
		Query query = new Query();
		Criteria criteria=Criteria.where("isDeleted").is(false);
		if (criterias != null) {
			criteria.andOperator(criterias);
		}
		query.addCriteria(criteria);
		Update update = new Update();
		if (newPropertyValueMap != null) {
			for (String key : newPropertyValueMap.keySet()) {
				update.set(key, newPropertyValueMap.get(key));
			}
		}
		update.set("lastModifierId", sessionUser.getUserId());
		update.set("lastModifierName", sessionUser.getRealname());
		update.set("lastModifiedTime", new Date());
		UpdateResult updateResult = template.updateMulti(query, update, tClass);
		JsonResult result = JsonResult.success("更新成功");
		result.put("updateResult", updateResult);
		getLogger().debug("updateAll:{}",updateResult.getModifiedCount());
		return result;
	}

	/**
	 * 求总数
	 */
	@Override
	public Long count(Criteria... criterias) {
		Query query = new Query();
		Criteria criteria=Criteria.where("isDeleted").is(false);
		if (criterias != null) {
			criteria.andOperator(criterias);
		}
		query.addCriteria(criteria);
		getLogger().debug("count");
		return template.count(query, tClass);
	}

	/**
	 * 按条件返回所有数据
	 * 
	 * @param params
	 * @return
	 */
	@Override
	public List<T> queryAll(String orderBy, String orderType, Criteria... criterias) {
		Query query = new Query();
		Criteria criteria=Criteria.where("isDeleted").is(false);
		if (criterias != null) {
			criteria.andOperator(criterias);
		}
		query.addCriteria(criteria);
		if(orderBy!=null&&!"".equals(orderBy)) {
			if ("asc".equals(orderType)) {
				query.with(Sort.by(Direction.ASC, orderBy));
			} else {
				query.with(Sort.by(Direction.DESC, orderBy));
			}
		}
		getLogger().debug("queryAll");
		return template.find(query, tClass);
	}

	@Override
	public List<T> pageQueryAll(String orderBy, String orderType, int pageNo, int pageSize, Criteria... criterias) {
		Query query = new Query();
		Criteria criteria=Criteria.where("isDeleted").is(false);
		if (criterias != null) {
			criteria.andOperator(criterias);
		}
		query.addCriteria(criteria);
		if(orderBy!=null&&!"".equals(orderBy)) {
			if ("asc".equals(orderType)) {
				query.with(Sort.by(Direction.ASC, orderBy));
			} else {
				query.with(Sort.by(Direction.DESC, orderBy));
			}
		}
		query.with(PageRequest.of(pageNo, pageSize));
		getLogger().debug("pageQueryAll");
		return template.find(query, tClass);
	}

	/**
	 * 按条件返回单条数据
	 */
	@Override
	public T queryObject(Criteria... criterias) {
		Query query = new Query();
		Criteria criteria=Criteria.where("isDeleted").is(false);
		if (criterias != null) {
			criteria.andOperator(criterias);
		}
		query.addCriteria(criteria);
		getLogger().debug("queryObject");
		return template.findOne(query, tClass);
	}

	/**
	 * 插入数据,返回主键pk的值
	 */
	@Override
	@Transactional
	public void insertObject(T object) {
		object.setIsDeleted(false);
		template.insert(object);
		getLogger().debug("insertObject");
	}

	/**
	 * 更新单条数据
	 */
	@Override
	@Transactional
	public Long updateObject(HashMap<String, Object> newPropertyValueMap, Criteria... criterias) {
		Query query = new Query();
		Criteria criteria=Criteria.where("isDeleted").is(false);
		if (criterias != null) {
			criteria.andOperator(criterias);
		}
		query.addCriteria(criteria);
		Update update = new Update();
		if (newPropertyValueMap != null) {
			for (String key : newPropertyValueMap.keySet()) {
				update.set(key, newPropertyValueMap.get(key));
			}
		}
		UpdateResult updateResult = template.updateMulti(query, update, tClass);
		getLogger().debug("updateObject:{}",updateResult.getModifiedCount());
		return updateResult.getModifiedCount();
	}

	/**
	 * 更新部分属性
	 */
	@Override
	@Transactional
	public Long patchObject(HashMap<String, Object> newPropertyValueMap, Criteria... criterias) {
		Query query = new Query();
		Criteria criteria=Criteria.where("isDeleted").is(false);
		if (criterias != null) {
			criteria.andOperator(criterias);
		}
		query.addCriteria(criteria);
		Update update = new Update();
		if (newPropertyValueMap != null) {
			for (String key : newPropertyValueMap.keySet()) {
				update.set(key, newPropertyValueMap.get(key));
			}
		}
		UpdateResult updateResult = template.updateMulti(query, update, tClass);
		getLogger().debug("patchObject:{}",updateResult.getModifiedCount());
		return updateResult.getModifiedCount();
	}

	/**
	 * 删除多条数据
	 */
	@Override
	@Transactional
	public Long deleteAll(String lastModifierId, String lastModifierName, Criteria... criterias) {
		Query query = new Query();
		Criteria criteria=Criteria.where("isDeleted").is(false);
		if (criterias != null) {
			criteria.andOperator(criterias);
		}
		query.addCriteria(criteria);
		Update update = new Update();
		update.set("lastModifierId", lastModifierId);
		update.set("lastModifierName", lastModifierName);
		update.set("lastModifiedTime", new Date());
		update.set("isDeleted", true);
		UpdateResult updateResult = template.updateMulti(query, update, tClass);
		getLogger().debug("deleteAll:{}",updateResult.getModifiedCount());
		return updateResult.getModifiedCount();
	}

	/**
	 * 更新多条数据
	 */
	@Override
	@Transactional
	public Long updateAll(HashMap<String, Object> newPropertyValueMap, Criteria... criterias) {
		Query query = new Query();
		Criteria criteria=Criteria.where("isDeleted").is(false);
		if (criterias != null) {
			criteria.andOperator(criterias);
		}
		query.addCriteria(criteria);
		Update update = new Update();
		if (newPropertyValueMap != null) {
			for (String key : newPropertyValueMap.keySet()) {
				update.set(key, newPropertyValueMap.get(key));
			}
		}
		UpdateResult updateResult = template.updateMulti(query, update, tClass);
		getLogger().debug("updateAll:{}",updateResult.getModifiedCount());
		return updateResult.getModifiedCount();
	}

	/**
	 * 删除单条数据
	 */
	@Override
	@Transactional
	public Long deleteById(String pkName, String id, String lastModifierId, String lastModifierName) {
		Query query = new Query();
		Criteria criteria=Criteria.where("isDeleted").is(false);
		criteria.andOperator(Criteria.where(pkName).is(id));
		query.addCriteria(criteria);
		Update update = new Update();
		update.set("lastModifierId", lastModifierId);
		update.set("lastModifierName", lastModifierName);
		update.set("lastModifiedTime", new Date());
		update.set("isDeleted", true);
		UpdateResult updateResult = template.updateMulti(query, update, tClass);
		getLogger().debug("deleteById:{}",updateResult.getModifiedCount());
		return updateResult.getModifiedCount();
	}
	protected Date now() {
		return new Date();
	}
}