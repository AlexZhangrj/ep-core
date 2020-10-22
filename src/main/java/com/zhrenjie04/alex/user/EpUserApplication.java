package com.zhrenjie04.alex.user;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import com.zhrenjie04.alex.core.DynamicDataSourceConfig;
import com.zhrenjie04.alex.core.Permission;
import com.zhrenjie04.alex.util.Base64ImageUtil;
import com.zhrenjie04.alex.util.FileUploadUtil;
import com.zhrenjie04.alex.util.HttpClientUtil;
import com.zhrenjie04.alex.util.IdGenerator;
import com.zhrenjie04.alex.util.JwtUtil;
import com.zhrenjie04.alex.util.RedisUtil;
import com.zhrenjie04.alex.util.SessionUtil;
import com.zhrenjie04.alex.util.SpringUtil;

/**
 * @author 张人杰
 */
@ImportResource({ "classpath:captcha.xml" })
@EnableTransactionManagement
@SpringBootApplication
@EnableDiscoveryClient
//@EnableCaching
@EnableWebMvc
@Import({ WebConfig.class, DynamicDataSourceConfig.class ,ContextConfig.class, SwaggerConfig.class })
public class EpUserApplication implements ApplicationContextAware{

	private static final Logger logger = LoggerFactory.getLogger(EpUserApplication.class);

//	@Override
//	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
//		WebMvcConfigurer.super.configureMessageConverters(converters);
//		AlexJsonHttpMessageConverter converter = new AlexJsonHttpMessageConverter();
//		converters.add(converter);
//	}
	public static void main(String[] args) {
		SpringApplication.run(EpUserApplication.class, args);
	}
	
	@Value("${id.worker-id}")
	private Integer workerId = 0;
	@Value("${img.urlPrefix}")
	private String urlPrefix = "";
	@Value("${img.nfs-folder}")
	private String nfsFolder = "";
	@Value("${jwt.jwt-auth-key}")
	private String jwtAutheKey="";

	@Value("${http.validate-inactivity-duration}")
	private Integer validateInactivityDuration;
	@Value("${http.wait-idle-connection-timeout}")
	private Integer waitIdleConnectionTimeoutConfig;
	@Value("${http.connect-timeout}")
	private Integer connectTimeoutConfig;
	@Value("${http.so-timeout-config}")
	private Integer soTimeoutConfig;

	/**
	 * Redis服务器IP
	 */
	@Value("${redis.addr}")
	private String addr = "127.0.0.1";

	/**
	 * Redis的端口号
	 */
	@Value("${redis.port}")
	private int port = 6379;

	/**
	 * 访问密码
	 */
	@Value("${redis.auth}")
	private String auth = "admin";

	/**
	 * 可用连接实例的最大数目，默认值为256；
	 * 如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
	 */
	@Value("${redis.max-total}")
	private int maxTotal = 256;

	/**
	 * 控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值也是8。
	 */
	@Value("${redis.max-idle}")
	private int maxIdle = 200;

	/**
	 * 控制一个pool最少有多少个状态为idle(空闲的)的jedis实例，默认值也是8。
	 */
	@Value("${redis.min-idle}")
	private int minIdle = 256;

	/**
	 * 等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException；
	 */
	@Value("${redis.max-wait-millis}")
	private long maxWaitMillis = 10000;

	@Value("${redis.timeout}")
	private int timeout = 10000;

	@Value("${redis.database}")
	private int database = 0;

	@Value("${redis.global-keeptime}")
	private int globalKeeptimeInput = 7200;
	/**
	 * 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
	 */
	@Value("${redis.test-on-borrow}")
	private boolean testOnBorrow = true;

	@Value("${springCacheExpiration}")
	private long springCacheExpiration;

	@Value("${http.max-total}")
	private Integer httpMaxTotal;
	@Value("${http.max-per-route}")
	private Integer maxPerRoute;
	@Value("${http.so-timeout}")
	private Integer soTimeout;
	@Value("${http.retry-execution-count}")
	private Integer retryExecutionCountConfig;
	
	@Value("${session.session-keep-time-seconds}")
	private Integer sessionKeepTimeSecondsConfig = 20;

	@Autowired
	private RequestMappingHandlerMapping requestMappingHandlerMapping;

	@Bean
	@Order(0)
	public Object redisUtil() {
		RedisUtil.init(maxTotal, maxIdle, minIdle, maxWaitMillis, testOnBorrow, globalKeeptimeInput, addr, port, timeout, auth, database);
		HttpClientUtil.init(maxTotal, maxPerRoute, validateInactivityDuration, waitIdleConnectionTimeoutConfig, connectTimeoutConfig, soTimeoutConfig, retryExecutionCountConfig);
		SessionUtil.init(sessionKeepTimeSecondsConfig);
		FileUploadUtil.init(urlPrefix, workerId, nfsFolder);
		Base64ImageUtil.init(urlPrefix, workerId, nfsFolder);
		IdGenerator.init(workerId);
		JwtUtil.init(jwtAutheKey);
		return new Object();
	}
	
//	@Bean
//	public Object settingBean1(CacheManager cacheManager) {
//		logger.info(">>>>>>>>>>" + cacheManager.getClass().getName());
//		RedisCacheManager manager = (RedisCacheManager) cacheManager;
//		manager.setDefaultExpiration(springCacheExpiration);
//		return new Object();
//	}

	@Bean
	public Object testBean(PlatformTransactionManager platformTransactionManager) {
		System.out.println(">>>>>>>>>>" + platformTransactionManager.getClass().getName());
		return new Object();
	}

	@Bean
	@SuppressWarnings("rawtypes")
	public Object testBean2(RedisTemplate redisTemplate) {
		logger.info(">>>>>>>>>>" + redisTemplate.getClass().getName());
		return new Object();
	}

	@PostConstruct
	private void printControllerUrls() {
		logger.info("####################################################################\n");
		Map<RequestMappingInfo, HandlerMethod> map = requestMappingHandlerMapping.getHandlerMethods();
		for (RequestMappingInfo info : map.keySet()) {
			Permission permission = map.get(info).getBeanType().getAnnotation(Permission.class);
			Permission methodPermission = map.get(info).getMethodAnnotation(Permission.class);
			logger.info((permission == null ? null : permission.value()) + "."
					+ (methodPermission == null ? null : methodPermission.value()) + ","
					+ info.getPatternsCondition().toString() + "," + info.getMethodsCondition() + ","
					+ map.get(info).getBean().toString() + ",["
					+ (permission == null ? "controller_no_permission" : "controller_with_permission") + ":"
					+ (permission == null ? null : permission.value()) + "]" + ",["
					+ (methodPermission == null ? "method_no_permission" : "method_with_permission") + ":"
					+ (methodPermission == null ? null : methodPermission.value()) + "]\n");
		}
		logger.info("#####################################################################\n");
	}
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		SpringUtil.init(applicationContext);
	}
}
