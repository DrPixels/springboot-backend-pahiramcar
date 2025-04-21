package com.lindtsey.pahiramcar.car;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.lindtsey.pahiramcar.enums.CarStatus;
import com.lindtsey.pahiramcar.enums.CarType;
import com.lindtsey.pahiramcar.enums.FuelType;
import com.lindtsey.pahiramcar.enums.TransmissionType;
import com.lindtsey.pahiramcar.images.Image;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "Cars")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer carId;

    private String name;
    private Integer year;
    private String plateNumber;
    private CarType carType;
    private TransmissionType transmissionType;
    private FuelType fuelType;
    private Integer seats;
    private Double pricePerDay;
    private CarStatus status;
    private String description;

    @OneToMany(
            mappedBy = "car",
            cascade = CascadeType.ALL
    )
    @JsonManagedReference
    private List<Image> carImages = new ArrayList<>();

}
