package com.lindtsey.pahiramcar.utils.customAnnotations.birthdate;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.Period;

public class BirthDateValidator implements ConstraintValidator<BirthDateConstraint, LocalDate> {

    private int minimumAge;

    @Override
    public void initialize(BirthDateConstraint constraintAnnotation) {
        this.minimumAge = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(LocalDate birthDate, ConstraintValidatorContext context) {

        if (birthDate == null) {
            return true; // Let @NotNull handle null values
        }

        LocalDate currentDate = LocalDate.now();
        int age = Period.between(birthDate, currentDate).getYears();

        return age >= minimumAge;
    }
}
