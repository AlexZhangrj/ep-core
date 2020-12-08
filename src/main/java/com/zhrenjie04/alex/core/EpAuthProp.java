package com.zhrenjie04.alex.core;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "auth")
public class EpAuthProp {
	private String unfilteredPathStartWith="/inner,/actuator,/actuator/health,/info,/instances";
	private String unfilteredCodes="user:back.login.do-login,user:fore.login.do-login,user:fore.register,user:back.login.do-logout";
	private String defaultAuthorizedCodes="user:back.login.do-logout";
	private String systemCode="user";
	private Boolean openSwagger=true;
}
