package com.zhrenjie04.alex.util;

/**
 * 从数据库字段名转换为Java属性名的转化工具类
 * 
 * @author 张人杰
 */
public class DbDefaultValueUtil {

	/**
	 * 转换数据库命名规则为java命名规则的方法
	 */
	public static String converToDbDefaultValue(String defaultValue, String dbColumnType) {
		if (defaultValue == null || "".equals(defaultValue)) {
			return null;
		}
		if ("CURRENT_TIMESTAMP".equals(defaultValue)) {
			return "CURRENT_TIMESTAMP";
		}
		if (dbColumnType.startsWith("bigint")) {
			return defaultValue;
		} else if (dbColumnType.startsWith("double")) {
			return defaultValue;
		} else if (dbColumnType.startsWith("longtext")) {
			return "'" + defaultValue + "'";
		} else if (dbColumnType.startsWith("text")) {
			return "'" + defaultValue + "'";
		} else if (dbColumnType.startsWith("char")) {
			return "'" + defaultValue + "'";
		} else if (dbColumnType.startsWith("varchar")) {
			return "'" + defaultValue + "'";
		} else if (dbColumnType.startsWith("datetime")) {
			return null;
		} else if (dbColumnType.startsWith("tinyint")) {
			return defaultValue;
		} else if (dbColumnType.startsWith("date")) {
			return null;
		} else if (dbColumnType.startsWith("decimal")) {
			return defaultValue;
		} else if (dbColumnType.startsWith("int")) {
			return defaultValue;
		} else {
			throw new RuntimeException("type is not supported:" + dbColumnType + ":" + defaultValue);
		}
	}
}
