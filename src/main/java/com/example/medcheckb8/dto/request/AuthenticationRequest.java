package com.example.medcheckb8.dto.request;

import com.example.medcheckb8.validation.PasswordValid;
import jakarta.validation.constraints.*;
import lombok.Builder;

@Builder
public record AuthenticationRequest(
        @NotBlank(message = "Email cannot be empty!")
        @NotNull(message = "Email cannot be empty!")
        @Email(regexp = "^(?=.{1,32}@)[A-Za-z0-9_-]+(\\\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\\\.[A-Za-z0-9-]+)*(\\\\.[A-Za-z]{2,})$", message = "Not valid email!")
        String email,
        @PasswordValid
        String password
) {
}
