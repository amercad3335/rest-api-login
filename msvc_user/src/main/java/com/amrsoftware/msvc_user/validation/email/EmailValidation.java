package com.amrsoftware.msvc_user.validation.email;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Objects;
import java.util.regex.Pattern;

public class EmailValidation implements ConstraintValidator<EmailValid, String> {
    private static final String EMAIL_PATTER = "[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})";
    private Pattern pattern;
    @Override
    public void initialize(EmailValid constraintAnnotation) {
        pattern = Pattern.compile(EMAIL_PATTER);
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        if (Objects.isNull(email)) return false;

        return pattern.matcher(email).matches();
    }
}
