package com.zhrenjie04.alex.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;

/**
 * @author 张人杰
 */
public class SpringUtil{

	private static final Logger logger = LoggerFactory.getLogger(SpringUtil.class);

	private static ApplicationContext applicationContext = null;

	public static void init(ApplicationContext applicationContext) {
		if (SpringUtil.applicationContext == null) {
			SpringUtil.applicationContext = applicationContext;
		}
		logger.info("-------------------------------------------");
		logger.info("SpringUtil.applicationContext == " + SpringUtil.applicationContext);
		logger.info("-------------------------------------------");
	}
	/***
	 * 根据一个bean的id获取配置文件中相应的bean
	 */
	public static Object getBean(String beanName) throws BeansException {
		if (applicationContext.containsBean(beanName)) {
			return applicationContext.getBean(beanName);
		}
		return null;
	}

	/***
	 * 根据一个bean的类型获取配置文件中相应的bean
	 */
	public static <T> T getBeanByClass(Class<T> requiredType) throws BeansException {
		return applicationContext.getBean(requiredType);
	}

	/**
	 * 如果BeanFactory包含一个与所给名称匹配的bean定义，则返回true
	 */
	public static boolean containsBean(String beanName) {
		return applicationContext.containsBean(beanName);
	}

	public static ApplicationContext getApplicationContext() {
		return SpringUtil.applicationContext;
	}

}