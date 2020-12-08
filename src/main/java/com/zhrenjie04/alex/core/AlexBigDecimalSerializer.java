package com.zhrenjie04.alex.core;

import java.io.IOException;
import java.math.BigDecimal;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
/**
 * 用于@JsonSerialize标注序列化BigDecimal为字符串
 * 用法：@JsonSerialize(using=AlexBigDecimalSerializer.class)
 * @author Alex.Zhang 作者：张人杰
 * 使用、引用，请保留作者信息
 */
public class AlexBigDecimalSerializer extends JsonSerializer<BigDecimal> {
	
	@Override
	public void serialize(BigDecimal value, JsonGenerator gen, SerializerProvider serializers)
			throws IOException, JsonProcessingException {
		if(value!=null) {
			gen.writeString(value.toString());
		}else {
			gen.writeNull();
		}
	}
}
