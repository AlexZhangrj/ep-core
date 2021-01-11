package com.zhrenjie04.alex.core;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import com.zhrenjie04.alex.util.DbUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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
//			DataSource dataSource = DataSourceBuilder.create()
//				.url(dataSourceProps.get(key).getUrl())
//				.username(dataSourceProps.get(key).getUsername())
//				.password(dataSourceProps.get(key).getPassword())
//				.driverClassName(dataSourceProps.get(key).getDriverClassName())
//				.build();
//			if (dataSource != null) {
//	        	datasourceMap.put(key, dataSource);
//	        }
			HikariConfig config = new HikariConfig();
			config.setDriverClassName(dataSourceProps.get(key).getDriverClassName());
			config.setJdbcUrl(dataSourceProps.get(key).getUrl());
			config.setUsername(dataSourceProps.get(key).getUsername());
			config.setPassword(dataSourceProps.get(key).getPassword());
			config.setMaximumPoolSize(dataSourceProps.get(key).getMaximumPoolSize());
			config.setMaxLifetime(dataSourceProps.get(key).getMaxLifeTime());
			config.setMinimumIdle(dataSourceProps.get(key).getMinimumIdle());
			config.setConnectionTestQuery(dataSourceProps.get(key).getConnectionTestQuery());
			config.setConnectionTimeout(dataSourceProps.get(key).getConnectionTimeout());
			config.setIdleTimeout(dataSourceProps.get(key).getIdleTimeout());
			config.setValidationTimeout(dataSourceProps.get(key).getValidationTimeout());
			config.setConnectionInitSql(dataSourceProps.get(key).getConnectionInitSql());
			config.setInitializationFailTimeout(dataSourceProps.get(key).getInitializationFailTimeout());
			config.setAutoCommit(dataSourceProps.get(key).getAutoCommit());
			config.addDataSourceProperty("cachePrepStmts", "true");
			config.addDataSourceProperty("prepStmtCacheSize", "250");
			config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
			DataSource dataSource = new HikariDataSource(config);
			if (dataSource != null) {
	        	datasourceMap.put(key, dataSource);
	        }
		}
		DbUtil.dbCountInGroupMap=epMutiDataSourceProp.getDatasourceCount();
        dds.setTargetDataSources(datasourceMap);
        dds.afterPropertiesSet();
        return dds;
	}
}
