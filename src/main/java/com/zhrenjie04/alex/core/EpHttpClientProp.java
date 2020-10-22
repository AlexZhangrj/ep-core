package com.zhrenjie04.alex.core;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "http")
public class EpHttpClientProp {
	private Integer validateInactivityDuration;
	private Integer waitIdleConnectionTimeout;
	private Integer connectTimeout;
	private Integer soTimeout;
	private Integer maxTotal;
	private Integer maxPerRoute;
	private Integer retryExecutionCount;
}
