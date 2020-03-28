package com.zhrenjie04.alex.core;

import java.io.IOException;
import java.util.Date;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
/**
 * 用于@JsonSerialize标注序列化Date为时间戳数值
 * 用法：@JsonSerialize(using=AlexTimestampSerializer.class)
 * @author Alex
 *
 */
public class AlexTimestampSerializer extends JsonSerializer<Date> {
	
	@Override
	public void serialize(Date value, JsonGenerator gen, SerializerProvider serializers)
			throws IOException, JsonProcessingException {
		if(value!=null) {
			gen.writeNumber(value.getTime());
		}else {
			gen.writeNull();
		}
	}
}
