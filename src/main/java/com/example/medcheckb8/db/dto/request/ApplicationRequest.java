package com.example.medcheckb8.db.dto.request;

import com.example.medcheckb8.db.validation.PhoneNumberValid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record ApplicationRequest(
        @Size(min = 2, max = 30, message = "Имя должно содержать от 2 до 30 символов.")
        @NotBlank(message = "Имя не может быть пустым!")
        @NotNull(message = "Имя не может быть пустым!")
        String name,
        @NotBlank(message = "Номер телефона не может быть пустым!")
        @NotNull(message = "Номер телефона не может быть пустым!")
        @PhoneNumberValid(message = "Неверный номер телефона!")
        String phoneNumber) {
}
