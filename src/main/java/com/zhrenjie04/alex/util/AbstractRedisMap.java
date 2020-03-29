package com.zhrenjie04.alex.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class AbstractRedisMap<V> {

	protected Class<V> vClazz;

	@SuppressWarnings("unchecked")
	public AbstractRedisMap() {
		Type superType=this.getClass().getGenericSuperclass();
		vClazz = (Class<V>) ((ParameterizedType)superType).getActualTypeArguments()[0];//此方法必须写在父类中
	}

	public V get(String key) {
		String json=RedisUtil.get(key);
		return JsonUtil.parse(json, vClazz);
	}
    public V put(String key, V value) {
		RedisUtil.set(key, JsonUtil.stringify(value));
    	return value;
    }
    public V remove(String key) {
		String json=RedisUtil.get(key);
    	RedisUtil.del(key);
		return JsonUtil.parse(json, vClazz);
    }
}
