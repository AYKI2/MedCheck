package com.example.medcheckb8.db.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record DoctorUpdateRequest(
        Long doctorId,
        Long departmentId,
        @Size(min = 2, max = 30, message = "Имя должно содержать от 2 до 30 символов.")
        @NotBlank(message = "Имя не может быть пустым!")
        @NotNull(message = "Имя не может быть пустым!")
        String firstName,
        @Size(min = 2, max = 30, message = "Фамилия должна содержать от 2 до 30 символов.")
        @NotBlank(message = "Фамилия не может быть пустой!")
        @NotNull(message = "Фамилия не может быть пустой!")
        String lastName,
        @NotBlank(message = "Название должности не может быть пустым!")
        @NotNull(message = "Название должности не может быть пустым!")
        String position,
        @NotBlank(message = "Изображение не может быть пустым!")
        @NotNull(message = "Изображение не может быть пустым!")
        String image,
        @NotBlank(message = "Описание не может быть пустым!")
        @NotNull(message = "Описание не может быть пустым!")
        String description
) {
}
