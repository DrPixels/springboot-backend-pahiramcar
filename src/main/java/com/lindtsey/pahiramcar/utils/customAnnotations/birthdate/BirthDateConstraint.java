package com.lindtsey.pahiramcar.utils.customAnnotations.birthdate;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = BirthDateValidator.class)
@Target({ ElementType.FIELD, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface BirthDateConstraint {
    int value() default 18;
    String message() default "User must be at least {value} years old!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
