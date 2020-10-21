package com.zhrenjie04.alex.manager.controller.back;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zhrenjie04.alex.core.AbstractGenericController;
import com.zhrenjie04.alex.core.FilterWithNoneFiltered;
import com.zhrenjie04.alex.core.FilterWithNormalFiltered;
import com.zhrenjie04.alex.core.JsonResult;
import com.zhrenjie04.alex.core.Permission;
import com.zhrenjie04.alex.core.ResponseJsonWithFilter;
import com.zhrenjie04.alex.core.TClazzLocal;
import com.zhrenjie04.alex.core.User;
import com.zhrenjie04.alex.manager.dao.PositionDao;
import com.zhrenjie04.alex.manager.domain.Position;
import com.zhrenjie04.alex.manager.service.PositionService;
import com.zhrenjie04.alex.util.SessionUtil;

/**
 * @author 张人杰
 */
@Controller
@RequestMapping("/user/back/position")
@Permission("position.back")
public class PositionBackController extends AbstractGenericController<Position, PositionDao, PositionService> {

	@Autowired
	PositionService positionService;

	@Override
	protected PositionService getService() {
		return positionService;
	}

	@Override
	@RequestMapping(value = "/page/search-{keyword}/order-by-{orderBy}/order-type-{orderType}/page-no/{pageNo}/page-size/{pageSize}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	@Permission("page-query")
	@ResponseJsonWithFilter(type = Position.class, include = "positionId,positionName,positionCode,isLocked,groupId,otherResults")
	public JsonResult pageQueryAll(HttpServletRequest request,
			@PathVariable(name = "keyword", required = true) String keyword,
			@PathVariable(name = "orderBy", required = false) String orderBy,
			@PathVariable(name = "orderType", required = false) String orderType,
			@PathVariable(name = "pageNo", required = true) Long pageNo,
			@PathVariable(name = "pageSize", required = true) Long pageSize) {
		User user=(User)request.getAttribute("user");
		HashMap<String, Object> params = new HashMap<String, Object>(16);
		params.put("keyword", keyword);
		String orderByFrontend = orderBy;
		switch (orderBy) {
		case "positionId":
			orderBy = "position_id";
			break;
		case "positionName":
			orderBy = "position_name";
			break;
		case "positionCode":
			orderBy = "position_code";
			break;
		case "isLocked":
			orderBy = "is_locked";
			break;
		default:
			orderBy = "position_id";
			orderByFrontend = "userId";
		}
		switch (orderType) {
		case "asc":
			orderType = "asc";
			break;
		case "desc":
			orderType = "desc";
			break;
		default:
			orderType = "asc";
		}
		params.put("orderByFrontend", orderByFrontend);
		params.put("orderBy", orderBy);
		params.put("orderType", orderType);
		params.put("pageNo", pageNo < 1 ? 1L : pageNo);
		if (pageSize > MAX_PAGESIZE) {
			pageSize = MAX_PAGESIZE;
		}
		if (pageSize < MIN_PAGESIZE) {
			pageSize = MIN_PAGESIZE;
		}
		params.put("pageSize", pageSize);
		TClazzLocal.set(tClazz);
		return getService().pageQueryAll(params, user);
	}
	@RequestMapping(value = "/all/search-{keyword}/order-by-{orderBy}/order-type-{orderType}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	@Permission("page-query")
	@ResponseJsonWithFilter(type = Position.class, include = "positionId,positionName,positionCode,isLocked,groupId,otherResults")
	public JsonResult queryAll(HttpServletRequest request,
			@PathVariable(name = "keyword", required = true) String keyword,
			@PathVariable(name = "orderBy", required = false) String orderBy,
			@PathVariable(name = "orderType", required = false) String orderType) {
		User user=(User)request.getAttribute("user");
		HashMap<String, Object> params = new HashMap<String, Object>(16);
		params.put("keyword", keyword);
		String orderByFrontend = orderBy;
		switch (orderBy) {
		case "positionId":
			orderBy = "position_id";
			break;
		case "positionName":
			orderBy = "position_name";
			break;
		case "positionCode":
			orderBy = "position_code";
			break;
		case "isLocked":
			orderBy = "is_locked";
			break;
		default:
			orderBy = "position_id";
			orderByFrontend = "userId";
		}
		switch (orderType) {
		case "asc":
			orderType = "asc";
			break;
		case "desc":
			orderType = "desc";
			break;
		default:
			orderType = "asc";
		}
		params.put("orderByFrontend", orderByFrontend);
		params.put("orderBy", orderBy);
		params.put("orderType", orderType);
		params.put("groupId", user.getCurrentJob().getGroupId());
		TClazzLocal.set(tClazz);
		return getService().queryAll(params, user);
	}

	@Override
	@RequestMapping(value = "/", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@Permission("add")
	@ResponseJsonWithFilter(type = FilterWithNormalFiltered.class)
	public JsonResult insertObject(HttpServletRequest request, @RequestBody @Validated Position object,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			StringBuilder sb = new StringBuilder();
			for (FieldError error : bindingResult.getFieldErrors()) {
				sb.append(error.getDefaultMessage()).append(";");
			}
			sb.deleteCharAt(sb.length() - 1);
			throw new RuntimeException(sb.toString());
		}
		User user=(User)request.getAttribute("user");
		TClazzLocal.set(tClazz);
		return getService().insertObject(object, user);
	}

	@Override
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = "application/json;charset=UTF-8")
	@Permission("modify")
	@ResponseJsonWithFilter(type = FilterWithNoneFiltered.class)
	public JsonResult updateObject(HttpServletRequest request, @PathVariable(name = "id", required = true) String id,
			@RequestBody @Validated Position object, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			StringBuilder sb = new StringBuilder();
			for (FieldError error : bindingResult.getFieldErrors()) {
				sb.append(error.getDefaultMessage()).append(";");
			}
			sb.deleteCharAt(sb.length() - 1);
			throw new RuntimeException(sb.toString());
		}
		User user=(User)request.getAttribute("user");
		TClazzLocal.set(tClazz);
		if (!ROOT_GROUP_ID.equals(user.getCurrentJob().getGroupId())) {
			// 非根系统用户只能修改本集团职位
			object.setGroupId(user.getCurrentJob().getGroupId());
		}
		return getService().updateObject(object, user);
	}

	@RequestMapping(value = "/{id}/lock", method = RequestMethod.PUT, produces = "application/json;charset=UTF-8")
	@Permission("lock")
	@ResponseJsonWithFilter(type = FilterWithNoneFiltered.class)
	public JsonResult lock(HttpServletRequest request, @PathVariable(name = "id", required = true) String id) {
		User user=(User)request.getAttribute("user");
		TClazzLocal.set(tClazz);
		Position object = new Position();
		object.setPositionId(id);
		object.setIsLocked(true);
		if (!ROOT_GROUP_ID.equals(user.getCurrentJob().getGroupId())) {
			// 非根系统用户只能修改本集团职位
			object.setGroupId(user.getCurrentJob().getGroupId());
		}
		return getService().lock(object, user);
	}

	@RequestMapping(value = "/{id}/unlock", method = RequestMethod.PUT, produces = "application/json;charset=UTF-8")
	@Permission("unlock")
	@ResponseJsonWithFilter(type = FilterWithNoneFiltered.class)
	public JsonResult unlock(HttpServletRequest request, @PathVariable(name = "id", required = true) String id) {
		User user=(User)request.getAttribute("user");
		TClazzLocal.set(tClazz);
		Position object = new Position();
		object.setPositionId(id);
		object.setIsLocked(false);
		if (!ROOT_GROUP_ID.equals(user.getCurrentJob().getGroupId())) {
			// 非根系统用户只能修改本集团职位
			object.setGroupId(user.getCurrentJob().getGroupId());
		}
		return getService().unlock(object, user);
	}
}
