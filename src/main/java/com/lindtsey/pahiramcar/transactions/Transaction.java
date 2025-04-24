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
            name = "employee_id"
    )
    private Employee employee;

    @OneToOne
    @JoinColumn(
            name = "booking_id"
    )
    private Booking booking;

    @CreationTimestamp
    private LocalDateTime transactionDate;

    @NotEmpty(message = "Car rental paid is required.")
    @Column(nullable = false)
    @Range(min = 0)
    private double carRentalPaid;

    @Range(min = 0)
    private double penaltyPaid;

    @NotEmpty(message = "Payment mode is required.")
    @Column(nullable = false)
    private PaymentMode paymentMode;

}
