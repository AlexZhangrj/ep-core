package com.zhrenjie04.alex.core;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import com.zhrenjie04.alex.util.DbUtil;

public class DynamicDataSource extends AbstractRoutingDataSource{

	@Override
	protected Object determineCurrentLookupKey() {
		return DbUtil.getDataSource();
	}

}
