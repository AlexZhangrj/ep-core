package com.zhrenjie04.alex.user;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.elasticsearch.ElasticsearchRestClientProperties;
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
import com.zhrenjie04.alex.core.EpAuthProp;
import com.zhrenjie04.alex.core.EpHttpClientProp;
import com.zhrenjie04.alex.core.EpIdGeneratorProp;
import com.zhrenjie04.alex.core.EpImgProp;
import com.zhrenjie04.alex.core.EpJwtProp;
import com.zhrenjie04.alex.core.EpMultiDataSourceProp;
import com.zhrenjie04.alex.core.EpRedisProp;
import com.zhrenjie04.alex.core.EpSessionUtilProp;
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
 * Swagger-UI 3.0 访问地址：http://localhost:9920/swagger-ui/index.html
 */
@ImportResource({ "classpath:captcha.xml" })
@EnableTransactionManagement
@SpringBootApplication
@EnableDiscoveryClient
//@EnableCaching
@EnableWebMvc
@Import({ WebConfig.class, DynamicDataSourceConfig.class ,ContextConfig.class, SwaggerConfig.class,
	EpMultiDataSourceProp.class,EpRedisProp.class,EpHttpClientProp.class,
	EpIdGeneratorProp.class, EpSessionUtilProp.class, EpImgProp.class, 
	EpJwtProp.class, EpAuthProp.class })
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
	@Autowired
	EpRedisProp redisProp;
	@Autowired
	EpHttpClientProp httpClientProp;
	@Autowired
	EpIdGeneratorProp idGeneratorProp;
	@Autowired
	EpSessionUtilProp sessionUtilProp;
	@Autowired
	EpImgProp imgProp;
	@Autowired
	EpJwtProp jwtProp;
	@Autowired
	EpAuthProp authProp;
	@Autowired
	private RequestMappingHandlerMapping requestMappingHandlerMapping;

	@Bean
	@Order(0)
	public Object epUserServiceUtils() {
		RedisUtil.init(redisProp.getMaxTotal(), redisProp.getMaxIdle(), redisProp.getMinIdle(), redisProp.getMaxWaitMillis(), redisProp.getTestOnBorrow(), redisProp.getGlobalKeeptime(), redisProp.getAddr(), redisProp.getPort(), redisProp.getTimeoutInMillis(), redisProp.getAuth(), redisProp.getDatabase());
		HttpClientUtil.init(httpClientProp.getMaxTotal(), httpClientProp.getMaxPerRoute(), httpClientProp.getValidateInactivityDuration(), httpClientProp.getWaitIdleConnectionTimeout(), httpClientProp.getConnectTimeout(), httpClientProp.getSoTimeout(), httpClientProp.getRetryExecutionCount());
		SessionUtil.init(sessionUtilProp.getSessionKeepTimeSeconds());
		FileUploadUtil.init(imgProp.getUrlPrefix(), idGeneratorProp.getWorkerId(), imgProp.getNfsFolder());
		Base64ImageUtil.init(imgProp.getUrlPrefix(), idGeneratorProp.getWorkerId(), imgProp.getNfsFolder());
		IdGenerator.init(idGeneratorProp.getWorkerId());
		JwtUtil.init(jwtProp.getJwtAuthKey());
		return new Object();
	}
	
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
