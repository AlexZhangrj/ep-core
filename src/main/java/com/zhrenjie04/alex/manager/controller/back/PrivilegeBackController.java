package com.zhrenjie04.alex.manager.controller.back;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zhrenjie04.alex.core.AbstractGenericController;
import com.zhrenjie04.alex.core.FilterWithNoneFiltered;
import com.zhrenjie04.alex.core.JsonResult;
import com.zhrenjie04.alex.core.Permission;
import com.zhrenjie04.alex.core.ResponseJsonWithFilter;
import com.zhrenjie04.alex.core.TClazzLocal;
import com.zhrenjie04.alex.core.User;
import com.zhrenjie04.alex.manager.domain.MoveToPrivilegeIds;
import com.zhrenjie04.alex.manager.domain.Privilege;
import com.zhrenjie04.alex.manager.dao.PrivilegeDao;
import com.zhrenjie04.alex.manager.service.PrivilegeService;
import com.zhrenjie04.alex.util.SessionUtil;
/**
 * @author 张人杰
 */
@Controller
@RequestMapping("/user/back/privilege")
@Permission("privilege.back")
public class PrivilegeBackController extends AbstractGenericController<Privilege, PrivilegeDao, PrivilegeService> {

	@Autowired
	PrivilegeService privilegeService;

	@Override
	protected PrivilegeService getService() {
		return privilegeService;
	}

	@RequestMapping(value = "/trees", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	@Permission("trees")
	@ResponseJsonWithFilter(type = Privilege.class, include = "privilegeId,privilegeName,privilegeCode,frontEndSystem,isMenu,children")
	public JsonResult trees(HttpServletRequest request) {
		User user=(User)request.getAttribute("user");
		return privilegeService.trees(user);
	}

	@RequestMapping(value = "/path/{privilegeId}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	@Permission("trees")
	@ResponseJsonWithFilter(type = Privilege.class, include = "privilegeId,privilegeName")
	public JsonResult path(HttpServletRequest request, @PathVariable(name = "privilegeId", required = true) String privilegeId) {
		User user=(User)request.getAttribute("user");
		return privilegeService.path(privilegeId, user);
	}

	@RequestMapping(value = "/page/parent-id-{parentId}/search-{keyword}/order-by-{orderBy}/order-type-{orderType}/page-no/{pageNo}/page-size/{pageSize}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	@Permission("trees")
	@ResponseJsonWithFilter(type = Privilege.class, include = "privilegeId,privilegeName,privilegeCode,frontEndSystem,simpleUrl,requestMethod,isMenu,parentId,idPath,isLocked,iconClass,iconType,ab,collapse,sortValue")
	public JsonResult pageQueryAll(HttpServletRequest request,
			@PathVariable(name = "parentId", required = true) String parentId,
			@PathVariable(name = "keyword", required = true) String keyword,
			@PathVariable(name = "orderBy", required = false) String orderBy,
			@PathVariable(name = "orderType", required = false) String orderType,
			@PathVariable(name = "pageNo", required = true) Long pageNo,
			@PathVariable(name = "pageSize", required = true) Long pageSize) {
		User user=(User)request.getAttribute("user");
		HashMap<String, Object> params = new HashMap<String, Object>(16);
		params.put("parentId", parentId);
		params.put("keyword", keyword);
		String orderByFrontend = orderBy;
		switch (orderBy) {
		case "privilegeId":
			orderBy = "privilege_id";
			break;
		case "privilegeName":
			orderBy = "convert(privilege_name using gbk)";
			break;
		case "parentId":
			orderBy = "parent_id";
			break;
		case "idPath":
			orderBy = "id_path";
			break;
		case "isLocked":
			orderBy = "is_locked";
			break;
		default:
			orderBy = "convert(privilege_name using gbk)";
			orderByFrontend = "privilegeName";
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

	@RequestMapping(value = "/move-to/{privilegeId}", method = RequestMethod.PUT, produces = "application/json;charset=UTF-8")
	@Permission("move-to")
	@ResponseJsonWithFilter(type = FilterWithNoneFiltered.class)
	public JsonResult moveTo(HttpServletRequest request, @PathVariable(name = "privilegeId") String privilegeId,
			@RequestBody MoveToPrivilegeIds moveToPrivilegeIds) {
		User user=(User)request.getAttribute("user");
		return getService().moveTo(privilegeId, moveToPrivilegeIds.getPrivilegeIds(), user);
	}

	@RequestMapping(value = "/{id}/lock", method = RequestMethod.PUT, produces = "application/json;charset=UTF-8")
	@Permission("lock")
	@ResponseJsonWithFilter(type = FilterWithNoneFiltered.class)
	public JsonResult lock(HttpServletRequest request, @PathVariable(name = "id", required = true) String id) {
		User user=(User)request.getAttribute("user");
		TClazzLocal.set(tClazz);
		Privilege object = new Privilege();
		object.setPrivilegeId(id);
		object.setIsLocked(true);
		return getService().lock(object, user);
	}

	@RequestMapping(value = "/{id}/unlock", method = RequestMethod.PUT, produces = "application/json;charset=UTF-8")
	@Permission("unlock")
	@ResponseJsonWithFilter(type = FilterWithNoneFiltered.class)
	public JsonResult unlock(HttpServletRequest request, @PathVariable(name = "id", required = true) String id) {
		User user=(User)request.getAttribute("user");
		TClazzLocal.set(tClazz);
		Privilege object = new Privilege();
		object.setPrivilegeId(id);
		object.setIsLocked(true);
		return getService().unlock(object, user);
	}
	
	@RequestMapping(value = "/all/by-role-id-{roleId}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	@Permission("load-role-original-privileges")
	@ResponseJsonWithFilter(type = FilterWithNoneFiltered.class)
	public JsonResult loadRoleOriginalPrivileges(HttpServletRequest request, @PathVariable(name = "roleId", required = true) String roleId) {
		User user=(User)request.getAttribute("user");
		TClazzLocal.set(tClazz);
		return getService().loadRoleOriginalPrivileges(roleId, user);
	}
	@RequestMapping(value = "/all/by-current-user", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	@Permission("load-current-user-job-privileges")
	@ResponseJsonWithFilter(type = FilterWithNoneFiltered.class)
	public JsonResult loadCurrentUserJobPrivileges(HttpServletRequest request) {
		User user=(User)request.getAttribute("user");
		TClazzLocal.set(tClazz);
		return getService().loadCurrentUserJobPrivileges(user);
	}
	@RequestMapping(value = "/config/role-id-{roleId}/privileges", method = RequestMethod.PUT, produces = "application/json;charset=UTF-8")
	@Permission("load-current-user-job-privileges")
	@ResponseJsonWithFilter(type = FilterWithNoneFiltered.class)
	public JsonResult configRolePrivileges(HttpServletRequest request, @PathVariable(name = "roleId", required = true) String roleId,@RequestBody List<String> privilegeIds) {
		User user=(User)request.getAttribute("user");
		TClazzLocal.set(tClazz);
		return getService().configRolePrivileges(roleId,privilegeIds,user);
	}
}
