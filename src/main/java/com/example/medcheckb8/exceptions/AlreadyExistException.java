package com.example.medcheckb8.exceptions;

public class AlreadyExistException  extends RuntimeException{
        public AlreadyExistException (){
                super();
        }
        public AlreadyExistException (String message){
            super(message);
        }

}
