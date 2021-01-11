package com.zhrenjie04.alex.core;

import lombok.Data;

@Data
public class EpDataSourceProps {
	private String url;
	private String username;
	private String password;
	private String driverClassName;
	private String groupName;
	private Integer maximumPoolSize;
	private Long maxLifeTime;
	private Integer minimumIdle;
	private Boolean autoCommit;
	private String connectionTestQuery;
	private Long connectionTimeout;
	private Long idleTimeout;
	private Long validationTimeout;
	private String connectionInitSql;
	private Long initializationFailTimeout;
	private Boolean cachePrepStmts;
	private Integer prepStmtCacheSize;
	private Integer prepStmtCacheSqlLimit;
}
