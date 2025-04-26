package com.lindtsey.pahiramcar.utils.exceptions;

public class ReservationCancelledOrExpiredException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "The reservation is not valid. Either it is already cancelled or expired.";


    public ReservationCancelledOrExpiredException() {
        super(DEFAULT_MESSAGE);
    }

    public ReservationCancelledOrExpiredException(String message) {
        super(message);
    }
}
