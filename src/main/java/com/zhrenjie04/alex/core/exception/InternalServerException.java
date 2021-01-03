package com.zhrenjie04.alex.core.exception;

public class InternalServerException extends RuntimeException{
    private final String message;
    public InternalServerException(String message) {
        this.message=message;
    }
    @Override
    public String getMessage() {
        return message;
    }
}
