package com.zhrenjie04.alex.example;

import com.fasterxml.jackson.annotation.JsonValue;
import com.zhrenjie04.alex.core.BasicEnum;

public enum ExampleEnum implements BasicEnum {

	Auditing("auditing","auditing","审核中"),
	Approved("approved","approved","审核通过"),
	Rejected("rejected","rejected","审核不通过");
	@JsonValue
	private String frontendCode;
	private String dbCode;
	private String description;

	private ExampleEnum(String frontendCode, String dbCode, String description) {
		this.dbCode=dbCode;
		this.description=description;
	}

	public String getDescription() {
		return description;
	}

	@Override
	public Object getDbCode() {
		return dbCode;
	}

	@Override
	public String getFrontendCode() {
		return frontendCode;
	}
}
