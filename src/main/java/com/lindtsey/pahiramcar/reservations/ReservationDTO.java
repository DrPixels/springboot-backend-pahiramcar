package com.lindtsey.pahiramcar.reservations;

import java.time.LocalDate;

public record ReservationDTO(
        LocalDate reservationStartDate,
        LocalDate reservationEndDate,
        String status,
        Integer customerId,
        Integer carId
) {
}
