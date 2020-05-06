package com.zhrenjie04.alex.core;

import java.util.Collections;
import java.util.List;

public class JsonPageResult extends JsonResult{
	
	public JsonPageResult(){
		super();
		put("total", 0);
		put("list", Collections.EMPTY_LIST);
		put("pageNo", 1);
		put("pageSize", 30);
		put("orderBy", null);
		put("orderType", null);
		put("totalPages", 0);
	}
 	public JsonPageResult(Integer state,String message,Long total,List list, Long pageNo,Long pageSize,String orderBy,String orderType){
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
	public static JsonPageResult success(){
		return new JsonPageResult();
	}
	public static JsonPageResult success(String message,Long total,List list, Long pageNo,Long pageSize,String orderBy,String orderType){
		return new JsonPageResult(CODE_SUCCESS,message,total,list,pageNo,pageSize,orderBy,orderType);
	}
	public static JsonPageResult failure(Integer code,String message,Long total,List list, Long pageNo,Long pageSize,String orderBy,String orderType) {
		return new JsonPageResult(code,message,0L,list,pageNo,pageSize,orderBy,orderType);
	}
	public JsonPageResult(Long total,List list, Long pageNo,Long pageSize,String orderBy,String orderType){
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
