package com.lindtsey.pahiramcar.utils.exceptions;

public class CarAlreadyReservedException extends RuntimeException {
    public CarAlreadyReservedException(String message) {
        super(message);
    }
}
