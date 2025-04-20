package com.lindtsey.pahiramcar.customer;


import java.time.LocalDate;

public record CustomerDTO(
        String username,
        String password,
        String firstName,
        String middleName,
        String lastName,
        LocalDate birthDate,
        String mobilePhone,
        String email
) {
}
