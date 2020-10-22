package com.zhrenjie04.alex.user.controller.inner;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/inner/user")
public class InnerUserController {
	
//	@Autowired
//	UserService userService;
//
//	@RequestMapping(value = "/all-by-ids", method = RequestMethod.POST)
//	@ResponseJsonWithFilter(type = User.class, include = "userId,username,realname,nickname,email,cellphone,portraitUrl,birthday,gender")
//	public List<User> getUsersByIds(@RequestBody List<String> ids) {
//		return userService.queryUsersByIds(ids);
//	}
//
//	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
//	@ResponseJsonWithFilter(type = User.class, include = "userId,username,realname,nickname,email,cellphone,portraitUrl,birthday,gender")
//	public User getUsersById(@PathVariable(name = "id") String id) {//订单系统调用库存系统，防止网络抖动服务重试导致的重复扣减：MySQL操作过程：1、扣件库存；2、insert库存扣减记录表（以orderId为唯一键）；3、异常则向上抛出CrisisError
//		return userService.queryUserById(id);
//	}
}
