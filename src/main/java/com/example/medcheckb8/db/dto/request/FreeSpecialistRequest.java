package com.example.medcheckb8.db.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Builder
public record FreeSpecialistRequest(
        @NotEmpty(message = "Department cannot be empty!")
        String department,
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        LocalDate localDate
) {
}
