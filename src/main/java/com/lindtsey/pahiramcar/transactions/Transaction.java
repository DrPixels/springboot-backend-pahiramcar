package com.lindtsey.pahiramcar.transactions;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.lindtsey.pahiramcar.bookings.Booking;
import com.lindtsey.pahiramcar.employee.Employee;
import com.lindtsey.pahiramcar.enums.PaymentMode;
import com.lindtsey.pahiramcar.enums.TransactionType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "Transactions")
@SuperBuilder
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer transactionId;

    @ManyToOne
    @JoinColumn(
            name = "employee_id"
    )
    @JsonBackReference
    private Employee employee;

    @ManyToOne
    @JoinColumn(
            name = "booking_id"
    )
    @JsonBackReference
    private Booking booking;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime transactionDateTime;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    @Column(nullable = false)
    private double amountPaid;

    private String onlinePaymentMethodDescription;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentMode paymentMode;
}
