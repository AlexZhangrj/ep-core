package com.zhrenjie04.alex.core;

import com.netflix.hystrix.exception.HystrixRuntimeException;
import com.zhrenjie04.alex.core.exception.*;
import com.zhrenjie04.alex.util.DbUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author 张人杰
 */
public class AbstractGenericExceptionControllerAdvice {

	private static final Logger logger = LoggerFactory.getLogger(AbstractGenericExceptionControllerAdvice.class);

	@ExceptionHandler(value = UnauthorizedException.class)
	public ResponseEntity<String> unauthorizedException(Exception exception) {
		logger.error("UnauthorizedException", exception);
		DbUtil.remove();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		return new ResponseEntity<String>(
				"{\"status\":401,\"error\":\""
						+ exception.getMessage().replaceAll("\"","\\\\\"").replaceAll("\t"," ").replaceAll("(\r\n|\r|\n|\n\r)", "<br/>") + "\"}",
				headers, HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(value = RequestInputErrorException.class)
	public ResponseEntity<String> requestInputErrorException(Exception exception) {
		logger.error("RequestInputErrorException", exception);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		return new ResponseEntity<String>(
				"{\"status\":406,\"error\":\""
						+ exception.getMessage().replaceAll("\"","\\\\\"").replaceAll("\t"," ").replaceAll("(\r\n|\r|\n|\n\r)", "<br/>") + "\"}",
				headers, HttpStatus.PRECONDITION_FAILED);
	}

	@ExceptionHandler(value = PrerequisiteNotSatisfiedException.class)
	public ResponseEntity<String> prerequisiteNotSatisfiedException(Exception exception) {
		logger.error("PrerequisiteNotSatisfiedException", exception);
		DbUtil.remove();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		return new ResponseEntity<String>(
				"{\"status\":412,\"error\":\""
						+ exception.getMessage().replaceAll("\"","\\\\\"").replaceAll("\t"," ").replaceAll("(\r\n|\r|\n|\n\r)", "<br/>") + "\"}",
				headers, HttpStatus.PRECONDITION_FAILED);
	}

	@ExceptionHandler(value = InternalServerException.class)
	public ResponseEntity<String> internalServerException(InternalServerException error) {
		logger.error("InternalServerException", error);
		DbUtil.remove();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		return new ResponseEntity<String>(
				"{\"status\":500,\"error\":\""
						+ error.getMessage().replaceAll("\"","\\\\\"").replaceAll("\t"," ").replaceAll("(\r\n|\r|\n|\n\r)", "<br/>") + "\"}",
				headers, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(value = CrisisError.class)
	public ResponseEntity<String> crisisError(CrisisError error) {
		logger.error("CrisisError", error);
		DbUtil.remove();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		return new ResponseEntity<String>(
				"{\"status\":500,\"error\":\""
						+ error.getMessage().replaceAll("\"","\\\\\"").replaceAll("\t"," ").replaceAll("(\r\n|\r|\n|\n\r)", "<br/>") + "\"}",
				headers, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(value = HystrixRuntimeException.class)
	public ResponseEntity<String> hystrixRuntimeException(HystrixRuntimeException exception) {
		logger.error("HystrixRuntimeException", exception);
		logger.error("fallback:", exception.getFallbackException());
		DbUtil.remove();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		return new ResponseEntity<String>(
				"{\"status\":500,\"error\":\"" + exception.getFallbackException().getCause().getCause().getMessage()
						.replaceAll("\"","\\\\\"").replaceAll("\t"," ").replaceAll("(\r\n|\r|\n|\n\r)", "<br/>") + "\"}",
				headers, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(value = NullPointerException.class)
	public ResponseEntity<String> nullPointerException(NullPointerException error) {
		logger.error("NullPointerException", error);
		DbUtil.remove();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		return new ResponseEntity<String>("{\"status\":500,\"error\":\"Null\"}", headers,
				HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<String> exception(Exception exception) {
		logger.error("Exception", exception);
		DbUtil.remove();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		return new ResponseEntity<String>(
				"{\"status\":500,\"error\":\""
						+ exception.getMessage().replaceAll("\"","\\\\\"").replaceAll("\t"," ").replaceAll("(\r\n|\r|\n|\n\r)", "<br/>") + "\"}",
				headers, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(value = Error.class)
	public ResponseEntity<String> error(Error error) {
		logger.error("Error", error);
		DbUtil.remove();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		return new ResponseEntity<String>(
				"{\"status\":500,\"error\":\""
						+ error.getMessage().replaceAll("\"","\\\\\"").replaceAll("\t"," ").replaceAll("(\r\n|\r|\n|\n\r)", "<br/>") + "\"}",
				headers, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	public static void main(String[] args) {
		System.out.println("</script>".replaceAll("<\\/script", ""));
		System.out.println("a\"aaaa\"\r\nbbbbb");
		System.out.println("a\"aaaa\"\r\nbbbbb".replaceAll("\"","\\\\\"").replaceAll("\t"," ").replaceAll("(\r\n|\r|\n|\n\r)", "<br/>"));
	}
}
