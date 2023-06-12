package com.example.medcheckb8.db.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<PasswordValid, String> {
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        boolean isValid = true;
        if (s == null || s.isBlank()) {
            System.out.println("Пароль не может быть пустым!");
            return false;
        }
        if (s.length() > 20 || s.length() < 6) {
            System.out.println("Пароль должен содержать от 6 до 20 символов.");
            isValid = false;
        }
        String upperCaseChars = "(.*[A-Z].*)";
        if (!s.matches(upperCaseChars)) {
            System.out.println("Пароль должен содержать хотя бы одну заглавную букву");
            isValid = false;
        }
        String lowerCaseChars = "(.*[a-z].*)";
        if (!s.matches(lowerCaseChars)) {
            System.out.println("Пароль должен содержать хотя бы одну строчную букву");
            isValid = false;
        }
        String numbers = "(.*[0-9].*)";
        if (!s.matches(numbers)) {
            System.out.println("Пароль должен содержать хотя бы одну цифру");
            isValid = false;
        }
        String specialChars = "(.*[@#$%].*$)";
        if (!s.matches(specialChars)) {
            System.out.println("Пароль должен содержать хотя бы один специальный символ из @#$%");
            isValid = false;
        }
        return isValid;
    }
}
