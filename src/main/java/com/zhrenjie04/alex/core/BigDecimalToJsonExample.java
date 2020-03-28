package com.zhrenjie04.alex.core;

import java.math.BigDecimal;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zhrenjie04.alex.util.JsonUtil;

public class BigDecimalToJsonExample {
	@JsonSerialize(using=AlexBigDecimalSerializer.class)
	private BigDecimal value;

	public BigDecimal getValue() {
		return value;
	}
	public void setValue(BigDecimal value) {
		this.value = value;
	}
	public static void main(String[] args) {
		BigDecimal d=new BigDecimal("2.9");
		BigDecimalToJsonExample example=new BigDecimalToJsonExample();
		example.setValue(d);
		String s=JsonUtil.stringify(example);
		System.out.println(s);
		example.setValue(example.getValue().add(new BigDecimal("0.05")));
		s=JsonUtil.stringify(example);
		System.out.println(s);
		example=JsonUtil.parse(s, BigDecimalToJsonExample.class);
		System.out.println(example.getValue());
	}
	
}
