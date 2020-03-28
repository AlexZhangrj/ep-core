package com.zhrenjie04.alex.core;

import java.lang.annotation.*;

/**
 * 审计日志标注
 * @author 张人杰
 * @example @AuditLog(code="purchaseOrder",memo="订单购买日志")
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface AuditLog {
    String code();
    String memo();
}
