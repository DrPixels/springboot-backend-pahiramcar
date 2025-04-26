package com.lindtsey.pahiramcar.transactions;

import com.lindtsey.pahiramcar.enums.PaymentMode;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

import java.time.LocalDateTime;

public record TransactionDTO(
        @NotEmpty(message = "Car rental paid is required.")
        @Range(min = 0) double carRentalPaid,

        @NotEmpty(message = "Payment mode is required.")
        PaymentMode paymentMode,

        @NotEmpty(message = "Employee ID related to Transaction is required.")
        Integer employeeId,

        @NotEmpty(message = "Customer ID related to Transaction is required.")
        Integer customerId,

        @NotEmpty(message = "Car ID related to Transaction is required.")
        Integer carId) {
}
