package com.lindtsey.pahiramcar.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum MaritalStatus {

    @JsonProperty("Single")
    SINGLE,

    @JsonProperty("Married")
    MARRIED,

    @JsonProperty("Widowed")
    WIDOWED,

    @JsonProperty("Seperated")
    SEPARATED,

    @JsonProperty("Annulled")
    ANNULLED
}
