package com.lindtsey.pahiramcar.utils.exceptionHandlers;


import com.lindtsey.pahiramcar.utils.exceptions.CarAlreadyReservedException;
import com.lindtsey.pahiramcar.utils.exceptions.DriversLicenseCurrentlyUsedInBookingException;
import com.lindtsey.pahiramcar.utils.exceptions.ReservationCancelledOrExpiredException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class GlobalRestExceptionHandler {

    @ExceptionHandler(CarAlreadyReservedException.class)
    public ResponseEntity<?> handleCarAlreadyReserved(CarAlreadyReservedException ex) {

        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(DriversLicenseCurrentlyUsedInBookingException.class)
    public ResponseEntity<?> handleDriversLicenseCurrentlyUsedInBooking(DriversLicenseCurrentlyUsedInBookingException ex) {

        Map<String, String> response = new HashMap<>();
        response.put("status", "true");
        response.put("message", ex.getMessage());
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(ReservationCancelledOrExpiredException.class)
    public ResponseEntity<?> handleReservationCancelledOrExpiredException(ReservationCancelledOrExpiredException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

}
