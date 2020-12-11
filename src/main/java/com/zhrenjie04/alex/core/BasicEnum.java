package com.zhrenjie04.alex.core;

public interface BasicEnum {
	/**
	 * 重写此方法，用于判断前端传过来的String为那个枚举类
	 * @return
	 */
	String getFrontendCode();
	/**
	 * 重写此方法，将枚举类中要写入数据库的属性从这个方法中返回
	 * @return
	 */
	Object getDbCode();
}
