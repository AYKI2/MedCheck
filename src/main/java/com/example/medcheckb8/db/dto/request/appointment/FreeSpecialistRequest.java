package com.example.medcheckb8.db.dto.request.appointment;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record FreeSpecialistRequest(
        @NotEmpty(message = "Department cannot be empty!")
        String department,

        @NotNull(message = "The timezone must not be empty!")
        String timeZone
) {
}
