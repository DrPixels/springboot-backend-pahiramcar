package com.lindtsey.pahiramcar.reservations;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record ReservationDTO(
        @NotBlank(message = "Reservation start date is required.")
        LocalDateTime startDateTime,

        @NotBlank(message = "Customer ID related to Reservation is required.")
        Integer customerId,

        @NotBlank(message = "Car ID related to Reservation is required.")
        Integer carId
) {
}
