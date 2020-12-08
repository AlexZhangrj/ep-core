package com.zhrenjie04.alex.core;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "session")
public class EpSessionUtilProp {
	private Integer sessionKeepTimeSeconds=0;
}
