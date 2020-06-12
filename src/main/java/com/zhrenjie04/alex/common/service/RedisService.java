package com.zhrenjie04.alex.common.service;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.zhrenjie04.alex.util.RedisUtil;

@Service
public class RedisService {
	
	/**
	 * Redis服务器IP
	 */
	@Value("${redis.addr}")
	private String addr = "127.0.0.1";

	/**
	 * Redis的端口号
	 */
	@Value("${redis.port}")
	private int port = 6379;

	/**
	 * 访问密码
	 */
	@Value("${redis.auth}")
	private String auth = "admin";

	/**
	 * 可用连接实例的最大数目，默认值为256；
	 * 如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
	 */
	@Value("${redis.max-total}")
	private int maxTotal = 256;

	/**
	 * 控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值也是8。
	 */
	@Value("${redis.max-idle}")
	private int maxIdle = 200;

	/**
	 * 控制一个pool最少有多少个状态为idle(空闲的)的jedis实例，默认值也是8。
	 */
	@Value("${redis.min-idle}")
	private int minIdle = 256;

	/**
	 * 等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException；
	 */
	@Value("${redis.max-wait-millis}")
	private long maxWaitMillis = 10000;

	@Value("${redis.timeout}")
	private int timeout = 10000;

	@Value("${redis.database}")
	private int database = 0;

	@Value("${redis.global-keeptime}")
	private int globalKeeptimeInput = 7200;
	/**
	 * 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
	 */
	@Value("${redis.test-on-borrow}")
	private boolean testOnBorrow = true;
	
	@PostConstruct
	private void init() {
		RedisUtil.init(maxTotal, maxIdle, minIdle, maxWaitMillis, testOnBorrow, globalKeeptimeInput, addr, port, timeout, auth, database);
	}
	
	/**
	 * 操作缓存 例如用于User对象的清除
	 */
	public void del(String key) {
		RedisUtil.del(key);
	}

	public void set(String key, String value) {
		RedisUtil.set(key, value);
	}

	/**
	 * -1永久存储 -2不更新存储时间
	 */
	public void set(String key, String value, int keepTime) {
		RedisUtil.set(key, value, keepTime);
	}

	public void sset(String key, String... members) {
		RedisUtil.sset(key, members);
	}

	/**
	 * 操作缓存 -1永久存储 -2不更新存储时间
	 */
	public void sset(String key, int keepTime, String... members) {
		RedisUtil.sset(key, keepTime, members);
	}

	public String get(String key) {
		return RedisUtil.get(key);
	}

	/**
	 * 读取缓存 -1永久存储 -2不更新存储时间
	 */
	public String get(String key, int keepTime) {
		return RedisUtil.get(key, keepTime);
	}

	public void hmset(String key, HashMap<String, String> hash) {
		RedisUtil.hmset(key, hash);
	}

	/**
	 * -1永久存储 -2不更新存储时间
	 */
	public void hmset(String key, HashMap<String, String> hash, int keepTime) {
		RedisUtil.hmset(key, hash, keepTime);
	}

	public void hset(String key, String field, String value) {
		RedisUtil.hset(key, field, value);
	}

	/**
	 * -1永久存储 -2不更新存储时间
	 */
	public void hset(String key, String field, String value, int keepTime) {
		RedisUtil.hset(key, field, value, keepTime);
	}

	public String hget(String key, String field) {
		return RedisUtil.hget(key, field);
	}

	/**
	 * -1永久存储 -2不更新存储时间
	 */
	public String hget(String key, String field, int keepTime) {
		return RedisUtil.hget(key, field, keepTime);
	}

	public List<String> hmget(String key, String... fields) {
		return RedisUtil.hmget(key, fields);
	}

	/**
	 * -1永久存储 -2不更新存储时间
	 */
	public List<String> hmget(String key, int keepTime, String... fields) {
		return RedisUtil.hmget(key, keepTime, fields);
	}

	public void sadd(String key, String... members) {
		RedisUtil.sadd(key, members);
	}

	/**
	 * -1永久存储 -2不更新存储时间
	 */
	public void sadd(String key, int keepTime, String... members) {
		RedisUtil.sadd(key, keepTime, members);
	}

	public Set<String> smembers(String key) {
		return RedisUtil.smembers(key);
	}

	public void srem(String key, String... members) {
		RedisUtil.srem(key, members);
	}

	public Long ttl(String key) {
		return RedisUtil.ttl(key);
	}

	/**
	 * -1永久存储 -2不更新存储时间
	 */
	public void srem(String key, int keepTime, String... members) {
		RedisUtil.srem(key, keepTime, members);
	}

	/**
	 * -1永久存储 -2不更新存储时间
	 */
	public String spop(String key, int keepTime) {
		return RedisUtil.spop(key, keepTime);
	}

	/**
	 * -1永久存储 -2不更新存储时间
	 */
	public Set<String> smembers(String key, int keepTime) {
		return RedisUtil.smembers(key, keepTime);
	}

	public boolean sismember(String key, String member) {
		return RedisUtil.sismember(key, member);
	}

	/**
	 * -1永久存储 -2不更新存储时间
	 */
	public static boolean sismember(String key, String member, int keepTime) {
		return RedisUtil.sismember(key, member, keepTime);
	}

	/**
	 * -1永久存储 -2不更新存储时间
	 */
	public Long inc(String key, int keepTime) {
		return RedisUtil.inc(key,keepTime);
	}

	public Long inc(String key) {
		return RedisUtil.inc(key);
	}

	/**
	 * 操作缓存 计数减1 -1永久存储 -2不更新存储时间
	 */
	public Long decr(String key, int keepTime) {
		return RedisUtil.decr(key, keepTime);
	}

	public Long decr(String key) {
		return RedisUtil.decr(key);
	}

	/**
	 * 操作缓存 更新失效时间
	 */
	public void touch(String key) {
		RedisUtil.touch(key);
	}

	/**
	 * 操作缓存 更新失效时间 -1永久存储 -2不更新存储时间
	 */
	public void touch(String key, int keepTime) {
		RedisUtil.touch(key,keepTime);
	}

	public boolean lock(String key, int keepTime) {
		return RedisUtil.lock(key, keepTime);
	}

	public boolean unlock(String key) {
		return RedisUtil.unlock(key);
	}

	/**
	 * 操作缓存 永久保存key-value
	 * 
	 * @param key
	 * @param value
	 */
	public void setPermanent(String key, String value) {
		RedisUtil.setPermanent(key, value);
	}
}
