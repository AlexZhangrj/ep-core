package com.zhrenjie04.alex.util;

import java.util.Locale;

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
			return "Date";
		} else if (dbColumnType.startsWith("tinyint")) {
			return "Boolean";
		} else if (dbColumnType.startsWith("date")) {
			return "Date";
		} else if (dbColumnType.startsWith("decimal")) {
			return "BigDecimal";
		} else if (dbColumnType.startsWith("int")) {
			return "Long";
		} else {
			throw new RuntimeException("type is not supported:" + dbColumnType + ":" + columnName);
		}
	}
	/**
	 * 获取数据库字段中已设置的字段长度
	 */
	public static Integer convertMySqlVarcharTypeToJavaMaxLength(String dbColumnType) {
		if (dbColumnType == null || "".equals(dbColumnType)) {
			return -1;
		}
		dbColumnType = dbColumnType.toLowerCase();
		if (dbColumnType.startsWith("char")) {
			return Integer.valueOf(dbColumnType.toLowerCase(Locale.ROOT).replace("char(","").replace(")",""));
		} else if (dbColumnType.startsWith("varchar")) {
			return Integer.valueOf(dbColumnType.toLowerCase(Locale.ROOT).replace("varchar(","").replace(")",""));
		} else {
			return -1;
		}
	}
	/**
	 * 转换数据库命名规则为java命名规则的方法
	 */
	public static String convertPostgreTypeToJavaType(String dbColumnType, String columnName) {
		if (dbColumnType == null || "".equals(dbColumnType)) {
			return null;
		}
		if (columnName.toLowerCase().endsWith("id")) {
			return "String";
		}
		dbColumnType = dbColumnType.toLowerCase();
		if (dbColumnType.startsWith("float4")) {
			return "Float";
		}else if (dbColumnType.startsWith("float")) {
			return "Double";
		} else if (dbColumnType.startsWith("text")) {
			return "String";
		} else if (dbColumnType.startsWith("character")) {
			return "String";
		} else if (dbColumnType.startsWith("character varying")) {
			return "String";
		} else if (dbColumnType.startsWith("datetime")) {
			return "Date";
		} else if (dbColumnType.startsWith("boolean")) {
			return "Boolean";
		} else if (dbColumnType.startsWith("date")) {
			return "Date";
		} else if (dbColumnType.startsWith("decimal")) {
			return "BigDecimal";
		} else if (dbColumnType.startsWith("int4")) {
			return "Integer";
		} else if (dbColumnType.startsWith("int")) {
			return "Long";
		} else {
			throw new RuntimeException("type is not supported:" + dbColumnType + ":" + columnName);
		}
	}
}
