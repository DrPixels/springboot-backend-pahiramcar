package com.lindtsey.pahiramcar.transactions.childClass.lateReturnFee;

import com.lindtsey.pahiramcar.enums.PaymentMode;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Range;

public record LateReturnFeeTransactionDTO(
        @NotEmpty(message = "Amount paid is required.")
        @Range(min = 0) double amountPaid,

        @NotNull(message = "Overdue hours is required.")
        @Range(min = 0) int overdueHours,

        @NotEmpty(message = "Payment mode is required.")
        PaymentMode paymentMode,

        @NotEmpty(message = "Employee ID related to Transaction is required.")
        Integer employeeId,

        @NotEmpty(message = "Booking ID related to Transaction is required.")
        Integer bookingId)
{
}
