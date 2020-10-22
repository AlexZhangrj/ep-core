package com.zhrenjie04.alex.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.UUID;

import com.zhrenjie04.alex.core.exception.CrisisError;

public class FileUploadUtil {

	private static String urlPrefix = "http://img.up-task.com:8080/fs";
	private static Long workerId = 0L;
	private static String nfsFolder = "";
	
	public static void init(String urlPrefix,Long workerId,String nfsFolder) {
		FileUploadUtil.urlPrefix = urlPrefix;
		FileUploadUtil.workerId = workerId;
		FileUploadUtil.nfsFolder = nfsFolder;
	}
	
	public static String saveFileAndReturnFullUrl(String ext, InputStream is) {
		return urlPrefix+saveFileAndReturnPath(ext, is);
	}
	public static String saveFileAndReturnPath(String ext, InputStream is){
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd/HH/mm");
		String randomPath = sdf.format(calendar.getTime()) + "/" + workerId;
		String randomFileName = UUID.randomUUID() + ext;
		try {
			File dir = new File(nfsFolder + "/" + randomPath);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			FileOutputStream fos = new FileOutputStream(new File(nfsFolder + "/" + randomPath + "/" + randomFileName));
			// 写文件
			byte[] bytes = new byte[1024];
			while(is.read(bytes,0,1024)>0) {
				fos.write(bytes,0,1024);
			}
			fos.flush();
			fos.close();
			return "/" + randomPath + "/" + randomFileName;
		} catch (Exception e) {
			throw new CrisisError("写文件错误：" + nfsFolder + "/" + randomPath + randomFileName);
		}
	}
}
