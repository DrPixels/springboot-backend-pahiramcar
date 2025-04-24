package com.lindtsey.pahiramcar.utils.exceptions;

public class ReservationCancelledOrExpiredException extends RuntimeException {
    public ReservationCancelledOrExpiredException(String message) {
        super(message);
    }
}
