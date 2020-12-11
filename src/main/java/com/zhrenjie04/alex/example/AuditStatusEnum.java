package com.zhrenjie04.alex.example;

import com.fasterxml.jackson.annotation.JsonValue;
import com.zhrenjie04.alex.core.BasicEnum;

public enum AuditStatusEnum implements BasicEnum {

	Auditing("auditing","审核中"),
	Approved("approved","审核通过"),
	Rejected("rejected","审核不通过");

	@JsonValue
	private String value;
	private String description;
	private AuditStatusEnum(String value,String description) {
		this.value=value;
		this.description=description;
	}
	public String getDescription() {
		return description;
	}

	@Override
	public Object getDbCode() {
		return value;
	}
}
