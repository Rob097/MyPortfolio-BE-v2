package com.myprojects.myportfolio.dto;

import com.myprojects.myportfolio.utils.EmailValidator;
import com.myprojects.myportfolio.utils.PasswordMatchesValidator;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Data
@NoArgsConstructor
@SignUPRequest.PasswordMatches
public class SignUPRequest {

    @NotNull(message = "Il nome è un campo obbligatorio")
    @NotEmpty(message = "Il nome non può essere vuoto")
    private String firstName;

    @NotNull(message = "Il cognnome è un campo obbligatorio")
    @NotEmpty(message = "Il cognnome non può essere vuoto")
    private String lastName;

    @NotNull(message = "La password è un campo obbligatorio")
    @NotEmpty(message = "La password non può essere vuota")
    private String password;
    private String matchingPassword;

    @ValidEmail
    @NotNull(message = "L'email è un campo obbligatorio")
    @NotEmpty(message = "L'email non può essere vuota")
    private String email;

    @Target({TYPE, FIELD, ANNOTATION_TYPE})
    @Retention(RUNTIME)
    @Constraint(validatedBy = EmailValidator.class)
    @Documented
    public @interface ValidEmail {
        String message() default "L'email non è valida.";
        Class<?>[] groups() default {};
        Class<? extends Payload>[] payload() default {};
    }

    @Target({TYPE,ANNOTATION_TYPE})
    @Retention(RUNTIME)
    @Constraint(validatedBy = PasswordMatchesValidator.class)
    @Documented
    public @interface PasswordMatches {
        String message() default "Le passwords non corrispondono.";
        Class<?>[] groups() default {};
        Class<? extends Payload>[] payload() default {};
    }

}
