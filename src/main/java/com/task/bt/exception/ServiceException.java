package com.task.bt.exception;

public class ServiceException extends InternalApiException {
    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(String message) {
        super(message);
    }
}
