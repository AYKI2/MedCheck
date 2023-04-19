package com.example.medcheckb8.db.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
@Builder
public record ApplicationRequest(
        @NotBlank(message = "Name cannot be empty!")
        String name,
        @NotBlank(message = "Phone number cannot be empty!")
        String phoneNumber) {
}
