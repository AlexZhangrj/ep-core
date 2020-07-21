package com.zhrenjie04.alex.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zhrenjie04.alex.core.exception.CrisisError;

/**
 * @author 张人杰 Twitter_Snowflake<br>
 * 		   2020-07-21改进：
 * 		   改为两个Long型数据（每个long64位，一共128位，高位long表示时间戳，低位long表示workid和sequence），并转化为base52表示
 */
public class IdGenerator2 {

	private static final Logger logger = LoggerFactory.getLogger(IdGenerator2.class);

	// ==============================Fields===========================================
	/** 开始时间截 (2020-07-21 00:00:00) */
	private static final long START_TIME = 1595260800000L;

	/** 机器id所占的位数 */
	private static final long WORKER_ID_BITS = 20L;//最大机器数量2^20 100万台

	/** 支持的最大机器id，结果是1024 (这个移位算法可以很快的计算出几位二进制数所能表示的最大十进制数) */
	private static final long MAX_WORK_ID = -1L ^ (-1L << WORKER_ID_BITS);

	/** 序列在id中占的位数，即时间戳相同时，同一个worker最多产生多少个不同的id，序列id20位，100万个 */
	private static final long SEQUENCE_BITS = 10L;

	/** 机器ID向左移12位 */
	private static final long WORKER_ID_SHIFT = SEQUENCE_BITS;

	/** 生成序列的掩码，这里为4095 (0b111111111111=0xfff=4095) */
	private static final long SEQUENCE_MASK = -1L ^ (-1L << SEQUENCE_BITS);

	/** 工作机器ID(0~2^30) */
	private static long workerId;

	/** 毫秒内序列(0~2^20) */
	private static long sequence = 0L;

	/** 上次生成ID的时间截 */
	private static long lastTimestamp = -1L;

	/**
	 * 默认构造方法，默认从配置文件id-generator.properties中读取datacenterId和workerId
	 */
	public static void init(long workId) {
		IdGenerator2.workerId = workerId;
		if (workerId > MAX_WORK_ID || workerId < 0) {
			throw new CrisisError(String.format("worker Id can't be greater than %d or less than 0", MAX_WORK_ID));
		}
		logger.info("===========================Attention=================================");
		logger.info("workerId:" + workerId);
		logger.info("=====================================================================");
	}

	// ==============================Methods==========================================
	/**
	 * 获得下一个ID (该方法是线程安全的)
	 * 
	 * @return SnowflakeId
	 */
	public static synchronized String nextIdString() {
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
				timestamp = tilNextMillis(lastTimestamp);
			}
		} else {
			// 时间戳改变，毫秒内序列重置
			sequence = 0L;
		}

		// 上次生成ID的时间截
		lastTimestamp = timestamp;
		// 移位并通过或运算拼到一起组成64位的ID
		return (timestamp - START_TIME) + "" + String.format("%010d", (workerId << WORKER_ID_SHIFT) | sequence);
	}

	/**
	 * 阻塞到下一个毫秒，直到获得新的时间戳
	 * 
	 * @param lastTimestamp 上次生成ID的时间截
	 * @return 当前时间戳
	 */
	protected static long tilNextMillis(long lastTimestamp) {
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

	/** 测试 */
	public static void main(String[] args) {
		for (int i = 0; i < 1000; i++) {
			logger.debug(IdGenerator2.nextIdString()+":::"+Long.MAX_VALUE);
			logger.debug(IdGenerator2.nextIdString()+":::"+Integer.MAX_VALUE);
			logger.debug(IdGenerator2.nextIdString());
			logger.debug(IdGenerator2.nextIdString());
			logger.debug(IdGenerator2.nextIdString());
		}
	}
}