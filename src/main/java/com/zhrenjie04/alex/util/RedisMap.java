package com.zhrenjie04.alex.util;

import java.lang.reflect.ParameterizedType;

public class RedisMap<V>{

	protected Class<V> vClazz;

	public RedisMap() {
		vClazz = (Class<V>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
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
