package com.lindtsey.pahiramcar.utils.exceptions;

public class CarAlreadyReservedException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "The car is already reserved for the selected dates!";

    public CarAlreadyReservedException() {
        super(DEFAULT_MESSAGE);
    }

    public CarAlreadyReservedException(String message) {
        super(message);
    }
}
