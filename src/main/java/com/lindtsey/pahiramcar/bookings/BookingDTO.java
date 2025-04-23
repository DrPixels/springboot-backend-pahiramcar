package com.lindtsey.pahiramcar.bookings;

import java.time.LocalDateTime;

public record BookingDTO(
        String renterFullName,
        String driverLicenseNumber,
        LocalDateTime startDateTime,
        LocalDateTime endDateTime,
        Integer customerId,
        Integer carId,
        Integer reservationId
) {
}
