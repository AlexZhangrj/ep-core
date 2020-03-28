package com.zhrenjie04.alex.core.exception;

/**
 * @author 张人杰
 */
public class UnauthorizedException extends RuntimeException{

	private static final long serialVersionUID = -7171595187640366886L;
	private final String message;
	public UnauthorizedException(String message) {
		this.message=message;
	}
	@Override
	public String getMessage() {
		return message;
	}
}
