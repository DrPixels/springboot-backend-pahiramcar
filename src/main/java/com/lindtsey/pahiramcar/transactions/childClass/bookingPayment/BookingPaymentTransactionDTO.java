package com.lindtsey.pahiramcar.transactions.childClass.bookingPayment;

import com.lindtsey.pahiramcar.enums.PaymentMode;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Range;

public record BookingPaymentTransactionDTO(

        @NotNull(message = "Payment mode is required.")
        PaymentMode paymentMode,

        @NotNull(message = "Employee ID related to Transaction is required.")
        Integer employeeId)
{
}
