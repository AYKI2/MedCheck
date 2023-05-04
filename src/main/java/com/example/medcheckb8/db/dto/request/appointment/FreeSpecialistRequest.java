package com.example.medcheckb8.db.dto.request.appointment;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

import java.time.ZonedDateTime;

@Builder
public record FreeSpecialistRequest(
        @NotEmpty(message = "Department cannot be empty!")
        String department,
        @FutureOrPresent
        ZonedDateTime zonedDateTime
) {
}
