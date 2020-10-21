package com.zhrenjie04.alex.util;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.zhrenjie04.alex.core.exception.CrisisError;

/**
 * @author 张人杰 Twitter_Snowflake<br>
 *         SnowFlake的结构如下(每部分用-分开):<br>
 *         0 - 0000000000 0000000000 0000000000 0000000000 0 - 00000 - 00000 -
 *         000000000000 <br>
 *         1位标识，由于long基本类型在Java中是带符号的，最高位是符号位，正数是0，负数是1，所以id一般是正数，最高位是0<br>
 *         41位时间截(毫秒级)，注意，41位时间截不是存储当前时间的时间截，而是存储时间截的差值（当前时间截 - 开始时间截)
 *         得到的值），这里的的开始时间截，一般是我们的id生成器开始使用的时间，由我们程序来指定的（如下下面程序IdWorker类的startTime属性）。41位的时间截，可以使用69年，年T
 *         = (1L << 41) / (1000L * 60 * 60 * 24 * 365) = 69<br>
 *         10位的数据机器位，可以部署在1024个节点，包括5位datacenterId和5位workerId（后期改为10位workerId）<br>
 *         12位序列，毫秒内的计数，12位的计数顺序号支持每个节点每毫秒(同一机器，同一时间截)产生4096个ID序号<br>
 *         加起来刚好64位，为一个Long型。<br>
 *         SnowFlake的优点是，整体上按照时间自增排序，并且整个分布式系统内不会产生ID碰撞(由数据中心ID和机器ID作区分)，并且效率较高，经测试，SnowFlake每秒能够产生26万ID左右。
 */
public class IdGenerator0 {

	private static final Logger logger = LoggerFactory.getLogger(IdGenerator0.class);

	// ==============================Fields===========================================
	/** 开始时间截 (2017-09-28) */
	private static final long START_TIME = 1506528000000L;

	/** 机器id所占的位数 */
	private static final long WORKER_ID_BITS = 10L;

	/** 支持的最大机器id，结果是1024 (这个移位算法可以很快的计算出几位二进制数所能表示的最大十进制数) */
	private static final long MAX_WORK_ID = -1L ^ (-1L << WORKER_ID_BITS);

	/** 序列在id中占的位数，序列取11位（原本12位），则时间范围可以显示42位，可以记录138年 */
	private static final long SEQUENCE_BITS = 11L;

	/** 机器ID向左移12位 */
	private static final long WORKER_ID_SHIFT = SEQUENCE_BITS;

	/** 时间截向左移22位(5+5+12) */
	private static final long TIMESTAMP_LEFT_SHIFT = WORKER_ID_BITS + SEQUENCE_BITS;

	/** 生成序列的掩码，这里为4095 (0b111111111111=0xfff=4095) */
	private static final long SEQUENCE_MASK = -1L ^ (-1L << SEQUENCE_BITS);

	/** 工作机器ID(0~1024) */
	private static long workerId;

	/** 毫秒内序列(0~4095) */
	private static long sequence = 0L;

	/** 上次生成ID的时间截 */
	private static long lastTimestamp = -1L;

	/**
	 * 默认构造方法，默认从配置文件id-generator.properties中读取datacenterId和workerId
	 */
	public static void init(long workId) {
		IdGenerator0.workerId = workerId;
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
	public static synchronized long nextId() {
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
		}
		// 时间戳改变，毫秒内序列重置
		else {
			sequence = 0L;
		}

		// 上次生成ID的时间截
		lastTimestamp = timestamp;

		// 移位并通过或运算拼到一起组成64位的ID
		return ((timestamp - START_TIME) << TIMESTAMP_LEFT_SHIFT) | (workerId << WORKER_ID_SHIFT) | sequence;
	}

	public static String nextIdString() {
		return "" + nextId();
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
			long id = IdGenerator0.nextId();
			logger.debug(Long.toBinaryString(id));
			logger.debug("main", id);
			logger.debug(IdGenerator0.nextIdString());
		}
	}
}