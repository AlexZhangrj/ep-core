package com.zhrenjie04.alex.core;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(value=ResponseJsonWithFilters.class)
public @interface ResponseJsonWithFilter {
    Class<?> type();
    String include() default "";
    String exclude() default "";
}
