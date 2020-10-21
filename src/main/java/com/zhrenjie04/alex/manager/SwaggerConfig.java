package com.zhrenjie04.alex.manager;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author 张人杰
 */
@Configuration  
@EnableSwagger2  
public class SwaggerConfig {  
    /** 
     * 可以定义多个组，比如本类中定义把test和demo区分开了 （访问页面就可以看到效果了） 
     * 
     */  
    @Bean  
    public Docket testApi() {  
        return new Docket(DocumentationType.SWAGGER_2)  
                .apiInfo(apiInfo())  
                .select()  
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any()).build();  
    }  
  
    private ApiInfo apiInfo() {  
        return new ApiInfoBuilder()  
                .title("RESTful APIs")  
                .description("后台管理系统RESTful APIs")  
                .termsOfServiceUrl("")
                .version("1.0.0")  
                .build();  
    }  
}  