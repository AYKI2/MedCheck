package com.example.medcheckb8.db.dto.request.appointment;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record FreeSpecialistRequest(
        @NotEmpty(message = "Отдел не может быть пустым!")
        String department,

        @NotNull(message = "Часовой пояс не может быть пустым!")
        String timeZone
) {
}
