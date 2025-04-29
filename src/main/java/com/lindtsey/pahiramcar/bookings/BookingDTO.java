package com.lindtsey.pahiramcar.bookings;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record BookingDTO(
        @NotNull(message = "Total amount paid is required.")
        Double totalAmountPaid,

        @NotNull(message = "Number of passengers is required.")
        Integer numberOfPassengers,

        @NotBlank(message = "Renter's Full name is required.")
        String renterFullName,

        @NotBlank(message = "Driver's License Number is required.")
        String driverLicenseNumber,

        @NotNull(message = "Start date time of the booking is required.")
        LocalDateTime startDateTime,

        @NotNull(message = "End date time of the booking is required.")
        LocalDateTime endDateTime,

        @NotNull( message = "Reservation ID related to booking is required.")
        Integer reservationId
) {
}
