package com.lindtsey.pahiramcar.utils.exceptionHandlers;


import com.lindtsey.pahiramcar.utils.exceptions.*;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
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

    @ExceptionHandler(CarHasBookingCannotDeleteException.class)
    public ResponseEntity<?> handleCarHasBookingCannotDelete(CarHasBookingCannotDeleteException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(PasswordDontMatchException.class)
    public ResponseEntity<?> handlePasswordDontMatch(PasswordDontMatchException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(CarMustHaveAtLeastOneImage.class)
    public ResponseEntity<?> handleCarMustHaveAtLeastOneImage(CarMustHaveAtLeastOneImage ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    //For exception handling from validation
    public ResponseEntity<?> handleConstraintViolationException(ConstraintViolationException ex) {

        var errors = new HashMap<String, String>();

        ex.getConstraintViolations().forEach(constraintViolation -> {
            var fieldName = constraintViolation.getPropertyPath().toString();
            var errorMessage = constraintViolation.getMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

}
