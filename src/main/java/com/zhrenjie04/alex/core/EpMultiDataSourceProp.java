package com.zhrenjie04.alex.core;

import java.util.HashMap;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "ep")
public class EpMultiDataSourceProp {
	HashMap<String,EpDataSourceProps>datasource=new HashMap<>();
	HashMap<String,Integer>dsCount=new HashMap<>();
}
