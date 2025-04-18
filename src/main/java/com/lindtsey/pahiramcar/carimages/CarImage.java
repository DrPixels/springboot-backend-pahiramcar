package com.lindtsey.pahiramcar.carimages;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.lindtsey.pahiramcar.car.Car;
import jakarta.persistence.*;

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

    public CarImage() {

    }

    public Integer getCarImageId() {
        return carImageId;
    }

    public void setCarImageId(Integer carImageId) {
        this.carImageId = carImageId;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getCloudinaryImageId() {
        return cloudinaryImageId;
    }

    public void setCloudinaryImageId(String cloudinaryImageId) {
        this.cloudinaryImageId = cloudinaryImageId;
    }
}
