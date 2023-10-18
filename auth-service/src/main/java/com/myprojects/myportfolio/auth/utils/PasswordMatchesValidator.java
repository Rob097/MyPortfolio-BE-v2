package com.myprojects.myportfolio.auth.utils;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import com.myprojects.myportfolio.auth.dto.SignUPRequest;

public class PasswordMatchesValidator implements ConstraintValidator<SignUPRequest.PasswordMatches, Object> {

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context) {
        SignUPRequest user = (SignUPRequest) obj;
        return user.getPassword().equals(user.getMatchingPassword());
    }
}
