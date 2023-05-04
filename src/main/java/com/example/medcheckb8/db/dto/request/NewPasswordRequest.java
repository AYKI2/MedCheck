package com.example.medcheckb8.db.dto.request;

import com.example.medcheckb8.db.validation.PasswordValid;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record NewPasswordRequest(
        @PasswordValid(message = "Invalid password!")
        @NotBlank(message = "Password cannot be empty!")
        String newPassword,
        String token
) {
}
