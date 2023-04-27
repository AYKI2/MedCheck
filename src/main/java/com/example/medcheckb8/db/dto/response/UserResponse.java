package com.example.medcheckb8.db.dto.response;

import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Builder
public record UserResponse(Long id,
                           String firstName,
                           String lastName,
                           String phoneNumber,
                           String email,
                           LocalDateTime dateOfVisit) {
}
