package com.zhrenjie04.alex.util;

import java.util.Random;

/**
 * 盐值生成类
 *
 * @author 张人杰
 * @date 2017年10月27日 下午4:14:22
 */
public class SaltUtil {

	private static String chars="";
    private static int charsSize=0;
    static{
    	chars="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$%^&*()_+-=/?.,><:;\"'{}[]|\\";
    	charsSize=chars.length();
    }
	public static String generatSalt(int length) {
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
	public static String generatSalt() {
    	return generatSalt(8);
    }
	public static void main(String[] args) {
		System.out.println(SaltUtil.generatSalt(10));
		System.out.println(SaltUtil.generatSalt());
	}
}
