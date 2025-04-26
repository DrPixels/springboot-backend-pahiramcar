package com.lindtsey.pahiramcar.transactions;

import com.lindtsey.pahiramcar.bookings.Booking;
import com.lindtsey.pahiramcar.car.Car;
import com.lindtsey.pahiramcar.customer.Customer;
import com.lindtsey.pahiramcar.employee.Employee;
import com.lindtsey.pahiramcar.enums.PaymentMode;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Range;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "Transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer transactionId;

    @ManyToOne
    @JoinColumn(
            name = "user_id"
    )
    private Employee employee;

    @OneToOne
    @JoinColumn(
            name = "booking_id"
    )
    private Booking booking;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime transactionDate;

    @Column(nullable = false)
    private double carRentalPaid;

    private LocalDateTime penaltyPaidDateTime;

    @Range(min = 0)
    private double penaltyPaid;

    private LocalDateTime carDamagePaidDateTime;

    @Range(min = 0)
    private double carDamagePaid;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentMode paymentMode;

}
