package com.lindtsey.pahiramcar.utils.exceptions;

public class CarHasBookingCannotDeleteException extends RuntimeException{

    private static final String DEFAULT_MESSAGE = "The car can't be delete since it has an active booking.";

    public CarHasBookingCannotDeleteException() {
        super(DEFAULT_MESSAGE);
    }

    public CarHasBookingCannotDeleteException(String message) {
        super(message);
    }
}
