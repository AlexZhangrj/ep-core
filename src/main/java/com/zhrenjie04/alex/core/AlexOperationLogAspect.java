package com.zhrenjie04.alex.core;

import com.zhrenjie04.alex.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.LinkedList;
import java.util.List;

@Slf4j
public abstract class AlexOperationLogAspect {

	public void  doBefore(JoinPoint joinPoint){
		OperationLog operationLog = new OperationLog();
		List<Object> args=new LinkedList<>();
		for(Object arg:joinPoint.getArgs()){
			if(arg instanceof HttpServletRequest){
				continue;
			}
			if(arg instanceof HttpServletResponse){
				continue;
			}
			args.add(arg);
		}
		operationLog.setParamsJson(JsonUtil.newSerializer().without(User.class,"password,oldPassword").doStringify(args));
		log.info("Operation Start:{}",operationLog);
	}
	public void  doAfterReturning(JoinPoint joinPoint,Object result){
		OperationLog operationLog = new OperationLog();
		List<Object> args=new LinkedList<>();
		for(Object arg:joinPoint.getArgs()){
			if(arg instanceof HttpServletRequest){
				continue;
			}
			if(arg instanceof HttpServletResponse){
				continue;
			}
			args.add(arg);
		}
		operationLog.setParamsJson(JsonUtil.newSerializer().without(User.class,"password,oldPassword").doStringify(args));
		operationLog.setResultJson(JsonUtil.stringify(result));
		log.info("Operation Finished:{}",operationLog);
    }
	public void  doAfterThrowing(JoinPoint joinPoint,Exception e){
		OperationLog operationLog = new OperationLog();
		List<Object> args=new LinkedList<>();
		for(Object arg:joinPoint.getArgs()){
			if(arg instanceof HttpServletRequest){
				continue;
			}
			if(arg instanceof HttpServletResponse){
				continue;
			}
			args.add(arg);
		}
		operationLog.setParamsJson(JsonUtil.newSerializer().without(User.class,"password,oldPassword").doStringify(args));
		operationLog.setExceptionJson(JsonUtil.stringify(e));
		log.info("Operation Exception:{}",operationLog);
	}
}
