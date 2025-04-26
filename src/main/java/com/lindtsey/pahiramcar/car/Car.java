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

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Range(min = 0)
    private Integer year;

    @Column(nullable = false, unique = true)
    private String plateNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CarType carType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransmissionType transmissionType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FuelType fuelType;

    @Column(nullable = false)
    private String engineNumber;

    @Column(nullable = false)
    private String chassisNumber;

    @Column(nullable = false)
    private Integer seats;

    @Column(nullable = false)
    private double pricePerDay;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CarStatus status = CarStatus.AVAILABLE;

    @Column(nullable = false)
    private String description;

    @OneToMany(
            mappedBy = "car",
            cascade = CascadeType.ALL
    )
    @JsonManagedReference
    private List<Image> carImages = new ArrayList<>();

}
