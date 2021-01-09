package com.zhrenjie04.alex.util;

import java.util.Random;

/**
 * 盐值生成类
 *
 * @author 张人杰
 * @date 2017年10月27日 下午4:14:22
 */
public class VerifyCodeUtil {

	private static String chars="";
	private static int charsSize=0;
	private static String numberChars="";
	private static int numberCharsSize=0;
    static{
    	chars="abcdefghijkmnpqrstuvwxyzABCDEFGHJKLMNOPQRSTUVWXYZ23456789";
    	charsSize=chars.length();
		numberChars="0123456789";
		numberCharsSize=numberChars.length();
    }
	public static String generatCharCode(int length) {
    	StringBuffer sb=new StringBuffer("");
		if(length<=0){
    		return "";
    	}
		for(int i=0;i<length;i++){
    		int p=new Random().nextInt(charsSize);
    		sb.append(chars.charAt(p));
    	}
		return sb.toString();
    }
	public static String generatNumberCode(int length) {
		StringBuffer sb=new StringBuffer("");
		if(length<=0){
			return "";
		}
		for(int i=0;i<length;i++){
			int p=new Random().nextInt(numberCharsSize);
			sb.append(numberChars.charAt(p));
		}
		return sb.toString();
	}
	public static String generatCharCode() {
    	return generatCharCode(6);
    }
	public static void main(String[] args) {
		System.out.println(VerifyCodeUtil.generatCharCode(10));
		System.out.println(VerifyCodeUtil.generatCharCode());
	}
}
