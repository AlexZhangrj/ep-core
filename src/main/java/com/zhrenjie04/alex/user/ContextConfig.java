package com.zhrenjie04.alex.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

import com.zhrenjie04.alex.core.AlexOperationLogAspect;

/**
 * @author 张人杰
 */
@Configuration
@ComponentScan(basePackages = { "com.zhrenjie04.alex.core" }, includeFilters = {
		@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {
				AlexOperationLogAspect.class }) }, useDefaultFilters = false)
@ComponentScan(basePackages = { "com.zhrenjie04.alex.service", "com.zhrenjie04.alex.util",
		"com.zhrenjie04.alex.user.service",
		"com.zhrenjie04.alex.user.controller.inner", "com.zhrenjie04.alex.user.controller.fore",
		"com.zhrenjie04.alex.user.controller.back"})
@MapperScan("com.zhrenjie04.alex.user.dao")
public class ContextConfig {
}
