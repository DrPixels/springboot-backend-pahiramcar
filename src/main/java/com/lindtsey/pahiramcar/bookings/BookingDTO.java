package com.lindtsey.pahiramcar.bookings;

import com.lindtsey.pahiramcar.enums.BookingStatus;

import java.time.LocalDateTime;

public record BookingDTO(
        String driverLicenseNumber,
        LocalDateTime startDate,
        LocalDateTime endDate,
        Float totalAmount,
        BookingStatus status,
        Integer customerId,
        Integer carId,
        Integer reservationId
) {
}
