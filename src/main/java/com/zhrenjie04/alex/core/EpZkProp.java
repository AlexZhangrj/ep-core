package com.zhrenjie04.alex.core;

import org.springframework.boot.context.properties.ConfigurationProperties;
import lombok.Data;

@Data
@ConfigurationProperties(prefix = "zookeeper")
public class EpZkProp {
	private String connectString="";
}
