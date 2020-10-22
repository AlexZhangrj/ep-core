package com.zhrenjie04.alex.core;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author 张人杰
 */
public abstract class AbstractGenericController<T extends AbstractGenericEntity, D extends GenericDao<T>, S extends GenericService<T, D>> {

	protected static final Long MAX_PAGESIZE = 200L;
	protected static final Long MIN_PAGESIZE = 10L;
	protected static final String ROOT_GROUP_ID = "0";
	protected static final String ROOT_ORG_ID = "0";

	protected Class<?> tClazz;

	public AbstractGenericController() {
		tClazz = (Class<?>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

	/**
	 * 用于具体Controller实例调用具体Service实例
	 * 
	 * @return
	 */
	protected abstract S getService();

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	@Permission("query-object")
//	@ResponseJsonWithFilter(type = FilterWithNormalFiltered.class)
	@ResponseJsonWithFilter(type = FilterWithNoneFiltered.class)
	public JsonResult queryObject(HttpServletRequest request, @PathVariable(name = "id", required = true) String id) {
		User user =(User)request.getAttribute("user");
		HashMap<String, Object> params = new HashMap<String, Object>(16);
		params.put("id", id);
		TClazzLocal.set(tClazz);
		return getService().queryObject(params, user);
	}

	@RequestMapping(value = "/all", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	@Permission("query-all")
//	@ResponseJsonWithFilter(type = FilterWithNormalFiltered.class)
	@ResponseJsonWithFilter(type = FilterWithNoneFiltered.class)
	public JsonResult queryAll(HttpServletRequest request) {
		User user =(User)request.getAttribute("user");
		HashMap<String, Object> params = new HashMap<String, Object>(16);
		TClazzLocal.set(tClazz);
		return getService().queryAll(params, user);
	}

	@RequestMapping(value = "/page/search-{keyword}/order-by-{orderBy}/order-type-{orderType}/page-no/{pageNo}/page-size/{pageSize}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	@Permission("page-query")
//	@ResponseJsonWithFilter(type = FilterWithNormalFiltered.class)
	@ResponseJsonWithFilter(type = FilterWithNoneFiltered.class)
	public JsonResult pageQueryAll(HttpServletRequest request,
			@PathVariable(name = "keyword", required = true) String keyword,
			@PathVariable(name = "orderBy", required = false) String orderBy,
			@PathVariable(name = "orderType", required = false) String orderType,
			@PathVariable(name = "pageNo", required = true) Long pageNo,
			@PathVariable(name = "pageSize", required = true) Long pageSize) {
		User user =(User)request.getAttribute("user");
		HashMap<String, Object> params = new HashMap<String, Object>(16);
		params.put("keyword", keyword);
		params.put("orderBy", orderBy);
		params.put("orderType", orderType);
		params.put("pageNo", pageNo < 1 ? 1L : pageNo);
		if (pageSize > MAX_PAGESIZE) {
			pageSize = MAX_PAGESIZE;
		} else if (pageSize < MIN_PAGESIZE) {
			pageSize = MIN_PAGESIZE;
		}
		params.put("pageSize", pageSize);
		TClazzLocal.set(tClazz);
		return getService().pageQueryAll(params, user);
	}

	@RequestMapping(value = "/", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@Permission("add")
//	@ResponseJsonWithFilter(type = FilterWithNormalFiltered.class)
	@ResponseJsonWithFilter(type = FilterWithNoneFiltered.class)
	public JsonResult insertObject(HttpServletRequest request, @RequestBody @Validated T object,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			StringBuilder sb = new StringBuilder();
			for (FieldError error : bindingResult.getFieldErrors()) {
				sb.append(error.getDefaultMessage()).append(";");
			}
			sb.deleteCharAt(sb.length() - 1);
			throw new RuntimeException(sb.toString());
		}
		User user =(User)request.getAttribute("user");
		TClazzLocal.set(tClazz);
		return getService().insertObject(object, user);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = "application/json;charset=UTF-8")
	@Permission("modify")
	@ResponseJsonWithFilter(type = FilterWithNoneFiltered.class)
	public JsonResult updateObject(HttpServletRequest request, @PathVariable(name = "id", required = true) String id,
			@RequestBody @Validated T object, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			StringBuilder sb = new StringBuilder();
			for (FieldError error : bindingResult.getFieldErrors()) {
				sb.append(error.getDefaultMessage()).append(";");
			}
			sb.deleteCharAt(sb.length() - 1);
			throw new RuntimeException(sb.toString());
		}
		User user =(User)request.getAttribute("user");
		TClazzLocal.set(tClazz);
		return getService().updateObject(object, user);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json;charset=UTF-8")
	@Permission("delete")
	@ResponseJsonWithFilter(type = FilterWithNoneFiltered.class)
	public JsonResult deleteObject(HttpServletRequest request, @PathVariable(name = "id", required = true) String id) {
		HashMap<String, Object> params = new HashMap<String, Object>(16);
		params.put("id", id);
		User user =(User)request.getAttribute("user");
		return getService().deleteAll(params, user);
	}

	@RequestMapping(value = "/by-ids/{ids}", method = RequestMethod.DELETE, produces = "application/json;charset=UTF-8")
	@Permission("delete-by-ids")
	@ResponseJsonWithFilter(type = FilterWithNoneFiltered.class)
	public JsonResult deleteByIds(HttpServletRequest request,
			@PathVariable(name = "ids", required = true) String idsStr) {
		String[] ids = idsStr.split("\\-");
		HashMap<String, Object> params = new HashMap<String, Object>(16);
		params.put("ids", ids);
		User user =(User)request.getAttribute("user");
		return getService().deleteAll(params, user);
	}
}