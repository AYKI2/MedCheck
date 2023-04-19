package com.example.medcheckb8.db.exceptions;

public class NotFountException extends RuntimeException {
    public NotFountException() {
        super();
    }

    public NotFountException(String message) {
        super(message);
    }
}
