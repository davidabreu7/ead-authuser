package com.ead.authuser.controllers.exceptions;

import com.ead.authuser.exceptions.DatabaseIntegrityException;
import com.ead.authuser.exceptions.FieldException;
import com.ead.authuser.exceptions.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StandardError> resourceNotFound(ResourceNotFoundException e, HttpServletRequest request) {
        return getStandardErrorResponseEntity(e, request, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(FieldException.class)
    public ResponseEntity<StandardError> resourceNotFound(FieldException e, HttpServletRequest request) {
        return getStandardErrorResponseEntity(e, request, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(DatabaseIntegrityException.class)
    public ResponseEntity<StandardError> database(DatabaseIntegrityException e, HttpServletRequest request) {
        return getStandardErrorResponseEntity(e, request, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationError> validation(MethodArgumentNotValidException e, HttpServletRequest request) {
        return getValidationErrorResponseEntity(e, request);
    }



    private ResponseEntity<StandardError> getStandardErrorResponseEntity(RuntimeException e, HttpServletRequest request, HttpStatus status) {
        StandardError err = new StandardError();
        err.setTimestamp(Instant.now());
        err.setStatus(status.value());
        err.setMessage(e.getMessage());
        err.setError("Validation error");
        err.setPath(request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    private ResponseEntity<ValidationError> getValidationErrorResponseEntity(MethodArgumentNotValidException e, HttpServletRequest request) {
        ValidationError err = new ValidationError();
        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
        err.setTimestamp(Instant.now());
        err.setStatus(status.value());
        err.setMessage(e.getMessage());
        err.setError("Validation error");
        err.setPath(request.getRequestURI());
        e.getBindingResult().getFieldErrors()
                .stream()
                .map(error -> new FieldError(error.getField(), error.getDefaultMessage()))
                .forEach(fieldError -> err.getFieldErrors().add(fieldError));
        return ResponseEntity.status(status).body(err);
    }

}
