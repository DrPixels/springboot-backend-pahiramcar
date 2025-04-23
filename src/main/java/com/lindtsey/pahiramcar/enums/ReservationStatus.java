package com.lindtsey.pahiramcar.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum ReservationStatus {

    @JsonProperty("Available")
    WAITING_FOR_APPROVAL,

    @JsonProperty("Booked")
    BOOKED,

    @JsonProperty("Cancelled")
    CANCELLED,

    @JsonProperty("Expired")
    EXPIRED,

}
