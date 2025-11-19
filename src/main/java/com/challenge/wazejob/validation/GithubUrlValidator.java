package com.challenge.wazejob.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class GithubUrlValidator implements ConstraintValidator<GithubUrl, String> {

    private static final Pattern PATTERN = Pattern.compile(
            "^(https://)?github\\.com/[A-Za-z0-9-]{1,39}$"
    );

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) {
            return true;
        }
        String normalized = value.trim();
        return PATTERN.matcher(normalized).matches();
    }
}

