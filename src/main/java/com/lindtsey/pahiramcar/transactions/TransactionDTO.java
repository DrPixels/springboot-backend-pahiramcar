package com.lindtsey.pahiramcar.transactions;

import com.lindtsey.pahiramcar.enums.PaymentMode;
import jakarta.validation.constraints.NotNull;

public record TransactionDTO(

        @NotNull(message = "Payment mode is required.")
        PaymentMode paymentMode,

        String carDamageDescription,

        @NotNull(message = "Employee ID related to Transaction is required.")
        Integer employeeId,

        Integer bookingId)
{
}
