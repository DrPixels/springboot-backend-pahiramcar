package com.lindtsey.pahiramcar.transactions.childClass.damageRepairFee;

import com.lindtsey.pahiramcar.enums.PaymentMode;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Range;

public record  DamageRepairFeeTransactionDTO(
        @NotNull(message = "Amount paid is required.")
        @Range(min = 0) double amountPaid,

        @NotNull(message = "Payment mode is required.")
        PaymentMode paymentMode,

        @NotBlank(message = "Car damage description is required.")
        String carDamageDescription,

        @NotNull(message = "Employee ID related to Transaction is required.")
        Integer employeeId)
{
}
