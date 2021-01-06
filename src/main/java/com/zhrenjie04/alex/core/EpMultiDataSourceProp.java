package com.zhrenjie04.alex.core;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;

@Data
@ConfigurationProperties(prefix = "ep")
public class EpMultiDataSourceProp {
	HashMap<String,EpDataSourceProps>datasource=new HashMap<>();
	HashMap<String,Integer>datasourceCount=new HashMap<>();
}
