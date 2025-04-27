package com.lindtsey.pahiramcar.utils.exceptions;

public class CarMustHaveAtLeastOneImage extends RuntimeException{
    private static final String DEFAULT_MESSAGE = "The last image can not be deleted. There must be at least one image.";

    public CarMustHaveAtLeastOneImage() {
        super(DEFAULT_MESSAGE);
    }

    public CarMustHaveAtLeastOneImage(String message) {
        super(message);
    }
}
