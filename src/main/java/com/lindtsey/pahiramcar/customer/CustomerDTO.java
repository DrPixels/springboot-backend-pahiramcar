package com.lindtsey.pahiramcar.customer;


import com.lindtsey.pahiramcar.enums.MaritalStatus;
import com.lindtsey.pahiramcar.utils.customAnnotations.birthdate.BirthDateConstraint;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record CustomerDTO(
        @NotBlank(message = "Username is required.")
        String username,

        @NotBlank(message = "Password is required.")
        @Size(min = 8, max = 20, message = "Password length must be between 8 and 20 characters.") String password,

        @NotBlank(message = "First name is required.")
        String firstName,

        String middleName,

        @NotBlank(message = "Last name is required.")
        String lastName,

        @NotNull(message = "Birthdate is required.")
        @BirthDateConstraint
        LocalDate birthDate,

        @NotBlank
        @Pattern(regexp = "^09\\d{9}$", message = "Phone number must start with 09 and must be 11 digits long.")
        String mobilePhone,

        @NotBlank
        @Pattern(regexp= "^[a-z][a-z0-9]*@[a-z]+\\.com$", message = "Invalid Email Format")
        String email,

        @NotNull(message = "Marital Status is required.")
        MaritalStatus maritalStatus,

        @NotBlank(message = "Nationality is required.")
        String nationality
) {
}
