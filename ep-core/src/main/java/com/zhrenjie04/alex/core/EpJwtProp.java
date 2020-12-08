package com.zhrenjie04.alex.core;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "jwt")
public class EpJwtProp {

	private String jwtAuthKey="";
}
