package com.task.bt.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class CustomExceptionHandler {

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ValidationException> handleValidationException(ValidationException ex) {
        log.error("Validation error", ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex);
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ValidationException> handleBindException(BindException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        List<String> errors = bindingResult.getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.toList());

        ValidationException validationException = new ValidationException(errors);
        log.error("Validation error", validationException);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validationException);
    }

    @ExceptionHandler(ExternalApiException.class)
    public ResponseEntity<String> handleExternalApiException(ExternalApiException ex) {
        log.error("Error calling external API", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("An error occurred while processing your request.");
    }

    @ExceptionHandler(InternalApiException.class)
    public ResponseEntity<String> handleInternalApiException(InternalApiException iae) {
        log.error("Error processing request", iae);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("An error occurred while processing your request.");
    }
}
