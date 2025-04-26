package com.lindtsey.pahiramcar.transactions.childClass.bookingPayment;

import com.lindtsey.pahiramcar.transactions.Transaction;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@Entity
@SuperBuilder
@DiscriminatorValue("BOOKING_PAYMENT")
public class BookingPaymentTransaction extends Transaction {

    @Column(nullable = false)
    private double depositAmount;

    @Column(nullable = false)
    private boolean isRefundableDepositClaimed = false;
}
