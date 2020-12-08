package com.zhrenjie04.alex.core.exception;
/**
 * 
 * @author 张人杰
 */
public class CrisisError extends RuntimeException{
	private static final long serialVersionUID = 3769037238097462426L;
	private final String message;
	public CrisisError(String message) {
		super(message);
		this.message=message;
	}
	public CrisisError(Exception e) {
		super(e);
		this.message=e.getMessage();
	}
	@Override
	public String getMessage() {
		return message;
	}
}
