package com.lindtsey.pahiramcar.reservations;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.lindtsey.pahiramcar.car.Car;
import com.lindtsey.pahiramcar.customer.Customer;
import com.lindtsey.pahiramcar.enums.ReservationStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
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
    private Car car;

    private LocalDateTime reservationStartDate;
    private LocalDateTime reservationEndDate;
    private ReservationStatus status = ReservationStatus.WAITING_FOR_APPROVAL;
}
