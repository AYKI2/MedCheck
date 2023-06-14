package com.example.medcheckb8.db.validation;

import com.example.medcheckb8.db.exceptions.BadRequestException;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<PasswordValid, String> {
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        boolean isValid = true;
        if (s == null || s.isBlank()) {
            throw new BadRequestException("Пароль не может быть пустым!");
        }
        if (s.length() > 20 || s.length() < 6) {
            throw new BadRequestException("Пароль должен содержать от 6 до 20 символов.");
        }
        String upperCaseChars = "(.*[A-Z].*)";
        if (!s.matches(upperCaseChars)) {
            throw new BadRequestException("Пароль должен содержать хотя бы одну заглавную букву");
        }
        String lowerCaseChars = "(.*[a-z].*)";
        if (!s.matches(lowerCaseChars)) {
            throw new BadRequestException("Пароль должен содержать хотя бы одну строчную букву");
        }
        String numbers = "(.*[0-9].*)";
        if (!s.matches(numbers)) {
            throw new BadRequestException("Пароль должен содержать хотя бы одну цифру 0-9");
        }
        String specialChars = "(.*[@#$%].*$)";
        if (!s.matches(specialChars)) {
            throw new BadRequestException("Пароль должен содержать хотя бы один специальный символ из @#$%");
        }
        return isValid;
    }
}
