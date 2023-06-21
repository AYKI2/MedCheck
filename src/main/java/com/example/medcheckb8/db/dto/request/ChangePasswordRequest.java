package com.example.medcheckb8.db.dto.request;

import com.example.medcheckb8.db.validation.PasswordValid;
import lombok.Builder;

@Builder
public record ChangePasswordRequest(
        String oldPassword,
        @PasswordValid(message = "Неправильный пароль!")
        String newPassword
) {
}
