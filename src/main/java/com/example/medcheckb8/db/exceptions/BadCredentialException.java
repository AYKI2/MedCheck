package com.example.medcheckb8.db.exceptions;

public class BadCredentialException extends RuntimeException{
    public BadCredentialException() {}
    public BadCredentialException(String message) {
        super(message);
    }
}
