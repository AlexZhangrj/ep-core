package com.zhrenjie04.alex.core;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Order(-5)
@Slf4j
public class AlexOperationLogAspect {
	
	@AfterReturning(value="execution(public * com.zhrenjie04.alex..*.controller.fore..*.*(..))",returning="result")
	public void  doAfterReturning(JoinPoint joinPoint,Object result){
		OperationLog operationLog = new OperationLog();
		operationLog.params = joinPoint.getArgs();
		operationLog.result=result;
		log.info("operation:{}",operationLog);
    }
}
