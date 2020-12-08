package com.zhrenjie04.alex.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XssFilter {
	
	private static String regExpScript="<script[^>]*?>[\\s\\S]*?<\\/script>"; //定义script的正则表达式 
	private static String regExpIframe="<iframe[^>]*?>[\\s\\S]*?<\\/iframe>"; //定义iframe标签的正则表达式 
	private static String regExpFrame="<frame[^>]*?>[\\s\\S]*?<\\/frame>"; //定义frame标签的正则表达式 
     
	private static Pattern pScript=Pattern.compile(regExpScript,Pattern.CASE_INSENSITIVE); 
	private static Pattern pIframe=Pattern.compile(regExpIframe,Pattern.CASE_INSENSITIVE); 
	private static Pattern pFrame=Pattern.compile(regExpFrame,Pattern.CASE_INSENSITIVE); 

	public static String filter(String s) {
		if(s==null) {
			return null;
		}
	    Matcher mScript=pScript.matcher(s); 
	    s=mScript.replaceAll(""); //过滤  
	    Matcher mIframe=pIframe.matcher(s); 
	    s=mIframe.replaceAll(""); //过滤iframe标签 
	    Matcher mFrame=pFrame.matcher(s); 
	    s=mFrame.replaceAll(""); //过滤frame标签 
		return s;
	}
	public static void main(String[] args) {
		System.out.println(XssFilter.filter("<style aaaa>aaa</style><script bbb>bbb</script><iframe ccc>ccc</iframe>dddd"));
	}
}
