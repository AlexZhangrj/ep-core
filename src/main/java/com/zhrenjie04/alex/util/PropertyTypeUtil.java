package com.zhrenjie04.alex.util;

/**
 * 从数据库字段名转换为Java属性名的转化工具类
 * 
 * @author 张人杰
 */
public class PropertyTypeUtil {

	/**
	 * 转换数据库命名规则为java命名规则的方法
	 */
	public static String convertMySqlTypeToJavaType(String dbColumnType, String columnName) {
		if (dbColumnType == null || "".equals(dbColumnType)) {
			return null;
		}
		if (columnName.toLowerCase().endsWith("id")) {
			return "String";
		}
		dbColumnType = dbColumnType.toLowerCase();
		if (dbColumnType.startsWith("bigint")) {
			return "Long";
		} else if (dbColumnType.startsWith("double")) {
			return "Double";
		} else if (dbColumnType.startsWith("longtext")) {
			return "String";
		} else if (dbColumnType.startsWith("text")) {
			return "String";
		} else if (dbColumnType.startsWith("char")) {
			return "String";
		} else if (dbColumnType.startsWith("varchar")) {
			return "String";
		} else if (dbColumnType.startsWith("datetime")) {
			return "Timestamp";
		} else if (dbColumnType.startsWith("tinyint")) {
			return "Boolean";
		} else if (dbColumnType.startsWith("date")) {
			return "Date";
		} else if (dbColumnType.startsWith("decimal")) {
			return "Decimal";
		} else if (dbColumnType.startsWith("int")) {
			return "Long";
		} else {
			throw new RuntimeException("type is not supported:" + dbColumnType + ":" + columnName);
		}
	}
}
