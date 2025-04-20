package com.lindtsey.pahiramcar.employee;

import java.time.LocalDate;

public record EmployeeDTO(
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
