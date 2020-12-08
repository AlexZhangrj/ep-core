package com.zhrenjie04.alex.util;

/**
 * 从数据库字段名转换为Java属性名的转化工具类
 * 
 * @author 张人杰
 */
public class PropertyDefaultValueUtil {

	/**
	 * 转换数据库命名规则为java命名规则的方法
	 */
	public static String converToJavaDefaultValue(String defaultValue, String javaType) {
		if (defaultValue == null || "".equals(defaultValue)) {
			return null;
		}
		if ("CURRENT_TIMESTAMP".equals(defaultValue)) {
			return null;
		}
		if ("Long".equals(javaType)) {
			return defaultValue;
		} else if ("Double".equals(javaType)) {
			return defaultValue;
		} else if ("String".equals(javaType)) {
			return "\"" + defaultValue + "\"";
		} else if ("Boolean".equals(javaType)) {
			return defaultValue.endsWith("0") ? "false" : "true";
		} else if ("BigDecimal".equals(javaType)) {
			return "new BigDecimal(\"" + defaultValue + "\")";
		} else {
			throw new RuntimeException("default is not supported:" + javaType + ":" + defaultValue);
		}
	}
}
