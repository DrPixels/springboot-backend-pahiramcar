package com.lindtsey.pahiramcar.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum PaymentMode {

    @JsonProperty("Cash")
    CASH,

    @JsonProperty("Debit Card")
    DEBIT_CARD,

    @JsonProperty("Credit Card")
    CREDIT_CARD,
}
