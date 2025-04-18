package com.lindtsey.pahiramcar.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum FuelType {
    @JsonProperty("Gasoline")
    GASOLINE,

    @JsonProperty("Diesel")
    DIESEL
}
