package com.lindtsey.pahiramcar.enums;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public enum BookingStatus {
    @JsonProperty("Ongoing")
    ONGOING(1),

    @JsonProperty("Due Already")
    DUE_ALREADY(2),

    @JsonProperty("Completed")
    COMPLETED(3);

    private final int order;

    BookingStatus(int order) {
        this.order = order;
    }

}
