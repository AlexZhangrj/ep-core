package com.zhrenjie04.alex.manager;

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
		"com.zhrenjie04.alex.manager.service", "com.zhrenjie04.alex.manager.controller",
		"com.zhrenjie04.alex.manager.controller.inner", "com.zhrenjie04.alex.manager.controller.fore",
		"com.zhrenjie04.alex.manager.controller.back", "com.zhrenjie04.alex.message.Interface" })
@MapperScan("com.zhrenjie04.alex.manager.dao")
public class ContextConfig {
}
