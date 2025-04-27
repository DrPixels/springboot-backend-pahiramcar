package com.lindtsey.pahiramcar.reservations;

import com.lindtsey.pahiramcar.utils.customAnnotations.reservationBookingDate.ReservationBookingDateConstraint;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record ReservationDTO(
        @NotNull(message = "Reservation start date is required.")
        LocalDateTime startDateTime,

        @NotNull(message = "Car ID related to Reservation is required.")
        Integer carId
) {
}
