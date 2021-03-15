package com.zhrenjie04.alex.core.exception;

public class RequestInputErrorException extends RuntimeException{

    private final String message;

    public RequestInputErrorException(String message) {
        this.message=message;
    }
    @Override
    public String getMessage() {
        return message;
    }
}
