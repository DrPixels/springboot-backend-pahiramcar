package com.lindtsey.pahiramcar.transactions.childClass.damageRepairFee;

import com.lindtsey.pahiramcar.enums.PaymentMode;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

public record DamageRepairFeeTransactionDTO(
        @NotEmpty(message = "Amount paid is required.")
        @Range(min = 0) double amountPaid,

        @NotEmpty(message = "Payment mode is required.")
        PaymentMode paymentMode,

        @NotBlank(message = "Car damage description is required.")
        String carDamageDescription,

        @NotEmpty(message = "Employee ID related to Transaction is required.")
        Integer employeeId,

        @NotEmpty(message = "Booking ID related to Transaction is required.")
        Integer bookingId)
{
}
