package com.example.medcheckb8.db.dto.request.appointment;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record InstallByTemplate(
        Long fromId,
        Long toId,
        int interval,
        String startBreak,
        String endBreak,
        LocalDate date
) {
}
