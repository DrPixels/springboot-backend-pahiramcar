package com.lindtsey.pahiramcar.car;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.lindtsey.pahiramcar.enums.CarStatus;
import com.lindtsey.pahiramcar.enums.CarType;
import com.lindtsey.pahiramcar.enums.FuelType;
import com.lindtsey.pahiramcar.enums.TransmissionType;
import com.lindtsey.pahiramcar.images.Image;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

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

    @NotBlank(message = "Car name is required.")
    @Column(nullable = false)
    private String name;

    @NotEmpty(message = "Car year is required.")
    @Column(nullable = false)
    private Integer year;

    @NotBlank(message = "Plate number is required.")
    @Column(nullable = false, unique = true)
    private String plateNumber;

    @NotEmpty(message = "Car type is required.")
    @Column(nullable = false)
    private CarType carType;

    @NotEmpty(message = "Car transmission type is required.")
    @Column(nullable = false)
    private TransmissionType transmissionType;

    @NotEmpty(message = "Car fuel type is required.")
    @Column(nullable = false)
    private FuelType fuelType;

    @NotEmpty(message = "Car seats is required.")
    @Column(nullable = false)
    @Range(min = 0)
    private Integer seats;

    @NotEmpty(message = "Rent price per day is required.")
    @Column(nullable = false)
    @Range(min = 0)
    private Double pricePerDay;

    @NotEmpty(message = "Car status is required.")
    @Column(nullable = false)
    private CarStatus status = CarStatus.AVAILABLE;

    private String description;

    @OneToMany(
            mappedBy = "car",
            cascade = CascadeType.ALL
    )
    @JsonManagedReference
    private List<Image> carImages = new ArrayList<>();

}
