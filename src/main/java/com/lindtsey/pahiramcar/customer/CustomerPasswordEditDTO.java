package com.lindtsey.pahiramcar.customer;


import com.lindtsey.pahiramcar.enums.MaritalStatus;
import com.lindtsey.pahiramcar.utils.customAnnotations.birthdate.BirthDateConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record CustomerPasswordEditDTO(

        @NotBlank(message = "Old password is required.")
        @Size(min = 8, max = 20, message = "Password length must be between 8 and 20 characters.")
        String oldPassword,

        @NotBlank(message = "New password is required.")
        @Size(min = 8, max = 20, message = "Password length must be between 8 and 20 characters.")
        String newPassword
) {
}
