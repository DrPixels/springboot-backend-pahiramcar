package com.lindtsey.pahiramcar.reservations;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record ReservationDTO(
        LocalDateTime reservationStartDate,
        Integer customerId,
        Integer carId
) {
}
