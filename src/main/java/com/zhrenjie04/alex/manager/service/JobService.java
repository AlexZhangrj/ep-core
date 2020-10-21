package com.zhrenjie04.alex.manager.service;

import java.util.HashMap;

import com.zhrenjie04.alex.core.JsonResult;
import com.zhrenjie04.alex.core.User;
/**
 * @author 张人杰
 */
public interface JobService{
	JsonResult pageQueryAll(HashMap<String,Object>params,User sessionUser);
	/**
	 * 删除多条数据，返回json结果
	 * @param params
	 * @param sessionUser
	 * @return
	 */
	JsonResult deleteUserJobRoles(String[] ids, User sessionUser);
}
