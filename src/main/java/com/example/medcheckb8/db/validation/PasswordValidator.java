package com.example.medcheckb8.db.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<PasswordValid, String> {
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        boolean isValid = true;
        if(s == null || s.isBlank()){
            System.out.println("Password cannot be empty!");
            return false;
        }
        if (s.length() > 20 || s.length() < 6)
        {
            System.out.println("Password must be less than 20 and more than 6 characters in length.");
            isValid = false;
        }
        String upperCaseChars = "(.*[A-Z].*)";
        if (!s.matches(upperCaseChars ))
        {
            System.out.println("Password must have at least one uppercase character");
            isValid = false;
        }
        String lowerCaseChars = "(.*[a-z].*)";
        if (!s.matches(lowerCaseChars ))
        {
            System.out.println("Password must have at least one lowercase character");
            isValid = false;
        }
        String numbers = "(.*[0-9].*)";
        if (!s.matches(numbers ))
        {
            System.out.println("Password must have at least one number");
            isValid = false;
        }
        String specialChars = "(.*[@#$%].*$)";
        if (!s.matches(specialChars ))
        {
            System.out.println("Password must have at least one special character among @#$%");
            isValid = false;
        }
        return isValid;
    }
}
