package com.task.bt.exception;

public class ExternalApiException extends RuntimeException{
    public ExternalApiException(String message, Exception e) {
        super(message, e);
    }

    public ExternalApiException(String message) {
        super(message);
    }
}
