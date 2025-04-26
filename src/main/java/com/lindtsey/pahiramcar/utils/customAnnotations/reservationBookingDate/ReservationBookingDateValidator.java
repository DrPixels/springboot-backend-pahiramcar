package com.lindtsey.pahiramcar.utils.customAnnotations.reservationBookingDate;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ReservationBookingDateValidator implements ConstraintValidator<ReservationBookingDateConstraint, Object> {

    @Override
    public void initialize(ReservationBookingDateConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {

        try {
            var clazz = value.getClass();

            var start = (LocalDateTime) clazz.getDeclaredField("startDateTime").get(value);
            var end = (LocalDateTime) clazz.getDeclaredField("endDateTime").get(value);

            // Validation logic
            return end.isAfter(start);
        } catch (Exception e) {
            return false;
        }
    }

}
