package com.zhrenjie04.alex.util;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.zhrenjie04.alex.core.User;

/**
 * 此类用于以后Session提取工作 所有session中存储的内容，以后会提取到redis中
 * 所有session中提取的对象，修改后需要调用set方法重新保存入session中
 * 
 * @author Alex
 *
 */
public class SessionUtil {

	private static Integer sessionKeepTimeSeconds = 20;

	public static void init(Integer sessionKeepTimeSecondsConfig) {
		sessionKeepTimeSeconds = sessionKeepTimeSecondsConfig;
	}

	/**
	 * 获取session中的User对象
	 * 
	 * @param request
	 * @return
	 */
	public static User getSessionUser(HttpServletRequest request) {
		String sid = (String) request.getAttribute("sid");
		String userJson = RedisUtil.hget(sid, "sessionUser",sessionKeepTimeSeconds);
		if (userJson != null) {
			User user = JsonUtil.parse(userJson, User.class);
			String userSids = "user-sids-" + user.getUserId();
			RedisUtil.touch(userSids, sessionKeepTimeSeconds);
			return user;
		} else {
			return null;
		}
	}

	public static void setSessionUser(HttpServletRequest request, User user) {
		String sid = (String) request.getAttribute("sid");
		String userJson = JsonUtil.stringify(user);
		RedisUtil.hset(sid, "sessionUser", userJson,sessionKeepTimeSeconds);
		String userSids = "user-sids-" + user.getUserId();
		RedisUtil.touch(userSids, sessionKeepTimeSeconds);
	}

	/**
	 * 设置session中的变量
	 * 
	 * @param request
	 * @param key
	 * @param value
	 */
	public static void setSession(HttpServletRequest request, String key, Object value) {
		String sid = (String) request.getAttribute("sid");
		String json = JsonUtil.stringify(value);
		RedisUtil.hset(sid, "key:"+key, json,sessionKeepTimeSeconds);
		String userJson = RedisUtil.hget(sid, "sessionUser", sessionKeepTimeSeconds);
		if (userJson != null) {
			User user = JsonUtil.parse(userJson, User.class);
			String userSids = "user-sids-" + user.getUserId();
			RedisUtil.touch(userSids, sessionKeepTimeSeconds);
		}
	}

	/**
	 * 获取存储于redis中的session中变量的值 所有session中提取的对象，修改后需要调用set方法重新保存入session中
	 */
	public static <T> T getSessionValueByKey(HttpServletRequest request, String key, Class<T> clazz) {
		String sid = (String) request.getAttribute("sid");
		String json = RedisUtil.hget(sid, "key:"+key, sessionKeepTimeSeconds);
		if (json == null) {
			return null;
		}
		String userJson = RedisUtil.hget(sid, "sessionUser", sessionKeepTimeSeconds);
		if (userJson != null) {
			User user = JsonUtil.parse(userJson, User.class);
			String userSids = "user-sids-" + user.getUserId();
			RedisUtil.touch(userSids, sessionKeepTimeSeconds);
		}
		if(clazz.equals(String.class)) {
			return (T)json;
		}else {
			return JsonUtil.parse(json, clazz);
		}
	}
	
	/**
	 * 获取存储于redis中的session中变量的数组对象值，比如：[1,2,3,4,5,6]
	 * 所有session中提取的对象，修改后需要调用set方法重新保存入session中
	 */
	public static <T> T getSessionValueByKey(HttpServletRequest request, String key, TypeReference<T> typeReference) {
		String sid = (String) request.getAttribute("sid");
		String json = RedisUtil.hget(sid, "key:"+key, sessionKeepTimeSeconds);
		if (json == null) {
			return null;
		}
		String userJson = RedisUtil.hget(sid, "sessionUser", sessionKeepTimeSeconds);
		if (userJson != null) {
			User user = JsonUtil.parse(userJson, User.class);
			String userSids = "user-sids-" + user.getUserId();
			RedisUtil.touch(userSids, sessionKeepTimeSeconds);
		}
		return JsonUtil.parse(json, typeReference);
	}

	/**
	 * 按sid清除redis中的user对象
	 * 
	 * @param request
	 */
	public static void killSession(HttpServletRequest request) {
		String sid = (String) request.getAttribute("sid");
		RedisUtil.del(sid);
		RedisUtil.sadd("banedTokens", 3*24*3600, sid);
	}

	/**
	 * 根据sid移除用户session
	 * 
	 * @param sid
	 */
	public static void killSessionBySid(String sid) {
		if(sid==null) {
			return;
		}
		RedisUtil.del(sid);
		RedisUtil.sadd("banedTokens", 3*24*3600, sid);
	}

	/**
	 * 强制用户掉线
	 */
	public static void killAllSessionsByUserId(String userId) {
		String userSids = "user-sids-" + userId;
		Set<String> sids = RedisUtil.smembers(userSids, sessionKeepTimeSeconds);
		for (String sid : sids) {
			RedisUtil.del(sid);
		}
		RedisUtil.del(userSids);
	}

	/**
	 * 根据sid返回User对象
	 * 
	 * @param sid
	 * @return
	 */
	public static User getSessionUserBySid(String sid) {
		String userJson = RedisUtil.hget(sid, "sessionUser", sessionKeepTimeSeconds);
		if (userJson != null) {
			User user = JsonUtil.parse(userJson, User.class);
			String userSids = "user-sids-" + user.getUserId();
			RedisUtil.touch(userSids, sessionKeepTimeSeconds);
			return user;
		} else {
			return null;
		}
	}
}