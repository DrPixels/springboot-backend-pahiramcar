package com.lindtsey.pahiramcar.reservations;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.lindtsey.pahiramcar.car.Car;
import com.lindtsey.pahiramcar.customer.Customer;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "Reservations")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer reservationId;

    @ManyToOne
    @JoinColumn(
            name = "customer_id"
    )
    @JsonBackReference
    private Customer customer;

    @OneToOne
    @JoinColumn(
            name = "car_id"
    )
    @JsonBackReference
    private Car car;

    private LocalDate reservationStartDate;
    private LocalDate reservationEndDate;
    private String status;

    public Reservation() {}

    public Reservation(LocalDate reservationStartDate, LocalDate reservationEndDate, String status) {
        this.reservationStartDate = reservationStartDate;
        this.reservationEndDate = reservationEndDate;
        this.status = status;
    }

    public Integer getReservationId() {
        return reservationId;
    }

    public void setReservationId(Integer reservationId) {
        this.reservationId = reservationId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public LocalDate getReservationStartDate() {
        return reservationStartDate;
    }

    public void setReservationStartDate(LocalDate reservationStartDate) {
        this.reservationStartDate = reservationStartDate;
    }

    public LocalDate getReservationEndDate() {
        return reservationEndDate;
    }

    public void setReservationEndDate(LocalDate reservationEndDate) {
        this.reservationEndDate = reservationEndDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
