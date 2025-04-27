package com.lindtsey.pahiramcar.employee;

import com.lindtsey.pahiramcar.enums.MaritalStatus;
import com.lindtsey.pahiramcar.enums.Role;
import com.lindtsey.pahiramcar.images.Image;

import java.time.LocalDate;

public record EmployeeResponseDTO(
        Role role,

        Integer employeeId,

        String username,

        String firstName,

        String middleName,

        String lastName,

        LocalDate birthDate,

        String mobilePhone,

        String email,

        MaritalStatus maritalStatus,

        String nationality,

        Image employeeImage
) {

}
