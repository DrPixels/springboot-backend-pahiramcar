package com.lindtsey.pahiramcar.employee;

import com.lindtsey.pahiramcar.enums.AdminRoles;

import java.time.LocalDate;

public record EmployeeDTO(
        AdminRoles role,
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
