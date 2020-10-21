package com.zhrenjie04.alex.core;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 张人杰
 */
@Configuration
public class DynamicDataSourceConfig {
	
	@Autowired
    private EpMultiDataSourceProp epMutiDataSourceProp;

	@Bean
	public DataSource dataSource() {
		DynamicDataSource dds=new DynamicDataSource();
		Map<Object, Object> datasourceMap = new ConcurrentHashMap<>();
		HashMap<String,EpDataSourceProps> dataSourceProps = epMutiDataSourceProp.getDatasource();
		for(String key : dataSourceProps.keySet()){
			DataSource dataSource = DataSourceBuilder.create()
				.url(dataSourceProps.get(key).getUrl())
				.username(dataSourceProps.get(key).getUsername())
				.password(dataSourceProps.get(key).getPassword())
				.driverClassName(dataSourceProps.get(key).getDriver())
				.build();
			if (dataSource != null) {
	        	datasourceMap.put(key, dataSource);
	        }
		}
        dds.setTargetDataSources(datasourceMap);
        dds.afterPropertiesSet();
        return dds;
	}
}
