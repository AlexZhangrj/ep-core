package com.zhrenjie04.alex.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.text.StringEscapeUtils;

public class HtmlCleaner {
	
	private static String regExpScript="<script[^>]*?>[\\s\\S]*?<\\/script>"; //定义script的正则表达式 
	private static String regExpStyle="<style[^>]*?>[\\s\\S]*?<\\/style>"; //定义style的正则表达式 
	private static String regExpIframe="<iframe[^>]*?>[\\s\\S]*?<\\/iframe>"; //定义iframe标签的正则表达式 
	private static String regExpFrame="<frame[^>]*?>[\\s\\S]*?<\\/frame>"; //定义iframe标签的正则表达式 
     
	private static Pattern pScript=Pattern.compile(regExpScript,Pattern.CASE_INSENSITIVE); 
	private static Pattern pStyle=Pattern.compile(regExpStyle,Pattern.CASE_INSENSITIVE); 
	private static Pattern pIframe=Pattern.compile(regExpIframe,Pattern.CASE_INSENSITIVE); 
	private static Pattern pFrame=Pattern.compile(regExpFrame,Pattern.CASE_INSENSITIVE); 

	public static String getPureText(String s) {
		if(s==null) {
			return null;
		}
		s = s.replaceAll("\\<.*?\\>","");
		return StringEscapeUtils.escapeHtml4(s);
	}
	
	public static String clean(String s) {
		if(s==null) {
			return null;
		}
	    Matcher mScript=pScript.matcher(s); 
	    s=mScript.replaceAll(""); //过滤  
	    Matcher mStyle=pStyle.matcher(s); 
	    s=mStyle.replaceAll(""); //过滤style标签 
	    Matcher mIframe=pIframe.matcher(s); 
	    s=mIframe.replaceAll(""); //过滤iframe标签 
	    Matcher mFrame=pFrame.matcher(s); 
	    s=mFrame.replaceAll(""); //过滤frame标签 
		return s;
	}
	public static void main(String[] args) {
		System.out.println(HtmlCleaner.clean("<style aaaa>aaa</style><script bbb>bbb</script><iframe ccc>ccc</iframe>dddd"));
		System.out.println(HtmlCleaner.getPureText("<div>aaa</div><br><br/><style aaaa>aaa</style><script bbb>bbb</script><iframe ccc>ccc</iframe>dddd"));
	}
}
