package com.lindtsey.pahiramcar.reservations;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record ReservationDTO(
        LocalDateTime reservationStartDate,
        LocalDateTime reservationEndDate,
        Integer customerId,
        Integer carId
) {
}
