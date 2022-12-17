package com.ead.authuser.exceptions;

public class DatabaseIntegrityException extends RuntimeException {

    public DatabaseIntegrityException(String message) {
        super(message);
    }
}
