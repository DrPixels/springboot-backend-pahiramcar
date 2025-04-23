package com.lindtsey.pahiramcar.transactions;

import com.lindtsey.pahiramcar.enums.PaymentMode;

import java.time.LocalDateTime;

public record TransactionDTO(
        double carRentalPaid,
        PaymentMode paymentMode,
        Integer employeeId,
        Integer customerId,
        Integer carId) {
}
