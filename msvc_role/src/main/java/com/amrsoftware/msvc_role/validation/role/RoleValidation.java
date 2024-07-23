package com.amrsoftware.msvc_role.validation.role;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Objects;
import java.util.regex.Pattern;

public class RoleValidation implements ConstraintValidator<RoleValid, String> {
    private static final String ROLE_PATTER = "^ROLE_\\w+$";
    private Pattern pattern;
    @Override
    public void initialize(RoleValid constraintAnnotation) {
        pattern = Pattern.compile(ROLE_PATTER);
    }

    @Override
    public boolean isValid(String role, ConstraintValidatorContext context) {
        if (Objects.isNull(role)) return false;
        return pattern.matcher(role).matches();
    }
}
