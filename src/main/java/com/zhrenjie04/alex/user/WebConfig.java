package com.zhrenjie04.alex.user;

import java.util.List;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.zhrenjie04.alex.core.AlexJsonReturnHandler;
import com.zhrenjie04.alex.core.interceptor.AuthorizationInterceptor;
import com.zhrenjie04.alex.core.interceptor.NoCachedInterceptor;
/**
 * @author 张人杰
 */
@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

	@Bean
    public FilterRegistrationBean<CorsFilter> corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<CorsFilter>(new CorsFilter(source));
        bean.setOrder(0);
        return bean;
    }
    @Bean
    public AuthorizationInterceptor authorizationInterceptor(){
        return new AuthorizationInterceptor();
    }

    @Bean
    public AlexJsonReturnHandler alexJsonReturnHandler(){
        return new AlexJsonReturnHandler();
    }
    
//    @Bean
//    public CorsInterceptor corsInterceptor(){
//        return new CorsInterceptor();
//    }

    @Bean
    public CommonsMultipartResolver commonsMultipartResolver() {
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        resolver.setMaxUploadSize(1024 * 1024 * 20);
        return resolver;
    }

    @Bean
    public NoCachedInterceptor noCachedInterceptor() {
        return new NoCachedInterceptor();
    }

    @Override
    public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> returnValueHandlers) {
    	returnValueHandlers.add(alexJsonReturnHandler());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(corsInterceptor());
//        registry.addInterceptor(noCachedInterceptor());
//        registry.addInterceptor(authorizationInterceptor());
        registry.addInterceptor(new NoCachedInterceptor());
        registry.addInterceptor(new AuthorizationInterceptor());
    }
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
    	registry.addResourceHandler("swagger-ui.html")
    	.addResourceLocations("classpath:/META-INF/resources/");
    	registry.addResourceHandler("/webjars/**")
        .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}