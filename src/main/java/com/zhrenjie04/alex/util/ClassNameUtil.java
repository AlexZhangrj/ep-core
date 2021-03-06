package com.zhrenjie04.alex.util;

/**
 * 从数据库字段名转换为Java属性名的转化工具类
 * 
 * @author 张人杰
 */
public class ClassNameUtil {

	/**
	 * 转换数据库命名规则为java命名规则的方法
	 */
	public static String converToJavaClassName(String key) {
		if (key == null || "".equals(key)) {
			return "";
		}
//		key = key.substring(1);// 跳过t（2020.11.05跳过t的过程在使用方中控制）
		StringBuffer sb = new StringBuffer("");
		int i = 0;
		for (; i < key.length(); i++) {
			if (key.charAt(i) == '_' && i < key.length() - 1) {
				sb.append(toUpperCaseChar(key.charAt(i + 1)));
				++i;
			} else {
				sb.append(key.charAt(i));
			}
		}
		return sb.toString();
	}

	/**
	 * 小写字母转大写字母的方法
	 */
	private static char toUpperCaseChar(char c) {
		if (c >= 'a' && c <= 'z') {
			return (char) (c - 32);
		}
		return c;
	}

	public static void main(String[] args) {
		System.out.println(converToJavaClassName("t_user_project_dept_position"));
	}

}
