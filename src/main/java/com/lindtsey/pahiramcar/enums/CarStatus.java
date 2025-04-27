package com.lindtsey.pahiramcar.enums;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public enum CarStatus {
    @JsonProperty("Available")
    AVAILABLE(1),

    @JsonProperty("Reserved")
    RESERVED(2),

    @JsonProperty("Booked")
    BOOKED(3);

    private final int order;

    CarStatus(int order) {
        this.order = order;
    }
}
