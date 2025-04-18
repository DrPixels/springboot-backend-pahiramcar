package com.lindtsey.pahiramcar.car;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.lindtsey.pahiramcar.carimages.CarImage;
import com.lindtsey.pahiramcar.enums.CarStatus;
import com.lindtsey.pahiramcar.enums.CarType;
import com.lindtsey.pahiramcar.enums.FuelType;
import com.lindtsey.pahiramcar.enums.TransmissionType;
import com.lindtsey.pahiramcar.reservations.Reservation;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

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

    public Car() {}

    @OneToOne(
            mappedBy = "car"
    )
    @JsonManagedReference
    private Reservation reservation;

    @OneToMany(
            mappedBy = "car",
            cascade = CascadeType.ALL
    )
    @JsonManagedReference
    private List<CarImage> carImages = new ArrayList<>();

    public Integer getCarId() {
        return carId;
    }

    public void setCarId(Integer carId) {
        this.carId = carId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public CarType getCarType() {
        return carType;
    }

    public void setCarType(CarType carType) {
        this.carType = carType;
    }

    public TransmissionType getTransmissionType() {
        return transmissionType;
    }

    public void setTransmissionType(TransmissionType transmissionType) {
        this.transmissionType = transmissionType;
    }

    public FuelType getFuelType() {
        return fuelType;
    }

    public void setFuelType(FuelType fuelType) {
        this.fuelType = fuelType;
    }

    public Integer getSeats() {
        return seats;
    }

    public void setSeats(Integer seats) {
        this.seats = seats;
    }

    public Double getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(Double pricePerDay) {
        this.pricePerDay = pricePerDay;
    }

    public CarStatus getStatus() {
        return status;
    }

    public void setStatus(CarStatus status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public List<CarImage> getCarImages() {
        return carImages;
    }

    public void setCarImages(List<CarImage> carImages) {
        this.carImages = carImages;
    }
}
