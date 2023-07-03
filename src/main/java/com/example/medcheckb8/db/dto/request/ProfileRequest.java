package com.example.medcheckb8.db.dto.request;

import com.example.medcheckb8.db.validation.PhoneNumberValid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record ProfileRequest(
        @Size(min = 2, max = 30, message = "Имя должно содержать от 2 до 30 символов.")
        @NotBlank(message = "Имя не может быть пустым!")
        @NotNull(message = "Имя не может быть пустым!")
        String firstName,
        @Size(min = 2, max = 30, message = "Фамилия должна содержать от 2 до 30 символов.")
        @NotBlank(message = "Фамилия не может быть пустым!")
        @NotNull(message = "Фамилия не может быть пустым!")
        String lastName,
        @NotBlank(message = "Номер телефона не может быть пустым!")
        @NotNull(message = "Номер телефона не может быть пустым!")
        @PhoneNumberValid(message = "Неверный номер телефона!")
        String phoneNumber,
        @NotBlank(message = "Email не может быть пустым!")
        @NotNull(message = "Email не может быть пустым!")
        @Email(message = "Неверный формат email!")
        String email) {
}
