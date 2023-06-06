package com.example.medcheckb8.db.dto.request;

import com.example.medcheckb8.db.validation.PasswordValid;
import lombok.Builder;

@Builder
public record ChangePasswordRequest(
        String oldPassword,
        @PasswordValid(message = """
                Пароль должен содержать хотя бы один из следующих символов: '@', '#', '$', '%'.
                Пароль должен содержать хотя бы одну букву верхнего регистра.
                Пароль должен содержать хотя бы одну цифру от 0 до 9.
                Пароль должен быть длиной не менее 6 и не более 20 символов.
                """)
        String newPassword
) {
}
