package com.zhrenjie04.alex.core;

public interface BasicEnum {
	/**
	 * 重写此方法，将枚举类中要写入数据库的属性从这个方法中返回
	 * @return
	 */
	Object getDbCode();
}
