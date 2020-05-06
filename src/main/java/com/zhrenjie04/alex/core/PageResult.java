package com.zhrenjie04.alex.core;

import java.util.Collections;
import java.util.List;

public class PageResult extends JsonResult{
	
	public PageResult(){
		super();
		put("total", 0);
		put("list", Collections.EMPTY_LIST);
		put("pageNo", 1);
		put("pageSize", 30);
		put("orderBy", null);
		put("orderType", null);
		put("totalPages", 0);
	}
 	public PageResult(Integer state,String message,Long total,Object list, Long pageNo,Long pageSize,String orderBy,String orderType){
		super(state,message);
		put("total", total);
		put("list", list);
		put("pageNo", pageNo);
		put("pageSize", pageSize);
		put("orderBy", orderBy);
		put("orderType", orderType);
		if (pageSize != null) {
			put("totalPages", (total / pageSize) + (total % pageSize > 0 ? 1 : 0));
		}
	}
 	public Long getTotal() {
 		return (Long)get("total");
 	}
 	public Object getList() {
 		return get("list");
 	}
 	public Long getPageNo() {
 		return (Long)get("pageNo");
 	}
 	public Long getPageSize() {
 		return (Long)get("pageSize");
 	}
 	public String getOrderBy() {
 		return (String)get("orderBy");
 	}
 	public String getOrderType() {
 		return (String)get("orderType");
 	}
 	public void setTotal(Long total) {
 		put("total",total);
 	}
 	public void setList(Object list) {
 		put("list",list);
 	}
 	public void setPageNo(Long pageNo) {
 		put("pageNo",pageNo);
 	}
 	public void setPageSize(Long pageSize) {
 		put("pageSize",pageSize);
 	}
 	public void setOrderBy(String orderBy) {
 		put("orderBy",orderBy);
 	}
 	public void setOrderType(String orderType) {
 		put("orderType",orderType);
 	}
	public static PageResult success(){
		return new PageResult();
	}
	public static PageResult success(String message,Long total,Object list, Long pageNo,Long pageSize,String orderBy,String orderType){
		return new PageResult(CODE_SUCCESS,message,total,list,pageNo,pageSize,orderBy,orderType);
	}
	public static PageResult failure(Integer code,String message,Long total,Object list, Long pageNo,Long pageSize,String orderBy,String orderType) {
		return new PageResult(code,message,0L,list,pageNo,pageSize,orderBy,orderType);
	}
	public PageResult(Long total,Object list, Long pageNo,Long pageSize,String orderBy,String orderType){
		put("total", total);
		put("list", list);
		put("pageNo", pageNo);
		put("pageSize", pageSize);
		put("orderBy", orderBy);
		put("orderType", orderType);
		if (pageSize != null) {
			put("totalPages", (total / pageSize) + (total % pageSize > 0 ? 1 : 0));
		}
	}
}
