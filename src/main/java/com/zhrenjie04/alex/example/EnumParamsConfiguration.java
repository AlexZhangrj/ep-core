package com.zhrenjie04.alex.example;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import com.zhrenjie04.alex.core.BaseEumnConverter;

@Configuration
public class EnumParamsConfiguration extends WebMvcConfigurationSupport{
	@Override
	public FormattingConversionService mvcConversionService() {
		FormattingConversionService f = super.mvcConversionService();
        f.addConverter(new BaseEumnConverter<AuditStatusEnum>());
        return f;
    }

	public static void main(String[] args) {
		System.out.println(new AuditStatusEnumConverter().convert("auditing").getDescription());
	}
}

class AuditStatusEnumConverter extends BaseEumnConverter<AuditStatusEnum>{
}