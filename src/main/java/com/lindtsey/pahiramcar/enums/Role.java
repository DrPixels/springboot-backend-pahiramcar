package com.lindtsey.pahiramcar.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum Role {

    @JsonProperty("User")
    USER,

    @JsonProperty("Admin")
    ADMIN,

    @JsonProperty("Employee")
    EMPLOYEE
}
