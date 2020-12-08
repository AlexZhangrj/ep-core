package com.zhrenjie04.alex.core;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.error.ErrorAttributeOptions.Include;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.ServletWebRequest;

@Controller
/**
 * @author 张人杰
 */
public class AppErrorController implements ErrorController {

	private static final Logger logger = LoggerFactory.getLogger(AppErrorController.class);

	/**
	 * Error Attributes in the Application
	 */
	@Autowired
	private ErrorAttributes errorAttributes;

	private final static String ERROR_PATH = "/error";

	/**
	 * Controller for the Error Controller
	 * 
	 * @param errorAttributes
	 * @return
	 */

	public AppErrorController(ErrorAttributes errorAttributes) {
		this.errorAttributes = errorAttributes;
	}

	/**
	 * Supports the HTML Error View
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = ERROR_PATH, produces = "text/html")
	public String errorHtml(HttpServletRequest request) {
		Map<String, Object> map = getErrorAttributes(request, false);
		request.setAttribute("path", map.get("path"));
		request.setAttribute("error", map.get("error"));
		request.setAttribute("status", map.get("status"));
		request.setAttribute("timestamp", map.get("timestamp"));
		return ERROR_PATH;
	}

	/**
	 * Supports other formats like JSON, XML
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = ERROR_PATH)
	@ResponseBody
	public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
		Map<String, Object> body = getErrorAttributes(request, getTraceParameter(request));
		HttpStatus status = getStatus(request);
		return new ResponseEntity<Map<String, Object>>(body, status);
	}

	/**
	 * Returns the path of the error page.
	 *
	 * @return the error path
	 */
	@Override
	public String getErrorPath() {
		return ERROR_PATH;
	}

	private boolean getTraceParameter(HttpServletRequest request) {
		String parameter = request.getParameter("trace");
		if (parameter == null) {
			return false;
		}
		return !"false".equals(parameter.toLowerCase());
	}

	private Map<String, Object> getErrorAttributes(HttpServletRequest request, boolean includeStackTrace) {
		ServletWebRequest webRequest = new ServletWebRequest(request);
		Map<String, Object> map = this.errorAttributes.getErrorAttributes(webRequest, ErrorAttributeOptions.defaults().including(Include.STACK_TRACE));
		String url = request.getRequestURL().toString();
		map.put("URL", url);
		logger.debug("AppErrorController.method [error info]: status-" + map.get("status") + ", request url-" + url);
		return map;
	}

	private HttpStatus getStatus(HttpServletRequest request) {
		Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
		if (statusCode != null) {
			try {
				return HttpStatus.valueOf(statusCode);
			} catch (Exception ex) {
				logger.error("getStatus", ex);
			}
		}
		return HttpStatus.INTERNAL_SERVER_ERROR;
	}

}