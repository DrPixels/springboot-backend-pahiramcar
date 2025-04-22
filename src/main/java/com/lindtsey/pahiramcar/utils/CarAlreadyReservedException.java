package com.lindtsey.pahiramcar.utils;

public class CarAlreadyReservedException extends RuntimeException {
    public CarAlreadyReservedException(String message) {
        super(message);
    }
}
