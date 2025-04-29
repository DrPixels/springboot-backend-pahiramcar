package com.lindtsey.pahiramcar.car;

import com.lindtsey.pahiramcar.enums.CarStatus;
import com.lindtsey.pahiramcar.enums.CarType;
import com.lindtsey.pahiramcar.enums.FuelType;
import com.lindtsey.pahiramcar.enums.TransmissionType;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Range;

public record CarDTO(
        @NotBlank(message = "Car name is required.")
        String name,

        @NotNull(message = "Car year is required.")
        Integer year,

        @NotBlank(message = "Plate number is required.")
        String plateNumber,

        @NotNull(message = "Car type is required.")
        CarType carType,

        @NotNull(message = "Car mileage is required.")
        Float mileage,

        @NotNull(message = "Car transmission type is required.")
        TransmissionType transmissionType,

        @NotNull(message = "Car fuel type is required.")
        FuelType fuelType,

        @NotBlank(message = "Engine number is required.")
        String engineNumber,

        @NotBlank(message = "Chassis number is required.")
        String chassisNumber,

        @NotNull(message = "Car seats is required.")
        @Range(min = 0) Integer seats,

        @NotNull(message = "Rent price per day is required.")
        @Range(min = 0) Double pricePerDay,

        @NotBlank(message = "Car status is required.")
        String description) {
}
