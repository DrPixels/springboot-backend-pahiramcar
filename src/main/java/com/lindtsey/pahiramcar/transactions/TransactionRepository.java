package com.lindtsey.pahiramcar.transactions;

import com.lindtsey.pahiramcar.car.Car;
import com.lindtsey.pahiramcar.enums.PaymentMode;
import com.lindtsey.pahiramcar.enums.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    List<Transaction> findTransactionsByBooking_Reservation_Customer_UserId(Integer id);

    Transaction findTransactionByBooking_BookingIdAndTransactionType(Integer bookingId, TransactionType transactionType);

    @Query("SELECT SUM(t.amountPaid) " +
            "FROM Transaction t " +
            "WHERE t.paymentMode = :paymentMode ")
    Double totalRevenueByPaymentMode(@Param("paymentMode") PaymentMode paymentMode);

    @Query("SELECT SUM(t.amountPaid) " +
            "FROM Transaction t " +
            "WHERE t.paymentMode = :paymentMode " +
            "AND t.transactionDateTime " +
            "BETWEEN :startDateTime AND :endDateTime")
    Double totalRevenueByPaymentModeBetween(@Param("paymentMode") PaymentMode paymentMode,
                                            @Param("startDateTime")LocalDateTime startDateTime,
                                            @Param("endDateTime")LocalDateTime endDateTime);

}
