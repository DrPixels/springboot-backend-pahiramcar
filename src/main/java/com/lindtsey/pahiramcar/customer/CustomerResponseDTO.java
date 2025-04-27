package com.lindtsey.pahiramcar.customer;

import com.lindtsey.pahiramcar.enums.MaritalStatus;
import com.lindtsey.pahiramcar.images.Image;
import com.lindtsey.pahiramcar.utils.customAnnotations.birthdate.BirthDateConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record CustomerResponseDTO(
        Integer customerId,

        String username,

        String firstName,

        String middleName,

        String lastName,

        LocalDate birthDate,

        String mobilePhone,

        String email,

        MaritalStatus maritalStatus,

        String nationality,

        Image customerImage
) {

}
