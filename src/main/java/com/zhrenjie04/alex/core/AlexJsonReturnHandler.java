package com.zhrenjie04.alex.core;

import java.lang.annotation.Annotation;
import java.util.Arrays;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * @author 张人杰
 */
public class AlexJsonReturnHandler implements HandlerMethodReturnValueHandler {

	private static final Logger logger=LoggerFactory.getLogger(AlexJsonReturnHandler.class);
	
	@Override
	public boolean supportsReturnType(MethodParameter returnType) {
		boolean flag = returnType.getMethodAnnotation(ResponseJsonWithFilter.class) != null
				|| returnType.getMethodAnnotation(ResponseJsonWithFilters.class) != null
				|| returnType.getMethodAnnotation(ResponseJson.class) != null;
		return flag;
	}

	@Override
	public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest) throws Exception {
		HttpServletResponse response = webRequest.getNativeResponse(HttpServletResponse.class);
		Annotation[] annos = returnType.getMethodAnnotations();
		AlexJsonSerializer jsonSerializer = new AlexJsonSerializer();
		jsonSerializer.beginAddFilter();
		Arrays.asList(annos).forEach(a -> {
			if (a instanceof ResponseJsonWithFilter) {
				ResponseJsonWithFilter json = (ResponseJsonWithFilter) a;
				jsonSerializer.filter(json.type(), json.include(), json.filter());
			}
			if (a instanceof ResponseJsonWithFilters) {
				ResponseJsonWithFilters jsonFilters = (ResponseJsonWithFilters) a;
				for (ResponseJsonWithFilter json : jsonFilters.value()) {
					jsonSerializer.filter(json.type(), json.include(), json.filter());
				}
			}
		});
		jsonSerializer.endAddFilter();
		response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
		String json = jsonSerializer.toJson(returnValue);
		if (returnValue instanceof JsonResult) {
			logger.debug("returnValue is JsonResult");
			JsonResult result = (JsonResult) returnValue;
			if (result.getStatus() != HttpStatus.OK.value()) {
				logger.debug("result status is not ok");
				response.sendError(result.getStatus(), result.getMessage());
			} else {
				logger.debug("result status is ok");
				response.getWriter().write(json);
				response.getWriter().flush();
			}
		} else {
			logger.debug("returnValue is not JsonResult");
			response.getWriter().write(json);
			response.getWriter().flush();
		}
		mavContainer.setRequestHandled(true);
	}
}
