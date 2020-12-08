package com.zhrenjie04.alex.core.exception;

/**
 * @author 张人杰
 */
public class PrerequisiteNotSatisfiedException extends RuntimeException{
	private static final long serialVersionUID = 5333685444428757922L;

	private final String message;

	public PrerequisiteNotSatisfiedException(String message) {
		this.message=message;
	}
	@Override
	public String getMessage() {
		return message;
	}
}
