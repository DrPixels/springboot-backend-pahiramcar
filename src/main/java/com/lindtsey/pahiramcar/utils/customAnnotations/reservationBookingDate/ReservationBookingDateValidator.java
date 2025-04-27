package com.lindtsey.pahiramcar.utils.customAnnotations.reservationBookingDate;

import com.lindtsey.pahiramcar.reservations.Reservation;
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

        if (!(value instanceof Reservation)) {
            return false; // Ensure the object is of type Reservation
        }

        Reservation reservation = (Reservation) value;

        System.out.println("DATEEEEEEEEEEEEEE");
        System.out.println("Start Date: " + reservation.getStartDateTime());
        System.out.println("End Date: " + reservation.getEndDateTime());

        // Handle null values
        if (reservation.getStartDateTime() == null || reservation.getEndDateTime() == null) {
            return false;
        }

        // Validation logic
        return reservation.getEndDateTime().isAfter(reservation.getStartDateTime());
    }

}
