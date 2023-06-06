package com.example.medcheckb8.db.exceptions;

public class DownloadFailedException extends RuntimeException{
    public DownloadFailedException(String message) {
        super(message);
    }
}
