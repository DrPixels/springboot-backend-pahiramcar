package com.lindtsey.pahiramcar.bookings;

import com.lindtsey.pahiramcar.enums.BookingStatus;
import com.lindtsey.pahiramcar.reservations.Reservation;
import com.lindtsey.pahiramcar.images.Image;
import com.lindtsey.pahiramcar.transactions.Transaction;
import com.lindtsey.pahiramcar.utils.customAnnotations.reservationBookingDate.ReservationBookingDateConstraint;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "Bookings")
@ReservationBookingDateConstraint
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer bookingId;

    @OneToMany (
            mappedBy = "booking"
    )
    private List<Transaction> transactions;

    @OneToOne
    @JoinColumn(
            name = "reservation_id"
    )
    private Reservation reservation;

    @OneToMany(
            mappedBy = "booking"
    )
    private List<Image> bookingProofImages;

    @Column(nullable = false)
    private String renterFullName;

    @Column(nullable = false)
    private String driverLicenseNumber;

    @Column(nullable = false)
    private LocalDateTime startDateTime;

    @Column(nullable = false)
    private LocalDateTime endDateTime;

    private LocalDateTime actualReturnDate;

    @Range(min = 0)
    private double totalAmount;

    @Range(min = 0)
    private double penalty = 0;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private BookingStatus status;

    private boolean isOverDue = false;

    @Range(min = 0)
    private Long overdueDurationInMinutes = 0L;
}
