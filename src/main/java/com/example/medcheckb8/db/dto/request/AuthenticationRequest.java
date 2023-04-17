package com.example.medcheckb8.db.dto.request;

import jakarta.validation.constraints.*;
import lombok.Builder;
@Builder
public record AuthenticationRequest(
        @NotBlank(message = "Email cannot be empty!")
        @NotEmpty(message = "Email cannot be empty!")
        String email,
        @NotBlank(message = "Password cannot be empty!")
        @NotEmpty(message = "Email cannot be empty!")
        String password
) {
}
