package com.lindtsey.pahiramcar.utils;

public class ReservationCancelledOrExpiredException extends RuntimeException {
    public ReservationCancelledOrExpiredException(String message) {
        super(message);
    }
}
