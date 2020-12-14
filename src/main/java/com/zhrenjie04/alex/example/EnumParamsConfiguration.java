package com.zhrenjie04.alex.example;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import com.zhrenjie04.alex.core.BasicEumnConverter;
import com.zhrenjie04.alex.util.JsonUtil;

@Configuration
public class EnumParamsConfiguration extends WebMvcConfigurationSupport{
	@Override
	public FormattingConversionService mvcConversionService() {
		FormattingConversionService f = super.mvcConversionService();
        f.addConverter(new AuditStatusEnumConverter());
        return f;
    }

	public static void main(String[] args) {
		System.out.println(new AuditStatusEnumConverter().convert("auditing").getDescription());
		ExampleEntity entity=new ExampleEntity();
		entity.setId("adfafasdfasdfasdf");
		entity.setStatus(ExampleEnum.Auditing);
		System.out.println(JsonUtil.stringify(entity));
	}
}

class AuditStatusEnumConverter extends BasicEumnConverter<ExampleEnum> {
}