package com.lindtsey.pahiramcar.carimages;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.lindtsey.pahiramcar.car.Car;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "CarImages")
public class CarImage {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer carImageId;

    @ManyToOne
    @JoinColumn(
            name = "car_id"
    )
    @JsonBackReference
    private Car car;

    private String imageName;
    private String imageUrl;
    private String cloudinaryImageId;

    public CarImage(String imageName, String imageUrl, String cloudinaryImageId) {
        this.imageName = imageName;
        this.imageUrl = imageUrl;
        this.cloudinaryImageId = cloudinaryImageId;
    }
}
