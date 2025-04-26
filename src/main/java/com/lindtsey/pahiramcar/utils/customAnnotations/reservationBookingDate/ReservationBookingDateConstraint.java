package com.lindtsey.pahiramcar.utils.customAnnotations.reservationBookingDate;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ReservationBookingDateValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ReservationBookingDateConstraint {
    String message() default "Reservation start date cannot be after reservation end date!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
