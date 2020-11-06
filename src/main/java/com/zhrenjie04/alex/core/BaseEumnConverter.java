package com.zhrenjie04.alex.core;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import com.zhrenjie04.alex.util.EnumUtil;

public class BaseEumnConverter<T extends Enum<?> & BaseEnum> extends AbstractConverter<T>{
	@Override
	public T convert(String source) {
		ParameterizedType parameterizedType=(ParameterizedType)this.getClass().getGenericSuperclass();
//		System.out.println(parameterizedType.getTypeName());
		Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
		Class<T> clazz=(Class<T>)actualTypeArguments[0];
		return EnumUtil.codeOf(clazz, source);
	}
}
//附加代码：判断某个类是否为另一个类的父类
//if(BaseEnum.class.isAssignableFrom(clazz)) {//BaseEnum是clazz的父类
//return EnumUtil.codeOf(clazz, source);
//}