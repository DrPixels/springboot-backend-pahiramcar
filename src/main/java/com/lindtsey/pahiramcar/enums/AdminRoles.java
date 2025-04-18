package com.lindtsey.pahiramcar.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum AdminRoles {
    @JsonProperty("Super Admin")
    SUPER_ADMIN,

    @JsonProperty("Admin")
    ADMIN
}
