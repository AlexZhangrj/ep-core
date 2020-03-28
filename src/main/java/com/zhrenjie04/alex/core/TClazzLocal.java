package com.zhrenjie04.alex.core;

/**
 * @author 张人杰
 */
public class TClazzLocal {
	private static final ThreadLocal<Class<?>> LOCAL=new ThreadLocal<Class<?>>();
	public static void set(Class<?> tClazz) {
		LOCAL.set(tClazz);
	}
	public static Class<?> get(){
		return LOCAL.get();
	}
	public static void remove(){
		LOCAL.remove();
	}
}
