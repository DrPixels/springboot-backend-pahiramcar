package com.lindtsey.pahiramcar.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum CarStatus {
    @JsonProperty("Available")
    AVAILABLE,

    @JsonProperty("Reserved")
    RESERVED,

    @JsonProperty("Rented")
    RENTED
}
