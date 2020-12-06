package com.zhrenjie04.alex.core;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 权限注解 用于检查权限 规定访问权限
 *
 * @example @Permission("huatu.cloud.project.add")
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD,ElementType.TYPE})
public @interface Permission {
    String value();
    String operMemo() default "";
}
