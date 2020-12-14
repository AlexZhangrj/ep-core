package com.zhrenjie04.alex.example;

import com.fasterxml.jackson.annotation.JsonValue;
import com.zhrenjie04.alex.core.BasicEnum;

public enum ExampleEnum implements BasicEnum {

	Auditing("auditing",0,"审核中"),
	Approved("approved",1,"审核通过"),
	Rejected("rejected",2,"审核不通过");
	@JsonValue
	private String frontendCode;
	private int dbCode;
	private String description;

	private ExampleEnum(String frontendCode, int dbCode, String description) {
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
