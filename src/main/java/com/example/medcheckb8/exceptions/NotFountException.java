package com.example.medcheckb8.exceptions;

public class NotFountException extends RuntimeException {
    public NotFountException (){
        super();
    }
    public NotFountException (String message){
        super(message);
    }
}
