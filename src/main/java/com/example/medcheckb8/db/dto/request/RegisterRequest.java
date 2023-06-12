package com.example.medcheckb8.db.dto.request;

import com.example.medcheckb8.db.validation.PasswordValid;
import com.example.medcheckb8.db.validation.PhoneNumberValid;
import jakarta.validation.constraints.*;
import lombok.Builder;

@Builder
public record RegisterRequest(
        @Size(min = 2, max = 30, message = "Имя должно содержать от 2 до 30 символов.")
        @Pattern(regexp = "^[а-яА-ЯёЁa-zA-Z]+(([',. -][а-яА-ЯёЁa-zA-Z ])?[а-яА-ЯёЁa-zA-Z])$", message = "Имя должно содержать только буквы.")
        @NotBlank(message = "Имя не может быть пустым!")
        @NotNull(message = "Имя не может быть пустым!")
        String firstName,
        @Size(min = 2, max = 30, message = "Фамилия должна содержать от 2 до 30 символов.")
        @Pattern(regexp = "^[а-яА-ЯёЁa-zA-Z]+(([',. -][а-яА-ЯёЁa-zA-Z ])?[а-яА-ЯёЁa-zA-Z])$", message = "Фамилия должна содержать только буквы.")
        @NotBlank(message = "Фамилия не может быть пустой!")
        @NotNull(message = "Фамилия не может быть пустой!")
        String lastName,
        @NotBlank(message = "Номер телефона не может быть пустым!")
        @NotNull(message = "Номер телефона не может быть пустым!")
        @PhoneNumberValid(message = "Неверный номер телефона!")
        String phoneNumber,
        @NotBlank(message = "Email не может быть пустым!")
        @NotNull(message = "Email не может быть пустым!")
        @Email(message = "Неверный формат email!")
        String email,
        @PasswordValid(message = """
                Пароль должен содержать хотя бы один из следующих символов: '@', '#', '$', '%'.
                Пароль должен содержать хотя бы одну букву верхнего регистра.
                Пароль должен содержать хотя бы одну цифру от 0 до 9.
                Пароль должен быть длиной не менее 6 и не более 20 символов.
                """)
        String password
) {
}
