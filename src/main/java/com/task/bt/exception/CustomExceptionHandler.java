package com.task.bt.exception;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

/**
 * This class is responsible for handling exceptions thrown by the application.
 * It returns a response with appropriate HTTP status code and error message.
 */
@RestControllerAdvice
@Slf4j
public class CustomExceptionHandler {

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Map<String, Object>> handleValidationException(ValidationException ex) {
        log.error("Validation error", ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", StringUtils.trim(ex.getMessage())));
    }

    @ExceptionHandler(ExternalApiException.class)
    public ResponseEntity<Map<String, Object>> handleExternalApiException(ExternalApiException ex) {
        log.error("Error calling external API", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", StringUtils.trim(ex.getMessage())));
    }

    @ExceptionHandler({InternalApiException.class, ServiceException.class})
    public ResponseEntity<Map<String, Object>> handleInternalApiException(Exception ex) {
        log.error("Error processing internal request", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", StringUtils.trim(ex.getMessage())));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, Object>> handleConstraintViolationException(ConstraintViolationException cve) {
        log.error("Validation error", cve);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", StringUtils.trim(cve.getMessage().split(":")[1])));
    }
}
