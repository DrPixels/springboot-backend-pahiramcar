package com.lindtsey.pahiramcar.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum TransactionType {

    @JsonProperty("Booking Payment")
    BOOKING_PAYMENT,

    @JsonProperty("Late Return Fee")
    LATE_RETURN_FEE,

    @JsonProperty("Damage Repair Fee")
    DAMAGE_REPAIR_FEE
}
