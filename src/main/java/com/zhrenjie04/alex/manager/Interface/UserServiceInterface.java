package com.zhrenjie04.alex.manager.Interface;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zhrenjie04.alex.core.User;

@FeignClient(name = "ep-manager-service", fallback = UserServiceInterfaceFallback.class)
public interface UserServiceInterface {
	@RequestMapping(value = "/inner/user/all-by-ids", method = RequestMethod.POST)
	List<User> getUsersByIds(@RequestBody List<String> ids);

	@RequestMapping(value = "/inner/user/{id}", method = RequestMethod.GET)
	User getUserById(@PathVariable(name = "id") String id);
}
