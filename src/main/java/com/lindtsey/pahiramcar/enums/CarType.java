package com.lindtsey.pahiramcar.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum CarType {

    @JsonProperty("SUV")
    SUV,

    @JsonProperty("Sedan")
    SEDAN,

    @JsonProperty("Hatchback")
    HATCHBACK,

    @JsonProperty("Luxury")
    LUXURY,

    @JsonProperty("Sports")
    SPORTS
}
