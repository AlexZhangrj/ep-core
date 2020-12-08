package com.zhrenjie04.alex.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * @author 张人杰
 */
public class DateTimeUtil {
	/**
	 * yyyy-MM-dd HH:mm:ss
	 * @param dateTime
	 * @return
	 */
	public static String formatDateTime(Date dateTime){
		if(dateTime==null){
			return null;
		}
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(dateTime);
	}
	/**
	 * yyyy-MM-dd HH:mm:ss
	 * @param timestamp
	 * @return
	 */
	public static String formatDateTime(Timestamp timestamp){
		if(timestamp==null){
			return null;
		}
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(timestamp);
	}
	/**
	 * yyyy-MM-dd
	 * @param date
	 * @return
	 */
	public static String formatDate(Date date){
		if(date==null){
			return null;
		}
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(date);
	}
	/**
	 * yyyy-MM-dd
	 * @param timestamp
	 * @return
	 */
	public static String formatDate(Timestamp timestamp){
		if(timestamp==null){
			return null;
		}
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(timestamp);
	}
}
