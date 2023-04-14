package com.example.medcheckb8.exceptions.handler;

import com.example.medcheckb8.dto.response.ExceptionResponse;
import com.example.medcheckb8.exceptions.AlreadyExistException;
import com.example.medcheckb8.exceptions.BadRequestException;
import com.example.medcheckb8.exceptions.ForbiddenException;
import com.example.medcheckb8.exceptions.NotFountException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionResponse handlerNotFound(NotFountException e) {
        return new ExceptionResponse(HttpStatus.NOT_FOUND,
                e.getMessage(), NotFountException.class.getSimpleName());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ExceptionResponse handlerAlreadyExist(AlreadyExistException e) {
        return new ExceptionResponse(HttpStatus.CONFLICT,
                e.getMessage(),
                AlreadyExistException.class.getSimpleName());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handlerBadRequest(BadRequestException e) {
        return new ExceptionResponse(HttpStatus.BAD_REQUEST,
                e.getMessage(),
                BadRequestException.class.getSimpleName());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ExceptionResponse handlerForbidden(ForbiddenException e) {
        return new ExceptionResponse(HttpStatus.FORBIDDEN,
                e.getMessage(),
                ForbiddenException.class.getSimpleName());
    }

}
