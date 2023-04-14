package com.example.medcheckb8.exceptions;

public class BadRequestException extends RuntimeException{
    public BadRequestException (){
        super();
    }
    public BadRequestException(String message) {
        super(message);
    }
}
