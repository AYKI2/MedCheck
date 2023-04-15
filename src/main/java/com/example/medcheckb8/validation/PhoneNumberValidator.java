package com.example.medcheckb8.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PhoneNumberValidator implements ConstraintValidator<PhoneNumberValid,String> {
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return s.startsWith("+996") && s.length() == 13 && s.matches("(\\\\\\\\+\\\\\\\\d{3}( )?)?((\\\\\\\\(\\\\\\\\d{3}\\\\\\\\))|\\\\\\\\d{3})[- .]?\\\\\\\\d{2}[- .]?\\\\\\\\d{2}[- .]?\\\\\\\\d{2}$");
    }
}
