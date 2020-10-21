package com.zhrenjie04.alex.manager.controller.back;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zhrenjie04.alex.core.FilterWithNoneFiltered;
import com.zhrenjie04.alex.core.JsonResult;
import com.zhrenjie04.alex.core.Permission;
import com.zhrenjie04.alex.core.ResponseJsonWithFilter;
import com.zhrenjie04.alex.core.User;
import com.zhrenjie04.alex.manager.domain.Config;
import com.zhrenjie04.alex.manager.service.ConfigService;
import com.zhrenjie04.alex.util.SessionUtil;

/**
 * @author 张人杰
 */
@Controller
@RequestMapping("/user/back/config")
@Permission("config.back")
public class ConfigBackController {
	
	@Autowired
	ConfigService configService;
	
	@RequestMapping(value = "/save", method = RequestMethod.PUT, produces="application/json;charset=UTF-8")
	@Permission("save")
	@ResponseJsonWithFilter(type=FilterWithNoneFiltered.class)
	public JsonResult save(HttpServletRequest request,@RequestBody Config object) {
		User user=(User)request.getAttribute("user");
		return configService.saveConfig(object, user);
	}
	@RequestMapping(value = "/load/user-ids-{userIds}/org-{orgId}/position-{positionId}", method = RequestMethod.GET, produces="application/json;charset=UTF-8")
	@Permission("load")
	@ResponseJsonWithFilter(type=FilterWithNoneFiltered.class)
	public JsonResult load(HttpServletRequest request,@PathVariable(name="userIds",required=true)String userIds,@PathVariable(name="orgId",required=true)String orgId,@PathVariable(name="positionId",required=true)String positionId) {
		User user=(User)request.getAttribute("user");
		Config config = new Config();
		config.setUserIds(Arrays.asList(userIds.split(",")));
		config.setOrgId(orgId);
		config.setPositionId(positionId);
		return configService.loadConfig(config, user);
	}
}
