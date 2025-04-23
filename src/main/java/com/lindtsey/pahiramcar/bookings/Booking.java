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

import java.time.Duration;
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

    @OneToOne
    @JoinColumn(
            name = "reservation_id"
    )
    private Reservation reservation;

    @OneToMany(
            mappedBy = "booking"
    )
    private List<Image> bookingProofImages;

    private String renterFullName;
    private String plateNumber;
    private String driverLicenseNumber;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private LocalDateTime actualReturnDate;
    private double totalAmount;
    private double penalty = 0;
    private BookingStatus status;
    private boolean isOverDue = false;
    private Long overdueDurationInMinutes = 0L;

}
