package com.lindtsey.pahiramcar.bookings;

import com.lindtsey.pahiramcar.enums.BookingStatus;

import java.time.LocalDate;

public record BookingDTO(
        String driverLicenseNumber,
        LocalDate startDate,
        LocalDate endDate,
        Float totalAmount,
        BookingStatus status,
        Integer customerId,
        Integer carId,
        Integer reservationId
) {
}
