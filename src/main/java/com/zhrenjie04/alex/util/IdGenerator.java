package com.zhrenjie04.alex.util;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zhrenjie04.alex.core.exception.CrisisError;

/**
 * @author 张人杰 Twitter_Snowflake<br>
 * 		   2020-07-21改进：
 * 		   改为一个Long型整数+一个Integer型整数（long64位，integer32位，一共96位，高位long表示时间戳，低位int表示workid和sequence），并转化为base50表示
 *
 * compareBase47Id(left,right)使用前提：
 * 1、id通过IdGenerator2产生；
 * 2、具有时间优先机器码后置特点；
 * 3、具有非1自增特性，具有字符串长短不确定特点
 * 用途：用于数据库服务器扩容时使用（旧id分库原则方法中hash规则不变，新id按新的服务器数量进行hash）
 * 使用说明：只比较头部时间
 */
public class IdGenerator {

	private static final Logger logger = LoggerFactory.getLogger(IdGenerator.class);

	// ==============================Fields===========================================
	/** 开始时间截 (2020-07-21 00:00:00) */
	private static final long START_TIME = 1595260800000L;

	/** 机器id所占的位数 */
	private static final int WORKER_ID_BITS = 20;//最大机器数量2^20 100万台

	/** 支持的最大机器id，结果是1024 (这个移位算法可以很快的计算出几位二进制数所能表示的最大十进制数) */
	private static final int MAX_WORK_ID = -1 ^ (-1 << WORKER_ID_BITS);

	/** 序列在id中占的位数，即时间戳相同时，同一个worker最多产生多少个不同的id，序列id10位，1024个，毫秒内一个微服务最多产生1024个id */
	private static final int SEQUENCE_BITS = 10;

	/** 机器ID向左移12位 */
	private static final int WORKER_ID_SHIFT = SEQUENCE_BITS;

	/** 生成序列的掩码，这里为4095 (0b111111111111=0xfff=4095) */
	private static final int SEQUENCE_MASK = -1 ^ (-1 << SEQUENCE_BITS);

	/** 工作机器ID(0~2^20) */
	private static int workerId = 2^19;

	/** 毫秒内序列(0~2^10) */
	private static int sequence = 0;

	/** 上次生成ID的时间截 */
	private static long lastTimestamp = -1L;
	
	private static String base48Chars="abcdefgh5jkmn6pqrstuvwxy7BCDEFGHJK8MNPQRSTUVWXY9";

	private static HashMap<String,Integer> charToIntegerMap=new HashMap<String,Integer>();
	static {
		for(int i=0;i<base48Chars.length();i++) {
			charToIntegerMap.put(""+base48Chars.charAt(i),i);
		}
	}
	/**
	 * 默认构造方法，默认从配置文件id-generator.properties中读取datacenterId和workerId
	 */
	public static void init(int workerId) {
		IdGenerator.workerId = workerId;
		if (workerId > MAX_WORK_ID || workerId < 0) {
			throw new CrisisError(String.format("worker Id can't be greator than %d or less than 0", MAX_WORK_ID));
		}
		logger.info("===========================Attention=================================");
		logger.info("workerId:" + workerId);
		logger.info("=====================================================================");
	}

	// ==============================Methods==========================================
	/**
	 * 获得下一个数字Id (该方法是线程安全的)，最大长度29字节
	 * 建议订单编号采用数字Id
	 * 数字位数已对齐
	 * @return 雪花算法id
	 */
	public static synchronized String nextIdNumberString() {
		long timestamp = timeGen();

		// 如果当前时间小于上一次ID生成的时间戳，说明系统时钟回退过这个时候应当抛出异常
		if (timestamp < lastTimestamp) {
			throw new CrisisError(String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds",
					lastTimestamp - timestamp));
		}

		// 如果是同一时间生成的，则进行毫秒内序列
		if (lastTimestamp == timestamp) {
			sequence = (sequence + 1) & SEQUENCE_MASK;
			// 毫秒内序列溢出
			if (sequence == 0) {
				// 阻塞到下一个毫秒,获得新的时间戳
				timestamp = tillNextMillis(lastTimestamp);
			}
		} else {
			// 时间戳改变，毫秒内序列重置
			sequence = 0;
		}

		// 上次生成ID的时间截
		lastTimestamp = timestamp;
		// 移位并通过或运算拼到一起组成64位的ID
		return  String.format("%019d", (timestamp - START_TIME)) + String.format("%010d", (workerId << WORKER_ID_SHIFT) | sequence);
	}
	/**
	 * 获得下一个字符Id (该方法是线程安全的)，最长18个字符
	 * 建议博客文章Id、聊天记录采用字符Id
	 * @return 雪花算法id
	 */
	public static synchronized String nextIdBase48String() {
		long timestamp = timeGen();

		// 如果当前时间小于上一次ID生成的时间戳，说明系统时钟回退过这个时候应当抛出异常
		if (timestamp < lastTimestamp) {
			throw new CrisisError(String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds",
					lastTimestamp - timestamp));
		}

		// 如果是同一时间生成的，则进行毫秒内序列
		if (lastTimestamp == timestamp) {
			sequence = (sequence + 1) & SEQUENCE_MASK;
			// 毫秒内序列溢出
			if (sequence == 0) {
				// 阻塞到下一个毫秒,获得新的时间戳
				timestamp = tillNextMillis(lastTimestamp);
			}
		} else {
			// 时间戳改变，毫秒内序列重置
			sequence = 0;
		}

		// 上次生成ID的时间截
		lastTimestamp = timestamp;
		// 移位并通过或运算拼到一起组成64位的ID
		Long firstLong=(timestamp - START_TIME);
//		System.out.println(firstLong);
		StringBuffer firstString=new StringBuffer("");
		while(firstLong>0) {
			int c=(int)(firstLong%base48Chars.length());
			firstString.insert(0, ""+base48Chars.charAt(c));
			firstLong=firstLong/base48Chars.length();
		}
		Integer lastInteger=(workerId << WORKER_ID_SHIFT) | sequence;
//		System.out.println(lastInteger);
		StringBuffer lastString=new StringBuffer("");
		while(lastInteger>0) {
			int c=(int)(lastInteger%base48Chars.length());
			lastString.insert(0, ""+base48Chars.charAt(c));
			lastInteger=lastInteger/base48Chars.length();
		}
		return firstString.append("A").append(lastString).toString();
	}
	/**
	 * 获得下一个Id的byte数组表示
	 * 建议博客文章Id、聊天记录采用字符Id
	 * @return 雪花算法id
	 */
	public static synchronized byte[] nextIdBytes() {
		long timestamp = timeGen();

		// 如果当前时间小于上一次ID生成的时间戳，说明系统时钟回退过这个时候应当抛出异常
		if (timestamp < lastTimestamp) {
			throw new CrisisError(String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds",
					lastTimestamp - timestamp));
		}

		// 如果是同一时间生成的，则进行毫秒内序列
		if (lastTimestamp == timestamp) {
			sequence = (sequence + 1) & SEQUENCE_MASK;
			// 毫秒内序列溢出
			if (sequence == 0) {
				// 阻塞到下一个毫秒,获得新的时间戳
				timestamp = tillNextMillis(lastTimestamp);
			}
		} else {
			// 时间戳改变，毫秒内序列重置
			sequence = 0;
		}

		// 上次生成ID的时间截
		lastTimestamp = timestamp;
		// 移位并通过或运算拼到一起组成64位的ID
		Long firstLong=(timestamp - START_TIME);
		Integer lastInteger=(workerId << WORKER_ID_SHIFT) | sequence;
		byte[] bytes=new byte[12];
		bytes[0]=(byte)(lastInteger&0xff);
		lastInteger=lastInteger>>8;
		bytes[1]=(byte)(lastInteger&0xff);
		lastInteger=lastInteger>>8;
		bytes[2]=(byte)(lastInteger&0xff);
		lastInteger=lastInteger>>8;
		bytes[3]=(byte)(lastInteger&0xff);
		
		bytes[4]=(byte)(firstLong&0xff);
		firstLong=firstLong>>8;
		bytes[5]=(byte)(firstLong&0xff);
		firstLong=firstLong>>8;
		bytes[6]=(byte)(firstLong&0xff);
		firstLong=firstLong>>8;
		bytes[7]=(byte)(firstLong&0xff);
		firstLong=firstLong>>8;
		bytes[8]=(byte)(firstLong&0xff);
		firstLong=firstLong>>8;
		bytes[9]=(byte)(firstLong&0xff);
		firstLong=firstLong>>8;
		bytes[10]=(byte)(firstLong&0xff);
		firstLong=firstLong>>8;
		bytes[11]=(byte)(firstLong&0xff);
		
		return bytes;
	}
	/**
	 * 阻塞到下一个毫秒，直到获得新的时间戳
	 * 
	 * @param lastTimestamp 上次生成ID的时间截
	 * @return 当前时间戳
	 */
	protected static long tillNextMillis(long lastTimestamp) {
		long timestamp = timeGen();
		while (timestamp <= lastTimestamp) {
			timestamp = timeGen();
		}
		return timestamp;
	}

	/**
	 * 返回以毫秒为单位的当前时间
	 * 
	 * @return 当前时间(毫秒)
	 */
	protected static long timeGen() {
		return System.currentTimeMillis();
	}

	public static Long convertToLong(String base48String) {
		Long data=0L;
		int i=0;
		while(i<base48String.length()) {
			char c = base48String.charAt(i);
			data = data * base48Chars.length() + charToIntegerMap.get(""+c);
			++i;
		}
//		System.out.println(data);
		return data;
	}
	
	public static IdComparorResult compareBase47Id(String left,String right) {
		String leftTimeString=left.substring(0, left.indexOf("A"));
		String rightTimeString=right.substring(0, right.indexOf("A"));
		Long leftTime=convertToLong(leftTimeString);
		Long rightTime=convertToLong(rightTimeString);
		if(leftTime < rightTime) {
			return IdComparorResult.Less;
		}else if(leftTime > rightTime) {
			return IdComparorResult.Great;
		}else {
			return IdComparorResult.Equals;//只比较头部时间
		}
	}
	
	/** 测试 */
	public static void main(String[] args) {
		for (int i = 0; i < 1000; i++) {
			logger.debug(IdGenerator.nextIdNumberString()+":::"+Long.MAX_VALUE);
			logger.debug(IdGenerator.nextIdNumberString()+":::"+Integer.MAX_VALUE);
			logger.debug(IdGenerator.nextIdNumberString());
			logger.debug(IdGenerator.nextIdNumberString());
			logger.debug(IdGenerator.nextIdNumberString());
			logger.debug(IdGenerator.nextIdBase48String());
			logger.debug(IdGenerator.nextIdBase48String());
			logger.debug(IdGenerator.nextIdBase48String());
			logger.debug(IdGenerator.nextIdBase48String());
			logger.debug(IdGenerator.nextIdBase48String());
		}
		System.out.println(IdGenerator.compareBase47Id("HxNoNPAhEc", "HxNoNPAhEd"));
	}
}

enum IdComparorResult{
	Equals("Equals"),Less("Less"),Great("Great");
	String value;
	private IdComparorResult(String value) {
		this.value=value;
	}
	public String toString() {
		return this.value;
	}
}
