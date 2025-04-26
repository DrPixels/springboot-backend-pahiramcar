package com.lindtsey.pahiramcar.reservations;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.lindtsey.pahiramcar.car.Car;
import com.lindtsey.pahiramcar.customer.Customer;
import com.lindtsey.pahiramcar.enums.ReservationStatus;
import com.lindtsey.pahiramcar.utils.customAnnotations.reservationBookingDate.ReservationBookingDateConstraint;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "Reservations")
@ReservationBookingDateConstraint
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer reservationId;

    @ManyToOne
    @JoinColumn(
            name = "user_id"
    )
    @JsonBackReference
    private Customer customer;

    @OneToOne
    @JoinColumn(
            name = "car_id"
    )
    private Car car;

    @Column(nullable = false)
    private LocalDateTime startDateTime;

    @NotBlank(message = "Reservation end date is required.")
    @Column(nullable = false)
    private LocalDateTime endDateTime;

    @NotBlank(message = "Reservation status is required.")
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ReservationStatus status = ReservationStatus.WAITING_FOR_APPROVAL;
}
