package com.example.medcheckb8.db.dto.request;

import com.example.medcheckb8.db.validation.PasswordValid;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record NewPasswordRequest(
        @PasswordValid(message = "Неправильный пароль!")
        @NotBlank(message = "Пароль не может быть пустым!")
        String newPassword,
        String token
) {
}
