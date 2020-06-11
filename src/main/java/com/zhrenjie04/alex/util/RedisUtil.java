package com.zhrenjie04.alex.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author 张人杰 -1永久存储 -2不更新存储时间
 */
public final class RedisUtil {

	private static final Logger logger = LoggerFactory.getLogger(RedisUtil.class);
	private static final String LOCK_SUCCESS = "OK";
	private static final String SET_IF_NOT_EXIST = "NX";
	private static final String SET_WITH_EXPIRE_TIME = "PX";
	private static final Long RELEASE_SUCCESS = 1L;

	private static int globalKeeptime = 7200;
	
	private static JedisPool jedisPool = null;

	public static void init(int maxTotal,int maxIdle,int minIdle,long maxWaitMillis,boolean testOnBorrow,int globalKeeptimeInput,String addr,int port,int timeout,String auth,int database) {
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxTotal(maxTotal);
		config.setMaxIdle(maxIdle);
		config.setMinIdle(minIdle);
		config.setMaxWaitMillis(maxWaitMillis);
		config.setTestOnBorrow(testOnBorrow);
		globalKeeptime = globalKeeptimeInput;
		if (auth == null || "".equals(auth)) {
			jedisPool = new JedisPool(config, addr, port, timeout, null, database);
		} else {
			jedisPool = new JedisPool(config, addr, port, timeout, auth, database);
		}
	}
	/**
	 * 获取Jedis实例
	 * 
	 * @return
	 */
	private synchronized static Jedis getJedis() {
		try {
			if (jedisPool != null) {
				Jedis resource = jedisPool.getResource();
				return resource;
			} else {
				throw new RuntimeException("jedisPool为空");
			}
		} catch (Exception e) {
			logger.error("RedisUtil", e);
			throw new RuntimeException("无法从jedisPool获取资源");
		}
	}

	/**
	 * 释放jedis资源
	 * 
	 * @param jedis
	 */
	private static void returnResource(final Jedis jedis) {
		if (jedis != null) {
			jedis.close();
		}
	}

	/**
	 * 操作缓存 例如用于User对象的清除
	 */
	public static void del(String key) {
		Jedis jedis = null;
		try {
			if (key == null || "".equals(key)) {
				return;
			}
			jedis = getJedis();
			jedis.del(key);
		} catch (Exception e) {
			logger.error("RedisUtil", e);
			throw e;
		} finally {
			returnResource(jedis);
		}
	}

	public static void set(String key, String value) {
		set(key, value, globalKeeptime);
	}

	/**
	 * -1永久存储 -2不更新存储时间
	 */
	public static void set(String key, String value, int keepTime) {
		Jedis jedis = null;
		try {
			if (key == null || "".equals(key)) {
				return;
			}
			if (value == null) {
				value = "";
			}
			jedis = getJedis();
			jedis.set(key, value);
			if (keepTime != -2) {
				jedis.expire(key, keepTime);
			}
		} catch (Exception e) {
			logger.error("RedisUtil", e);
			throw e;
		} finally {
			returnResource(jedis);
		}
	}

	public static void sset(String key, String... members) {
		sset(key, globalKeeptime, members);
	}

	/**
	 * 操作缓存 -1永久存储 -2不更新存储时间
	 */
	public static void sset(String key, int keepTime, String... members) {
		Jedis jedis = null;
		try {
			if (key == null || "".equals(key)) {
				return;
			}
			jedis = getJedis();
			jedis.sadd(key, members);
			if (keepTime != -2) {
				jedis.expire(key, keepTime);
			}
		} catch (Exception e) {
			logger.error("RedisUtil", e);
			throw e;
		} finally {
			returnResource(jedis);
		}
	}

	public static String get(String key) {
		return get(key, globalKeeptime);
	}

	/**
	 * 读取缓存 -1永久存储 -2不更新存储时间
	 */
	public static String get(String key, int keepTime) {
		Jedis jedis = null;
		try {
			if (key == null || "".equals(key)) {
				return null;
			}
			jedis = getJedis();
			String result = jedis.get(key);
			if (keepTime != -2) {
				jedis.expire(key, keepTime);
			}
			return result;
		} catch (Exception e) {
			logger.error("RedisUtil", e);
			throw e;
		} finally {
			returnResource(jedis);
		}
	}

	public static void hmset(String key, HashMap<String, String> hash) {
		hmset(key, hash, globalKeeptime);
	}

	/**
	 * -1永久存储 -2不更新存储时间
	 */
	public static void hmset(String key, HashMap<String, String> hash, int keepTime) {
		Jedis jedis = null;
		try {
			if (key == null || "".equals(key)) {
				return;
			}
			jedis = getJedis();
			jedis.hmset(key, hash);
			if (keepTime != -2) {
				jedis.expire(key, keepTime);
			}
		} catch (Exception e) {
			logger.error("RedisUtil", e);
			throw e;
		} finally {
			returnResource(jedis);
		}
	}

	public static void hset(String key, String field, String value) {
		hset(key, field, value, globalKeeptime);
	}

	/**
	 * -1永久存储 -2不更新存储时间
	 */
	public static void hset(String key, String field, String value, int keepTime) {
		Jedis jedis = null;
		try {
			if (key == null || "".equals(key)) {
				return;
			}
			if (field == null || "".equals(field)) {
				return;
			}
			if (value == null) {
				value = "";
			}
			jedis = getJedis();
			jedis.hset(key, field, value);
			if (keepTime != -2) {
				jedis.expire(key, keepTime);
			}
		} catch (Exception e) {
			logger.error("RedisUtil", e);
			throw e;
		} finally {
			returnResource(jedis);
		}
	}

	public static String hget(String key, String field) {
		return hget(key, field, globalKeeptime);
	}

	/**
	 * -1永久存储 -2不更新存储时间
	 */
	public static String hget(String key, String field, int keepTime) {
		Jedis jedis = null;
		try {
			if (key == null || "".equals(key)) {
				return null;
			}
			if (field == null || "".equals(field)) {
				return null;
			}
			jedis = getJedis();
			String result = jedis.hget(key, field);
			if (keepTime != -2) {
				jedis.expire(key, keepTime);
			}
			return result;
		} catch (Exception e) {
			logger.error("RedisUtil", e);
			throw e;
		} finally {
			returnResource(jedis);
		}
	}

	public static List<String> hmget(String key, String... fields) {
		return hmget(key, globalKeeptime, fields);
	}

	/**
	 * -1永久存储 -2不更新存储时间
	 */
	public static List<String> hmget(String key, int keepTime, String... fields) {
		Jedis jedis = null;
		try {
			if (key == null || "".equals(key)) {
				return null;
			}
			if (fields == null) {
				return null;
			}
			jedis = getJedis();
			List<String> result = jedis.hmget(key, fields);
			if (keepTime != -2) {
				jedis.expire(key, keepTime);
			}
			return result;
		} catch (Exception e) {
			logger.error("RedisUtil", e);
			throw e;
		} finally {
			returnResource(jedis);
		}
	}

	public static void sadd(String key, String... members) {
		sadd(key, globalKeeptime, members);
	}

	/**
	 * -1永久存储 -2不更新存储时间
	 */
	public static void sadd(String key, int keepTime, String... members) {
		Jedis jedis = null;
		try {
			if (key == null || "".equals(key)) {
				return;
			}
			jedis = getJedis();
			jedis.sadd(key, members);
			if (keepTime != -2) {
				jedis.expire(key, keepTime);
			}
		} catch (Exception e) {
			logger.error("RedisUtil", e);
			throw e;
		} finally {
			returnResource(jedis);
		}
	}

	public static Set<String> smembers(String key) {
		return smembers(key, globalKeeptime);
	}

	public static void srem(String key, String... members) {
		srem(key, globalKeeptime, members);
	}

	public static Long ttl(String key) {
		Jedis jedis = null;
		try {
			if (key == null || "".equals(key)) {
				return 0L;
			}
			jedis = getJedis();
			return jedis.ttl(key);
		} catch (Exception e) {
			logger.error("RedisUtil", e);
			throw e;
		} finally {
			returnResource(jedis);
		}
	}

	/**
	 * -1永久存储 -2不更新存储时间
	 */
	public static void srem(String key, int keepTime, String... members) {
		Jedis jedis = null;
		try {
			if (key == null || "".equals(key) || members == null || members.length == 0) {
				return;
			}
			jedis = getJedis();
			jedis.srem(key, members);
			if (keepTime != -2) {
				jedis.expire(key, keepTime);
			}
			return;
		} catch (Exception e) {
			logger.error("RedisUtil", e);
			throw e;
		} finally {
			returnResource(jedis);
		}
	}

	/**
	 * -1永久存储 -2不更新存储时间
	 */
	public static String spop(String key, int keepTime) {
		Jedis jedis = null;
		try {
			if (key == null || "".equals(key)) {
				return null;
			}
			jedis = getJedis();
			String value = jedis.spop(key);
			if (keepTime != -2) {
				jedis.expire(key, keepTime);
			}
			return value;
		} catch (Exception e) {
			logger.error("RedisUtil", e);
			throw e;
		} finally {
			returnResource(jedis);
		}
	}

	/**
	 * -1永久存储 -2不更新存储时间
	 */
	public static Set<String> smembers(String key, int keepTime) {
		Jedis jedis = null;
		try {
			if (key == null || "".equals(key)) {
				return Collections.emptySet();
			}
			jedis = getJedis();
			Set<String> members = jedis.smembers(key);
			if (keepTime != -2) {
				jedis.expire(key, keepTime);
			}
			return members;
		} catch (Exception e) {
			logger.error("RedisUtil", e);
			throw e;
		} finally {
			returnResource(jedis);
		}
	}

	public static boolean sismember(String key, String member) {
		return sismember(key, member, globalKeeptime);
	}

	/**
	 * -1永久存储 -2不更新存储时间
	 */
	public static boolean sismember(String key, String member, int keepTime) {
		Jedis jedis = null;
		try {
			if (key == null || "".equals(key)) {
				return false;
			}
			if (member == null || "".equals(member)) {
				return false;
			}
			jedis = getJedis();
			boolean result = jedis.sismember(key, member);
			if (keepTime != -2) {
				jedis.expire(key, keepTime);
			}
			return result;
		} catch (Exception e) {
			logger.error("RedisUtil", e);
			throw e;
		} finally {
			returnResource(jedis);
		}
	}

	/**
	 * -1永久存储 -2不更新存储时间
	 */
	public static Long inc(String key, int keepTime) {
		Jedis jedis = null;
		try {
			if (key == null || "".equals(key)) {
				return 0L;
			}
			jedis = getJedis();
			Long count = jedis.incr(key);
			if (keepTime != -2) {
				jedis.expire(key, keepTime);
			}
			return count;
		} catch (Exception e) {
			logger.error("RedisUtil", e);
			throw e;
		} finally {
			returnResource(jedis);
		}
	}

	public static Long inc(String key) {
		return inc(key, globalKeeptime);
	}

	/**
	 * 操作缓存 计数减1 -1永久存储 -2不更新存储时间
	 */
	public static Long decr(String key, int keepTime) {
		Jedis jedis = null;
		try {
			if (key == null || "".equals(key)) {
				return 0L;
			}
			jedis = getJedis();
			Long count = jedis.decr(key);
			if (keepTime != -2) {
				jedis.expire(key, keepTime);
			}
			return count;
		} catch (Exception e) {
			logger.error("RedisUtil", e);
			throw e;
		} finally {
			returnResource(jedis);
		}
	}

	public static Long decr(String key) {
		return decr(key, globalKeeptime);
	}

	/**
	 * 操作缓存 更新失效时间
	 */
	public static void touch(String key) {
		touch(key, globalKeeptime);
	}

	/**
	 * 操作缓存 更新失效时间 -1永久存储 -2不更新存储时间
	 */
	public static void touch(String key, int keepTime) {
		Jedis jedis = null;
		try {
			if (key == null || "".equals(key)) {
				return;
			}
			jedis = getJedis();
			if (keepTime != -2) {
				jedis.expire(key, keepTime);
			}
		} catch (Exception e) {
			logger.error("RedisUtil", e);
			throw e;
		} finally {
			returnResource(jedis);
		}
	}

	public static boolean lock(String key, int keepTime) {
		Jedis jedis = null;
		try {
			if (key == null || "".equals(key)) {
				return true;
			}
			jedis = getJedis();
//			String result = jedis.set(key, "1", SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, keepTime * 1000);
//			System.out.println(result);
//			if (LOCK_SUCCESS.equals(result)) {
//				return true;
//			}
			Long result = jedis.setnx(key, "1");
			if(result == 1) {
				return true;
			}
			return false;
		} catch (Exception e) {
			logger.error("RedisUtil", e);
			throw e;
		} finally {
			returnResource(jedis);
		}
	}

	public static boolean unlock(String key) {
		Jedis jedis = null;
		try {
			if (key == null || "".equals(key)) {
				return true;
			}
			jedis = getJedis();
			jedis.del(key);
			return true;
		} catch (Exception e) {
			logger.error("RedisUtil", e);
			throw e;
		} finally {
			returnResource(jedis);
		}
	}

	/**
	 * 操作缓存 永久保存key-value
	 * 
	 * @param key
	 * @param value
	 */
	public static void setPermanent(String key, String value) {
		Jedis jedis = null;
		try {
			if (key == null || "".equals(key)) {
				return;
			}
			if (value == null) {
				value = "";
			}
			jedis = getJedis();
			jedis.set(key, value);
			jedis.persist(key);
		} catch (Exception e) {
			logger.error("RedisUtil", e);
			throw e;
		} finally {
			returnResource(jedis);
		}
	}

	public static void main(String args[]) {
		RedisUtil.hset("admin", "username", "admin", 20 * 60);
		RedisUtil.hset("admin", "password", "123456", 20 * 60);
		RedisUtil.hset("admin", "nickname", "系统管理员", 20 * 60);
		RedisUtil.hset("admin", "realname", "系统管理员", 20 * 60);
		System.out.println(RedisUtil.hget("admin", "realname", 20 * 60));
		try {
			Thread.sleep(20 * 60 * 1000 + 1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(RedisUtil.hget("admin", "realname", 20 * 60));
		for (int i = 0; i < 10000000; i++) {
			System.out.println(RedisUtil.inc("onlineCount", 20));
		}
	}
}