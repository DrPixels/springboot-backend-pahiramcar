package com.lindtsey.pahiramcar.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum PaymentMode {

    @JsonProperty("Cash")
    CASH,

    @JsonProperty("Online")
    ONLINE
}
