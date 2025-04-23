package com.lindtsey.pahiramcar.car;

import com.lindtsey.pahiramcar.enums.CarStatus;
import com.lindtsey.pahiramcar.enums.CarType;
import com.lindtsey.pahiramcar.enums.FuelType;
import com.lindtsey.pahiramcar.enums.TransmissionType;

public record CarDTO(String name,
        Integer year,
        String plateNumber,
        CarType carType,
        TransmissionType transmissionType,
        FuelType fuelType,
        Integer seats,
        Double pricePerDay,
        String description) {
}
