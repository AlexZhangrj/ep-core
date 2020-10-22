package com.zhrenjie04.alex.user;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.security.SecurityScheme.In;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@EnableOpenApi
@Configuration
public class SwaggerConfiguration {
	@Bean
    public Docket createRestApi() {
		List<SecurityScheme> securitySchemes=new LinkedList<>();
		ApiKey apiKeyHeader = new ApiKey("token", "token", In.HEADER.name());
		securitySchemes.add(apiKeyHeader);
		ApiKey apiKeyCookie = new ApiKey("sid",	"sid", In.COOKIE.name());
		securitySchemes.add(apiKeyCookie);
		Set<String> protocols=new HashSet<>();
		protocols.add("https");
		protocols.add("http");
        return new Docket(DocumentationType.OAS_30).pathMapping("/")
        		.apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                // 支持的通讯协议集合
                .protocols(protocols)
                // 授权信息设置，必要的header token等认证信息
                .securitySchemes(securitySchemes);
    }

    /**
     * API 页面上半部分展示信息
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("User Service Microservice Api Doc")
                .description("user-service api")
                .contact(new Contact("Alex.Zhangrj", null, "zhangrenjie1981@126.com"))
                .version("2.0.4")
                .build();
    }
}
