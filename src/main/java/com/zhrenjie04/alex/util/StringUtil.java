package com.zhrenjie04.alex.util;

import java.util.List;

/**
 * @author 张人杰
 */
public class StringUtil {
	/**
	 * 判断字符串是否不为空
	 * 
	 * @param s
	 * @return
	 */
	public static boolean isNotEmpty(String s) {
		if (s == null || "".equals(s)) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 判断字符串是否为空
	 * 
	 * @param s
	 * @return
	 */
	public static boolean isEmpty(String s) {
		if (s == null || "".equals(s)) {
			return true;
		} else {
			return false;
		}
	}

	public static String join(List<String> ids) {
		if (ids == null) {
			return "";
		} else {
			StringBuilder sb = new StringBuilder("");
			for (String id : ids) {
				sb.append(id).append(',');
			}
			if (sb.charAt(sb.length() - 1) == ',') {
				sb.deleteCharAt(sb.length() - 1);
			}
			return sb.toString();
		}
	}
	private static final String HEX_DIGITS = "0123456789abcdef";
	public static String toHexString(byte[] bytes){
		 StringBuilder sb = new StringBuilder(bytes.length * 2);
		    for (int i = 0; i < bytes.length; i++) {
		        int b = bytes[i] & 0xFF;
		        sb.append(HEX_DIGITS.charAt(b >>> 4)).append(HEX_DIGITS.charAt(b & 0xF));
		    }
		    return sb.toString();
	}
}
