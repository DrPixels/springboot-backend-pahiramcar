package com.lindtsey.pahiramcar.transactions;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.lindtsey.pahiramcar.bookings.Booking;
import com.lindtsey.pahiramcar.car.Car;
import com.lindtsey.pahiramcar.customer.Customer;
import com.lindtsey.pahiramcar.employee.Employee;
import com.lindtsey.pahiramcar.enums.PaymentMode;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

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
    private double carRentalPaid;
    private double penaltyPaid;
    private PaymentMode paymentMode;

}
