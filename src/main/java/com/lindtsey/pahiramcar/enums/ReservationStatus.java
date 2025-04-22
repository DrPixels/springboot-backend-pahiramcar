package com.lindtsey.pahiramcar.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum ReservationStatus {

    @JsonProperty("Available")
    AVAILABLE,

    @JsonProperty("Booked")
    BOOKED,

    @JsonProperty("Cancelled")
    CANCELLED,

    @JsonProperty("Expired")
    EXPIRED,

}
