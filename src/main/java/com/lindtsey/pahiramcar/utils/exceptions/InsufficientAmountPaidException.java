package com.lindtsey.pahiramcar.utils.exceptions;

public class InsufficientAmountPaidException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "The amount paid is insufficient.";

    public InsufficientAmountPaidException() {
        super(DEFAULT_MESSAGE);
    }

    public InsufficientAmountPaidException(String message) {
        super(message);
    }
}
