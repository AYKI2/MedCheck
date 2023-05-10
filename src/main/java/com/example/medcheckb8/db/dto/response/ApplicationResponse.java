package com.example.medcheckb8.db.dto.response;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record ApplicationResponse(Long id,
                                  String name,
                                  LocalDate date,
                                  String phoneNumber,
                                  Boolean processed) {
}
