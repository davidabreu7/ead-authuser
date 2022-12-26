package com.ead.authuser.controllers.exceptions;

import com.ead.authuser.exceptions.DatabaseIntegrityException;
import com.ead.authuser.exceptions.FieldException;
import com.ead.authuser.exceptions.ResourceNotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.MongoWriteException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class ControllerExceptionHandler {



    private static final  String ERROR = "Validation error";

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

    @ExceptionHandler(MongoWriteException.class)
    public ResponseEntity<MongoError> database(MongoWriteException e, HttpServletRequest request) {
        return getMongoWriteException(e, request);
    }



    private ResponseEntity<StandardError> getStandardErrorResponseEntity(RuntimeException e, HttpServletRequest request, HttpStatus status) {
        StandardError error = new StandardError();
        error.setTimestamp(Instant.now());
        error.setStatus(status.value());
        error.setMessage(e.getMessage());
        error.setError(ERROR);
        error.setPath(request.getRequestURI());
        return ResponseEntity.status(status).body(error);
    }

    private ResponseEntity<ValidationError> getValidationErrorResponseEntity(MethodArgumentNotValidException e, HttpServletRequest request) {

        ValidationError err = new ValidationError();
        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;

        err.setTimestamp(Instant.now());
        err.setStatus(status.value());
        err.setMessage(e.getMessage());
        err.setError(ERROR);
        err.setPath(request.getRequestURI());
        e.getBindingResult().getFieldErrors()
                .stream()
                .map(error -> new FieldError(error.getField(), error.getDefaultMessage()))
                .forEach(fieldError -> err.getFieldErrors().add(fieldError));
        return ResponseEntity.status(status).body(err);
    }

    private ResponseEntity<MongoError> getMongoWriteException(MongoWriteException e, HttpServletRequest request)  {

        ValidationError err = new ValidationError();
        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
        ObjectMapper mapper = new ObjectMapper();
        MongoError mongoError = new MongoError();
        err.setTimestamp(Instant.now());
        err.setStatus(status.value());
        err.setMessage(e.getMessage());
        err.setError(ERROR);
        err.setPath(request.getRequestURI());
        JsonNode node;
        try {
            node = mapper.readTree(e.getError().getDetails().get("details").toString());
        } catch (JsonProcessingException ex) {
            throw new FieldException(ex.getMessage());
        }

        mongoError.setMessage(e.getMessage());
        mongoError.setJsonError(node);

        return ResponseEntity.status(status).body(mongoError);

    }
}


