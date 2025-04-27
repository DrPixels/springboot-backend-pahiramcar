package com.lindtsey.pahiramcar.utils.customAnnotations.reservationBookingDate;

import com.lindtsey.pahiramcar.bookings.Booking;
import com.lindtsey.pahiramcar.reservations.Reservation;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ReservationBookingDateValidator implements ConstraintValidator<ReservationBookingDateConstraint, Object> {

    @Override
    public void initialize(ReservationBookingDateConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {

        if (!(value instanceof Reservation) && !(value instanceof Booking)) {
            return false; // Ensure the object is of type Reservation
        }

        if(value instanceof Reservation reservation) {

            // Handle null values
            if (reservation.getStartDateTime() == null || reservation.getEndDateTime() == null) {
                return false;
            }

            // Validation logic
            return reservation.getEndDateTime().isAfter(reservation.getStartDateTime());
        } else {
            Booking booking = (Booking) value;

            if(booking.getStartDateTime() == null || booking.getEndDateTime() == null) {
                return false;
            }

            // Validation logic
            return booking.getEndDateTime().isAfter(booking.getStartDateTime());
        }


    }

}
