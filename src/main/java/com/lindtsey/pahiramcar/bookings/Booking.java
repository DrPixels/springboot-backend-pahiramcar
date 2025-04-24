package com.lindtsey.pahiramcar.bookings;

import com.lindtsey.pahiramcar.car.Car;
import com.lindtsey.pahiramcar.customer.Customer;
import com.lindtsey.pahiramcar.enums.BookingStatus;
import com.lindtsey.pahiramcar.reservations.Reservation;
import com.lindtsey.pahiramcar.images.Image;
import com.lindtsey.pahiramcar.transactions.Transaction;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

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


    @NotBlank(message = "Renter's Full name is required.")
    @Column(nullable = false)
    private String renterFullName;

    @NotBlank(message = "Driver's License Number is required.")
    @Column(nullable = false)
    private String driverLicenseNumber;

    @NotEmpty(message = "Start date time of the booking is required.")
    @Column(nullable = false)
    private LocalDateTime startDateTime;

    @NotEmpty(message = "End date time of the booking is required.")
    @Column(nullable = false)
    private LocalDateTime endDateTime;

    private LocalDateTime actualReturnDate;

    @Range(min = 0)
    private double totalAmount;

    @Range(min = 0)
    private double penalty = 0;

    @Column(nullable = false)
    private BookingStatus status;

    private boolean isOverDue = false;
    private Long overdueDurationInMinutes = 0L;
}
