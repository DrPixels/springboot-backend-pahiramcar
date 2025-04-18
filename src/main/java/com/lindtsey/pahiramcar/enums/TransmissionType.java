package com.lindtsey.pahiramcar.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum TransmissionType {

    @JsonProperty("Automatic")
    AUTOMATIC,

    @JsonProperty("Manual")
    MANUAL
}
