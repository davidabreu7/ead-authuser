package com.ead.authuser.controllers.exceptions;

import java.util.ArrayList;
import java.util.List;

public class ValidationError extends StandardError{

    private final List<FieldError> fieldErrors = new ArrayList<>();

    public List<FieldError> getFieldErrors() {
        return fieldErrors;
    }

}
