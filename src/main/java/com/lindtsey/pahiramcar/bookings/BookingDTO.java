package com.lindtsey.pahiramcar.bookings;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDateTime;

public record BookingDTO(
        @NotBlank(message = "Renter's Full name is required.")
        String renterFullName,

        @NotBlank(message = "Driver's License Number is required.")
        String driverLicenseNumber,

        @NotEmpty(message = "Start date time of the booking is required.")
        LocalDateTime startDateTime,

        @NotEmpty(message = "End date time of the booking is required.")
        LocalDateTime endDateTime,

        @NotBlank( message = "Customer ID related to booking is required.")
        Integer customerId,

        @NotBlank( message = "Car ID related to booking is required.")
        Integer carId,

        @NotBlank( message = "Reservation ID related to booking is required.")
        Integer reservationId
) {
}
