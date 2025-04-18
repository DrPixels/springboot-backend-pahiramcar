package com.lindtsey.pahiramcar.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum BookingStatus {
    @JsonProperty("Ongoing")
    ONGOING,

    @JsonProperty("Due Already")
    DUE_ALREADY,

    @JsonProperty("Completed")
    COMPLETED
}
