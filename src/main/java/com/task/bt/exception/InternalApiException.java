package com.task.bt.exception;

public class InternalApiException extends RuntimeException{
    public InternalApiException(String message, Throwable cause) {
        super(message);
    }

    public InternalApiException(String message) {
    }
}
