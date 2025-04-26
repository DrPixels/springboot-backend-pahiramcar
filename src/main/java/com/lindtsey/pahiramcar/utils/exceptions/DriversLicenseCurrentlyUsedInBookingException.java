package com.lindtsey.pahiramcar.utils.exceptions;

public class DriversLicenseCurrentlyUsedInBookingException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "The driver's license number is being used already from someone's booking. Use a different license.";

    public DriversLicenseCurrentlyUsedInBookingException() {
        super(DEFAULT_MESSAGE);
    }

    public DriversLicenseCurrentlyUsedInBookingException(String message) {
        super(message);
    }
}
