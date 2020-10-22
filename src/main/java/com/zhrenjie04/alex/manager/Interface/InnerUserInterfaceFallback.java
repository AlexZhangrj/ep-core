package com.zhrenjie04.alex.manager.Interface;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.zhrenjie04.alex.core.User;
import com.zhrenjie04.alex.core.exception.CrisisError;

@Component
public class InnerUserInterfaceFallback implements InnerUserInterface {

	@Autowired
	InnerUserInterface userInterface;

	@Autowired
	RedisTemplate<String, String> redisTemplate;
	
	@Override
	public List<User> getUsersByIds(List<String> ids) {
		throw new CrisisError("user服务器繁忙，无法请求到对应数据");
	}

	@Override
	public User getUserById(String id) {
		if(redisTemplate.opsForValue().increment("user-getUserById"+id)<4) {//微服务挂掉或一定时间没返回，重试3次，为保证幂等性，订单id不变，订单id在调用此方法前通过雪花算法产生，
			redisTemplate.expire("user-getUserById"+id, 30, TimeUnit.SECONDS);
			return userInterface.getUserById(id);
		}
		throw new CrisisError("user服务器繁忙，已重试3次，无法请求到对应数据");
	}

}
