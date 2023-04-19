package com.example.medcheckb8.db.dto.response;

import lombok.Builder;
import org.springframework.http.HttpStatus;

@Builder
public record ExceptionResponse(HttpStatus status,
                                String message,
                                String exceptionClassName) {
}
