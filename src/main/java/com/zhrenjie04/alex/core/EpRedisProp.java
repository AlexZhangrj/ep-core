package com.zhrenjie04.alex.core;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "redis")
public class EpRedisProp {
	/** Redis服务器IP */
	private String addr="redis";
	/** Redis服务器端口 */
	private Integer port=6379;
	/** Redis服务器密码 */
	private String auth="";
	/** 使用的db */
	private Integer database=0;
	/** 可用连接实例的最大数目，默认值为256；如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。 */
	private Integer maxTotal=20;
	/** 控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值也是8。 */
	private Integer maxIdle=20;
	/** 控制一个pool最少有多少个状态为idle(空闲的)的jedis实例，默认值也是8。 */
	private Integer minIdle=20;
	/** 等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException； */
	private Long maxWaitMillis=10000L;
	/** 连接超时时间毫秒数 */
	private Integer timeoutInMillis=10000;
	/** 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的； */
	private Boolean testOnBorrow=true;
	/** 默认TTL */
	private Integer globalKeeptime=7200;
}
