package com.zhrenjie04.alex.core;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "id-generator")
public class EpIdGeneratorProp {
	private Integer workerId=0;
}
