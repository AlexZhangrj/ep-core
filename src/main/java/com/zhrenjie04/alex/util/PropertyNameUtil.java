package com.zhrenjie04.alex.util;

/**
 * 从数据库字段名转换为Java属性名的转化工具类
 * 
 * @author 张人杰
 */
public class PropertyNameUtil {

	/**
	 * 转换数据库命名规则为java命名规则的方法
	 */
	public static String convertToJavaRuleName(String key) {
		if (key == null || "".equals(key)) {
			return "";
		}
		StringBuffer sb = new StringBuffer("");
		int i = 0;
		while (i < key.length() && key.charAt(i) == '_') {
			++i;
		}
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
		System.out.println(convertToJavaRuleName("cs_agent_id"));
		System.out.println(convertToJavaRuleName("_role_privilege_id"));
		System.out.println(convertToJavaRuleName("__role_privilege_id"));
		System.out.println(convertToJavaRuleName("___role_privilege_id"));
	}

}
