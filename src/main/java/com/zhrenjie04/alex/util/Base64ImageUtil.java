package com.zhrenjie04.alex.util;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Calendar;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import com.zhrenjie04.alex.core.exception.CrisisError;

@Component
public class Base64ImageUtil {

	private static String urlPrefix = "http://img.up-task.com:8080/fs";
	private static Long workerId = 0L;
	private static String nfsFolder = "/Users/apple/";

	public static void init(String urlPrefix,Long workerId,String nfsFolder) {
		Base64ImageUtil.urlPrefix = urlPrefix;
		Base64ImageUtil.workerId = workerId;
		Base64ImageUtil.nfsFolder = nfsFolder;
	}

	private static Pattern patternImg = Pattern.compile("<img(\\s+)src=('|\")data:image/(\\S+);base64,(\\S+)('|\")",
			Pattern.CASE_INSENSITIVE);
//	private static Pattern patternJPEG = Pattern.compile("<img(\\s+)src=('|\")(data:image/jpeg;base64,)(\\S+)('|\")", Pattern.CASE_INSENSITIVE);
//	private static Pattern patternPNG = Pattern.compile("<img(\\s+)src=('|\")(data:image/png;base64,)(\\S+)('|\")", Pattern.CASE_INSENSITIVE);
//	private static Pattern patternGIF = Pattern.compile("<img(\\s+)src=('|\")(data:image/gif;base64,)(\\S+)('|\")", Pattern.CASE_INSENSITIVE);
//	private static Pattern patternICON = Pattern.compile("<img(\\s+)src=('|\")(data:image/x-icon;base64,)(\\S+)('|\")", Pattern.CASE_INSENSITIVE);
	private static Pattern patternIlleager = Pattern.compile("<img(\\s+)src=('|\")[^/|h](ttp)?(\\S+)('|\")",
			Pattern.CASE_INSENSITIVE);
	private static Pattern patternLeager1 = Pattern.compile("<img(\\s+)src=('|\")/(\\S+)('|\")",
			Pattern.CASE_INSENSITIVE);
	private static Pattern patternLeager2 = Pattern.compile("<img(\\s+)src=('|\")http(\\S+)('|\")",
			Pattern.CASE_INSENSITIVE);

	public static String saveImagesInContent(String content) {
		if (content == null || "".equals(content)) {
			return content;
		}
		Matcher matcher = patternImg.matcher(content);
		StringBuffer buf = new StringBuffer("");
		int start = 0;
		while (matcher.find()) {
			buf.append(content.substring(start, matcher.start()));
			start = matcher.end();
			Calendar calendar = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd/HH/mm");
			String randomPath = sdf.format(calendar.getTime()) + "/" + workerId;
			String randomFileName = "";
			switch (matcher.group(3)) {
			case "jpeg":
				randomFileName = UUID.randomUUID() + ".jpg";
				break;
			case "png":
				randomFileName = UUID.randomUUID() + ".png";
				break;
			case "gif":
				randomFileName = UUID.randomUUID() + ".gif";
				break;
			case "x-icon":
				randomFileName = UUID.randomUUID() + ".icon";
				break;
			}
			// 写文件
			byte[] bytes = Base64.getDecoder().decode(matcher.group(4));
			try {
				File dir = new File(nfsFolder + "/" + randomPath);
				if (!dir.exists()) {
					dir.mkdirs();
				}
				FileOutputStream fos = new FileOutputStream(
						new File(nfsFolder + "/" + randomPath + "/" + randomFileName));
				fos.write(bytes);
				fos.flush();
				fos.close();
			} catch (Exception e) {
				throw new CrisisError("写文件错误：" + nfsFolder + "/" + randomPath + randomFileName);
			}
			buf.append("<img src='" + urlPrefix + "/" + randomPath + "/" + randomFileName + "'");
		}
		if (start < content.length()) {
			buf.append(content.substring(start));
		}
		content = buf.toString();
//		Matcher matcher = patternJPEG.matcher(content);
//		StringBuffer buf=new StringBuffer("");
//		int start=0;
//		while (matcher.find()) {
//			System.out.println("jpeg:"+matcher.group(4));
//			buf.append(content.substring(start, matcher.start()));
//			start=matcher.end();
//			Calendar calendar=Calendar.getInstance();
//			SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd/HH/mm");
//			String randomPath=sdf.format(calendar.getTime())+"/"+workerId;
//			String randomFileName = UUID.randomUUID()+".jpg";
//			// 写文件
//			byte[] bytes = Base64.getDecoder().decode(matcher.group(4));
//			try {
//				File dir=new File(nfsFolder+"/"+randomPath);
//				if(!dir.exists()) {
//					dir.mkdirs();
//				}
//				FileOutputStream fos=new FileOutputStream(new File(nfsFolder+"/"+randomPath+"/"+randomFileName));
//				fos.write(bytes);
//				fos.flush();
//				fos.close();
//			} catch (Exception e) {
//				throw new CrisisError("写文件错误："+nfsFolder+"/"+randomPath+randomFileName);
//			}
//			buf.append("<img src='"+urlPrefix+"/"+randomPath+"/"+randomFileName+"'");
//		}
//		if(start<content.length()) {
//			buf.append(content.substring(start));
//		}
//		content=buf.toString();
//		matcher = patternPNG.matcher(content);
//		buf=new StringBuffer("");
//		start=0;
//		while (matcher.find()) {
//			System.out.println("png:"+matcher.group(4));
//			buf.append(content.substring(start, matcher.start()));
//			start=matcher.end();
//			Calendar calendar=Calendar.getInstance();
//			SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd/HH/mm");
//			String randomPath=sdf.format(calendar.getTime())+"/"+workerId;
//			String randomFileName = UUID.randomUUID()+".png";
//			// 写文件
//			byte[] bytes = Base64.getDecoder().decode(matcher.group(4));
//			try {
//				File dir=new File(nfsFolder+"/"+randomPath);
//				if(!dir.exists()) {
//					dir.mkdirs();
//				}
//				FileOutputStream fos=new FileOutputStream(new File(nfsFolder+"/"+randomPath+"/"+randomFileName));
//				fos.write(bytes);
//				fos.flush();
//				fos.close();
//			} catch (Exception e) {
//				throw new CrisisError("写文件错误："+nfsFolder+"/"+randomPath+randomFileName);
//			}
//			buf.append("<img src='"+urlPrefix+"/"+randomPath+"/"+randomFileName+"'");
//		}
//		if(start<content.length()) {
//			buf.append(content.substring(start));
//		}
//		content=buf.toString();
//		matcher = patternGIF.matcher(content);
//		buf=new StringBuffer("");
//		start=0;
//		while (matcher.find()) {
//			System.out.println("gif:"+matcher.group(4));
//			buf.append(content.substring(start, matcher.start()));
//			start=matcher.end();
//			Calendar calendar=Calendar.getInstance();
//			SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd/HH/mm");
//			String randomPath=sdf.format(calendar.getTime())+"/"+workerId;
//			String randomFileName = UUID.randomUUID()+".gif";
//			// 写文件
//			byte[] bytes = Base64.getDecoder().decode(matcher.group(4));
//			try {
//				File dir=new File(nfsFolder+"/"+randomPath);
//				if(!dir.exists()) {
//					dir.mkdirs();
//				}
//				FileOutputStream fos=new FileOutputStream(new File(nfsFolder+"/"+randomPath+"/"+randomFileName));
//				fos.write(bytes);
//				fos.flush();
//				fos.close();
//			} catch (Exception e) {
//				throw new CrisisError("写文件错误："+nfsFolder+"/"+randomPath+randomFileName);
//			}
//			buf.append("<img src='"+urlPrefix+"/"+randomPath+"/"+randomFileName+"'");
//		}
//		if(start<content.length()) {
//			buf.append(content.substring(start));
//		}
//		content=buf.toString();
//		matcher = patternICON.matcher(content);
//		buf=new StringBuffer("");
//		start=0;
//		while (matcher.find()) {
//			System.out.println("icon:"+matcher.group(4));
//			buf.append(content.substring(start, matcher.start()));
//			start=matcher.end();
//			Calendar calendar=Calendar.getInstance();
//			SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd/HH/mm");
//			String randomPath=sdf.format(calendar.getTime())+"/"+workerId;
//			String randomFileName = UUID.randomUUID()+".icon";
//			// 写文件
//			byte[] bytes = Base64.getDecoder().decode(matcher.group(4));
//			try {
//				File dir=new File(nfsFolder+"/"+randomPath);
//				if(!dir.exists()) {
//					dir.mkdirs();
//				}
//				FileOutputStream fos=new FileOutputStream(new File(nfsFolder+"/"+randomPath+"/"+randomFileName));
//				fos.write(bytes);
//				fos.flush();
//				fos.close();
//			} catch (Exception e) {
//				throw new CrisisError("写文件错误："+nfsFolder+"/"+randomPath+randomFileName);
//			}
//			buf.append("<img src='"+urlPrefix+"/"+randomPath+"/"+randomFileName+"'");
//		}
//		if(start<content.length()) {
//			buf.append(content.substring(start));
//		}
//		content=buf.toString();
		matcher = patternIlleager.matcher(content);
		buf = new StringBuffer("");
		start = 0;
		while (matcher.find()) {
			buf.append(content.substring(start, matcher.start()));
			buf.append("<img src='" + urlPrefix + "/error.jpg'");
			start = matcher.end();
		}
		if (start < content.length()) {
			buf.append(content.substring(start));
		}
		content = buf.toString();
		matcher = patternLeager1.matcher(content);
		buf = new StringBuffer("");
		start = 0;
		while (matcher.find()) {
			buf.append(content.substring(start, matcher.start()));
			if (matcher.end() - matcher.start() > 1024) {
				buf.append("<img src='" + urlPrefix + "/error.jpg'");
			} else {
				buf.append(content.substring(matcher.start(), matcher.end()));
			}
			start = matcher.end();
		}
		if (start < content.length()) {
			buf.append(content.substring(start));
		}
		content = buf.toString();
		matcher = patternLeager2.matcher(content);
		buf = new StringBuffer("");
		start = 0;
		while (matcher.find()) {
			buf.append(content.substring(start, matcher.start()));
			if (matcher.end() - matcher.start() > 1024) {
				buf.append("<img src='" + urlPrefix + "/error.jpg'");
			} else {
				buf.append(content.substring(matcher.start(), matcher.end()));
			}
			start = matcher.end();
		}
		if (start < content.length()) {
			buf.append(content.substring(start));
		}
		content = buf.toString();
		return content;
	}

	public static void main(String[] args) {
		System.out.println(Base64ImageUtil.saveImagesInContent(
				"<img src='data:image/jpeg;base64,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'/>"
						+ "<img src='aabbcc/dd/ee/ff.jpg'/>"
						+ "<img src='http://aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaab'/>"));
	}
}
