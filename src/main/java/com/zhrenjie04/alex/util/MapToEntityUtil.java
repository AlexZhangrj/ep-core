package com.zhrenjie04.alex.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MapToEntityUtil {
	/**
	 * 只转一级
	 * @param <T>
	 * @param clazz
	 * @param map
	 * @return
	 */
	public static <T> T mapToEntity(Class<T> clazz,Map<String, Object> map){
        T obj = null;
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
            obj = clazz.getDeclaredConstructor().newInstance(); // 创建 JavaBean 对象
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            // 给 JavaBean 对象的属性赋值
            for (int i = 0; i < propertyDescriptors.length; i++) {
                PropertyDescriptor descriptor = propertyDescriptors[i];
                String propertyName = descriptor.getName();
                if (map.containsKey(propertyName)) {
                    Object value = map.get(propertyName);
                    Object[] args = new Object[1];
                    args[0] = value;
                    try {
                        descriptor.getWriteMethod().invoke(obj, args);
                    } catch (InvocationTargetException e) {
                        log.error("MapToEntityUtil.mapToEntity",e);
                    }
                }
            }
        } catch (IllegalAccessException e) {
            log.error("MapToEntityUtil.mapToEntity",e);
        } catch (IntrospectionException e) {
            log.error("MapToEntityUtil.mapToEntity",e);
        } catch (IllegalArgumentException e) {
            log.error("MapToEntityUtil.mapToEntity",e);
        } catch (InstantiationException e) {
            log.error("MapToEntityUtil.mapToEntity",e);
        } catch (InvocationTargetException e) {
            log.error("MapToEntityUtil.mapToEntity",e);
		} catch (NoSuchMethodException e) {
            log.error("MapToEntityUtil.mapToEntity",e);
		} catch (SecurityException e) {
            log.error("MapToEntityUtil.mapToEntity",e);
		}
        return (T)obj;
    }
}
