package com.lindtsey.pahiramcar.utils.exceptions;

public class PasswordDontMatchException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "Old password don't match";

    public PasswordDontMatchException() {
        super(DEFAULT_MESSAGE);
    }

    public PasswordDontMatchException(String message) {
        super(message);
    }
}
