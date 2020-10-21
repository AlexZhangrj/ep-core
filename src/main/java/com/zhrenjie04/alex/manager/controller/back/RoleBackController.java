package com.zhrenjie04.alex.manager.controller.back;

import java.util.HashMap;

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
import com.zhrenjie04.alex.manager.domain.MoveToRoleIds;
import com.zhrenjie04.alex.manager.domain.Role;
import com.zhrenjie04.alex.manager.dao.RoleDao;
import com.zhrenjie04.alex.manager.service.RoleService;
import com.zhrenjie04.alex.util.SessionUtil;
/**
 * @author 张人杰
 */
@Controller
@RequestMapping("/user/back/role")
@Permission("role.back")
public class RoleBackController extends AbstractGenericController<Role, RoleDao, RoleService>{
	
	@Autowired
	RoleService roleService;
	
	@Override
	protected RoleService getService() {
		return roleService;
	}
	@RequestMapping(value = "/trees", method = RequestMethod.GET, produces="application/json;charset=UTF-8")
	@Permission("trees")
	@ResponseJsonWithFilter(type=Role.class,include="roleId,roleName,roleCode,children")
	public JsonResult trees(HttpServletRequest request) {
		User user=(User)request.getAttribute("user");
		return roleService.trees(user);
	}
	@RequestMapping(value = "/path/{roleId}", method = RequestMethod.GET, produces="application/json;charset=UTF-8")
	@Permission("trees")
	@ResponseJsonWithFilter(type=Role.class,include="roleId,roleName")
	public JsonResult path(HttpServletRequest request,@PathVariable(name="roleId",required=true)String roleId) {
		User user=(User)request.getAttribute("user");
		return roleService.path(roleId,user);
	}
	@RequestMapping(value = "/page/parent-id-{parentId}/search-{keyword}/order-by-{orderBy}/order-type-{orderType}/page-no/{pageNo}/page-size/{pageSize}", method = RequestMethod.GET, produces="application/json;charset=UTF-8")
	@Permission("trees")
	@ResponseJsonWithFilter(type=Role.class,include="roleId,roleName,roleCode,parentId,idPath,isLocked,groupId,otherResults")
	public JsonResult pageQueryAll(HttpServletRequest request,@PathVariable(name="parentId",required=true) String parentId,@PathVariable(name="keyword",required=true) String keyword,@PathVariable(name="orderBy",required=false) String orderBy,@PathVariable(name="orderType",required=false) String orderType,@PathVariable(name="pageNo",required=true) Long pageNo,@PathVariable(name="pageSize",required=true) Long pageSize) {
		User user=(User)request.getAttribute("user");
		HashMap<String,Object>params=new HashMap<String, Object>(16);
		params.put("parentId", parentId);
		params.put("keyword", keyword);
		String orderByFrontend=orderBy;
		switch(orderBy) {
		case "roleId":
			orderBy="role_id";
			break;
		case "roleName":
			orderBy="convert(role_name using gbk)";
			break;
		case "roleCode":
			orderBy="role_code";
			break;
		case "parentId":
			orderBy="parent_id";
			break;
		case "idPath":
			orderBy="id_path";
			break;
		case "isLocked":
			orderBy="is_locked";
			break;
		default:
			orderBy="convert(role_name using gbk)";
			orderByFrontend="roleName";
		}
		switch(orderType) {
		case "asc":
			orderType="asc";
			break;
		case "desc":
			orderType="desc";
			break;
		default:
			orderType="asc";
		}
		params.put("orderByFrontend", orderByFrontend);
		params.put("orderBy", orderBy);
		params.put("orderType", orderType);
		params.put("pageNo", pageNo<1?1L:pageNo);
		if(pageSize>MAX_PAGESIZE){
			pageSize=MAX_PAGESIZE;
		}else if(pageSize < MIN_PAGESIZE){
			pageSize=MIN_PAGESIZE;
		}
		params.put("pageSize", pageSize);
		TClazzLocal.set(tClazz);
		return getService().pageQueryAll(params, user);
	}
	
	@RequestMapping(value = "/move-to/{roleId}", method = RequestMethod.PUT, produces="application/json;charset=UTF-8")
	@Permission("move-to")
	@ResponseJsonWithFilter(type=FilterWithNoneFiltered.class)
	public JsonResult moveTo(HttpServletRequest request,@PathVariable(name="roleId")String roleId,@RequestBody MoveToRoleIds moveToRoleIds) {
		User user=(User)request.getAttribute("user");
		return getService().moveTo(roleId,moveToRoleIds.getRoleIds(), user);
	}
	@RequestMapping(value = "/{id}/lock", method = RequestMethod.PUT, produces="application/json;charset=UTF-8")
	@Permission("lock")
	@ResponseJsonWithFilter(type=FilterWithNoneFiltered.class)
	public JsonResult lock(HttpServletRequest request,@PathVariable(name="id",required=true) String id) {
		User user=(User)request.getAttribute("user");
		TClazzLocal.set(tClazz);
		Role object = new Role();
		object.setRoleId(id);
		object.setIsLocked(true);
		return getService().lock(object, user);
	}

	@RequestMapping(value = "/{id}/unlock", method = RequestMethod.PUT, produces="application/json;charset=UTF-8")
	@Permission("unlock")
	@ResponseJsonWithFilter(type=FilterWithNoneFiltered.class)
	public JsonResult unlock(HttpServletRequest request,@PathVariable(name="id",required=true) String id) {
		User user=(User)request.getAttribute("user");
		TClazzLocal.set(tClazz);
		Role object = new Role();
		object.setRoleId(id);
		object.setIsLocked(true);
		return getService().unlock(object, user);
	}

}
