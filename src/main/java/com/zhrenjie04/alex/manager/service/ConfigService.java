package com.zhrenjie04.alex.manager.service;

import com.zhrenjie04.alex.core.JsonResult;
import com.zhrenjie04.alex.core.User;
import com.zhrenjie04.alex.manager.domain.Config;

/**
 * @author 张人杰
 */
public interface ConfigService{
	JsonResult saveConfig(Config config, User user);
	JsonResult loadConfig(Config config, User user);
}
