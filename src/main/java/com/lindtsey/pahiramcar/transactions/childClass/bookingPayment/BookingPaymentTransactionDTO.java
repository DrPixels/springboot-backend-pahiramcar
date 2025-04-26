package com.lindtsey.pahiramcar.transactions.childClass.bookingPayment;

import com.lindtsey.pahiramcar.enums.PaymentMode;
import jakarta.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

public record BookingPaymentTransactionDTO(
        @NotEmpty(message = "Amount paid is required.")
        @Range(min = 0) double amountPaid,

        @NotEmpty(message = "Payment mode is required.")
        PaymentMode paymentMode,

        @NotEmpty(message = "Employee ID related to Transaction is required.")
        Integer employeeId,

        @NotEmpty(message = "Booking ID related to Transaction is required.")
        Integer bookingId)
{
}
