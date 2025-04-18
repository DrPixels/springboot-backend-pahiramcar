package com.lindtsey.pahiramcar.transactions;

import com.lindtsey.pahiramcar.enums.PaymentMode;

public record TransactionDTO(
        Float amountDue,
        Float amountPaid,
        PaymentMode paymentMode,
        Integer employeeId,
        Integer bookingId,
        Integer customerId,
        Integer carId) {
}
