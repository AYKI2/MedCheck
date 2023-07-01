package com.example.medcheckb8.db.exceptions.handler;

import com.example.medcheckb8.db.exceptions.*;
import com.example.medcheckb8.db.dto.response.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NotFountException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionResponse handlerNotFound(NotFountException e) {
        return new ExceptionResponse(HttpStatus.NOT_FOUND,
                e.getMessage(), NotFountException.class.getSimpleName());
    }

    @ExceptionHandler(AlreadyExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ExceptionResponse handlerAlreadyExist(AlreadyExistException e) {
        return new ExceptionResponse(HttpStatus.CONFLICT,
                e.getMessage(),
                AlreadyExistException.class.getSimpleName());
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handlerBadRequest(BadRequestException e) {
        return new ExceptionResponse(HttpStatus.BAD_REQUEST,
                e.getMessage(),
                BadRequestException.class.getSimpleName());
    }

    @ExceptionHandler(BadCredentialException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ExceptionResponse handlerBadCredential(BadCredentialException e) {
        return new ExceptionResponse(
                HttpStatus.FORBIDDEN,
                e.getMessage(),
                BadCredentialException.class.getSimpleName());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handlerMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return new ExceptionResponse(
                HttpStatus.BAD_REQUEST,
                e.getMessage(),
                MethodArgumentNotValidException.class.getSimpleName()
        );
    }
    @ExceptionHandler(DownloadFailedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionResponse downloadFailedException(DownloadFailedException e) {
        return new ExceptionResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                e.getMessage(),
                DownloadFailedException.class.getSimpleName());
    }
}
