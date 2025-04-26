package com.lindtsey.pahiramcar.transactions;

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

    @Query("SELECT SUM(CASE WHEN t.isRefundableDepositClaimed " +
            "THEN t.carRentalPaid - :deposit " +
            "ELSE t.carRentalPaid " +
            "END + COALESCE(t.penaltyPaid, 0)) " +
            "FROM Transaction t")
    Double totalRevenue(@Param("deposit") Double deposit);

    @Query("SELECT SUM(CASE WHEN t.isRefundableDepositClaimed " +
            "THEN t.carRentalPaid - :deposit " +
            "ELSE t.carRentalPaid " +
            "END + COALESCE(t.penaltyPaid, 0)) " +
            "FROM Transaction t " +
            "WHERE t.transactionDateTime " +
            "BETWEEN :startDateOfMonth AND :endDateOfMonth")
    Double totalRevenueBetweenThisTime(@Param("deposit") Double deposit,
                                       @Param("startDateOfMonth")LocalDateTime startOfMonth,
                                       @Param("endDateOfMonth") LocalDateTime endOfMonth);

    @Query("SELECT SUM(CASE WHEN t.isRefundableDepositClaimed " +
            "THEN t.carRentalPaid - :deposit " +
            "ELSE t.carRentalPaid " +
            "END + COALESCE(t.penaltyPaid, 0)) " +
            "FROM Transaction t " +
            "WHERE t.transactionDateTime < :startOfMonth")
    Double totalRevenueBeforeThisMonth(@Param("deposit") Double deposit,
                                       @Param("startDateOfMonth")LocalDateTime startOfMonth);

    @Query("SELECT SUM(CASE WHEN t.isRefundableDepositClaimed " +
            "THEN t.carRentalPaid - :deposit " +
            "ELSE t.carRentalPaid " +
            "END + COALESCE(t.penaltyPaid, 0)) " +
            "FROM Transaction t " +
            "WHERE t.carRentalPaymentMode = :paymentMode " +
            "OR t.penaltyPaymentMode = :paymentMode")
    Double totalRevenueByPaymentMode(@Param("deposit") Double deposit,
                                     @Param("paymentMode") PaymentMode paymentMode);
}
