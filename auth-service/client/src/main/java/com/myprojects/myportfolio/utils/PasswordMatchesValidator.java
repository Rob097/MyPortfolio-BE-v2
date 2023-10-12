package com.myprojects.myportfolio.utils;

import com.myprojects.myportfolio.dto.SignUPRequest;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<SignUPRequest.PasswordMatches, Object> {

    @Override
    public void initialize(SignUPRequest.PasswordMatches constraintAnnotation) {
    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context) {
        SignUPRequest user = (SignUPRequest) obj;
        return user.getPassword().equals(user.getMatchingPassword());
    }
}
