package com.zhrenjie04.alex.core;

import java.util.LinkedHashMap;

/**
 * @author 张人杰
 */
public class JsonResult extends LinkedHashMap<String,Object>{
	private static final long serialVersionUID = 2003291836100850303L;
	public static final Integer CODE_SUCCESS=200;
	public static final Integer CODE_INTERNAL_SERVER_ERROR=500;
	public static final Integer CODE_UNAUTHORIZED=401;
	public static final Integer CODE_REQUEST_INPUT_ERROR=406;
	public static final Integer CODE_PREREQUISITE_NOT_SATISFIED=412;
	public JsonResult(Integer code, String message) {
		super.put("status", code);
		super.put("message", message);
	}
	public JsonResult() {
		super.put("status", CODE_SUCCESS);
		super.put("message", "");
	}
	public Integer getStatus(){
		if(super.get("status")==null) {
			return null;
		}
		return Integer.valueOf(super.get("status").toString());
//		return (Integer)super.get("status");
	}
	public void setStatus(Integer code){
		super.put("status",code);
	}
	public String getMessage(){
		return (String)super.get("message");
	}
	public void setMessage(String message){
		super.put("message", message);
	}
	public static JsonResult success(){
		return new JsonResult();
	}
	public static JsonResult success(String message){
		return new JsonResult(CODE_SUCCESS,message);
	}
	public static JsonResult failure(Integer code,String message) {
		return new JsonResult(code,message);
	}
}
