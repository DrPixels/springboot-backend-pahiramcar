package com.lindtsey.pahiramcar.bookings;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.lindtsey.pahiramcar.car.Car;
import com.lindtsey.pahiramcar.customer.Customer;
import com.lindtsey.pahiramcar.enums.BookingStatus;
import com.lindtsey.pahiramcar.reservations.Reservation;
import com.lindtsey.pahiramcar.images.Image;
import com.lindtsey.pahiramcar.transactions.Transaction;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "Bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer bookingId;

    @OneToOne
    @JoinColumn(
            name = "transaction_id"
    )
    private Transaction transaction;

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

    @OneToOne
    @JoinColumn(
            name = "reservation_id"
    )
    private Reservation reservation;

    @OneToMany(
            mappedBy = "booking"
    )
    private List<Image> bookingProofImages;

    private String driverLicenseNumber;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private float totalAmount;
    private float penalty;
    private BookingStatus status;
}
