package com.lindtsey.pahiramcar.transactions;

import com.lindtsey.pahiramcar.enums.PaymentMode;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

import java.time.LocalDateTime;

public record TransactionDTO(
        @NotEmpty(message = "Amount paid is required.")
        @Range(min = 0) double amountPaid,

        @NotEmpty(message = "Payment mode is required.")
        PaymentMode paymentMode,

        String carDamageDescription,

        @NotEmpty(message = "Employee ID related to Transaction is required.")
        Integer employeeId,

        Integer bookingId)
{
}
