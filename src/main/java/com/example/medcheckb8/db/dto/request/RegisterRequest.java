package com.example.medcheckb8.db.dto.request;

import com.example.medcheckb8.db.validation.PasswordValid;
import com.example.medcheckb8.db.validation.PhoneNumberValid;
import jakarta.validation.constraints.*;
import lombok.Builder;

@Builder
public record RegisterRequest(
        @Size(min = 2, max = 30, message = "Имя должно содержать от 2 до 30 символов.")
        @Pattern(regexp = "^[а-яА-ЯёЁa-zA-Z]+(([',. -][а-яА-ЯёЁa-zA-Z ])?[а-яА-ЯёЁa-zA-Z])$", message = "Имя должно содержать только буквы.")
        String firstName,
        @Size(min = 2, max = 30, message = "Фамилия должна содержать от 2 до 30 символов.")
        @Pattern(regexp = "^[а-яА-ЯёЁa-zA-Z]+(([',. -][а-яА-ЯёЁa-zA-Z ])?[а-яА-ЯёЁa-zA-Z])$", message = "Фамилия должна содержать только буквы.")
        String lastName,
        @PhoneNumberValid(message = "Неверный номер телефона!")
        String phoneNumber,
        @Email(message = "Неверный формат email!")
        String email,
        @PasswordValid(message = "Неправильный пароль")
        String password
) {
}
