package com.challenge.wazejob.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = GithubUrlValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface GithubUrl {
    String message() default "Invalid GitHub profile URL. Use https://github.com/<username>";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

