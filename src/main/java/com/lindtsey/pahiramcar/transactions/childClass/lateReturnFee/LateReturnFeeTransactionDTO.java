package com.lindtsey.pahiramcar.transactions.childClass.lateReturnFee;

import com.lindtsey.pahiramcar.enums.PaymentMode;
import jakarta.validation.constraints.NotNull;

public record LateReturnFeeTransactionDTO(

        @NotNull(message = "Payment mode is required.")
        PaymentMode paymentMode,

        @NotNull(message = "Employee ID related to Transaction is required.")
        Integer employeeId
)
{
}
