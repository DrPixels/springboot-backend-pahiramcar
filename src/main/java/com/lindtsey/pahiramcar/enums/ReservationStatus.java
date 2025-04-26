package com.lindtsey.pahiramcar.enums;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public enum ReservationStatus {

    @JsonProperty("Available")
    WAITING_FOR_APPROVAL(1),

    @JsonProperty("Booked")
    BOOKED(2),

    @JsonProperty("Cancelled")
    CANCELLED(3),

    @JsonProperty("Expired")
    EXPIRED(4);

    private final int order;

    ReservationStatus(int order) {
        this.order = order;
    }

}
