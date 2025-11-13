package org.labcabrera.sample.archetype.interfaces.http;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.labcabrera.sample.archetype.domain.player.exceptions.DomainException;
import org.labcabrera.sample.archetype.domain.player.exceptions.ConstraintViolationException;
import org.labcabrera.sample.archetype.interfaces.http.dto.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class RestExceptionHandler {

    @ExceptionHandler(DomainException.class)
    public ResponseEntity<ApiError> handleDomainException(DomainException ex) {
        log.error("Domain exception: code={}, message={}", ex.getCode(), ex.getMessage(), ex);
        var apiError = fromDomainException(ex);
        return ResponseEntity.status(HttpStatus.valueOf(ex.getStatus())).body(apiError);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidationExceptions(MethodArgumentNotValidException ex) {
        log.error("Validation exception", ex);
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        var apiError = new ApiError(
            "VALIDATION_FAILED",
            "Validation failed",
            LocalDateTime.now(),
            errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiError> handleIllegalArgumentException(IllegalArgumentException ex) {
        log.error("Illegal argument exception", ex);
        ApiError error = new ApiError(
            "BAD_REQUEST",
            ex.getMessage(),
            LocalDateTime.now(),
            Collections.emptyMap());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiError> handleTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        log.error("Type mismatch exception", ex);
        Class<?> requiredType = ex.getRequiredType();
        String typeName = requiredType != null ? requiredType.getSimpleName() : "unknown";
        String message = String.format("Parameter '%s' should be of type %s",
            ex.getName(),
            typeName);
        ApiError error = new ApiError(
            "BAD_REQUEST",
            message,
            LocalDateTime.now(),
            Collections.emptyMap());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiError> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        log.error("HTTP message not readable exception", ex);
        ApiError error = new ApiError(
            "BAD_REQUEST",
            "Malformed JSON request or invalid data format",
            LocalDateTime.now(),
            Collections.emptyMap());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ApiError> handleNoHandlerFoundException(NoHandlerFoundException ex) {
        log.error("No handler found exception", ex);
        ApiError error = new ApiError(
            "NOT_FOUND",
            "Resource not found",
            LocalDateTime.now(),
            Collections.emptyMap());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGenericException(Exception ex) {
        log.error("Unexpected exception", ex);
        ApiError error = new ApiError(
            "INTERNAL_SERVER_ERROR",
            "An unexpected error occurred",
            LocalDateTime.now(),
            Collections.emptyMap());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    private ApiError fromDomainException(DomainException ex) {
        var details = new LinkedHashMap<String, String>();
        if (ex instanceof ConstraintViolationException) {
            var ve = (ConstraintViolationException) ex;
            ve.getMessages().forEach((key, value) -> details.put(key, value));
        }
        details.put("message", ex.getMessage());
        details.put("stack_trace", ExceptionUtils.getStackTrace(ex));
        return new ApiError(
            ex.getCode(),
            ex.getMessage(),
            LocalDateTime.now(),
            details);
    }

}
