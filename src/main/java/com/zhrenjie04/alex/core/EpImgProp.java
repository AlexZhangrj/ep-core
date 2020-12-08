package com.zhrenjie04.alex.core;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "img")
public class EpImgProp {
	private String urlPrefix = "";
	private String nfsFolder = "";
}
