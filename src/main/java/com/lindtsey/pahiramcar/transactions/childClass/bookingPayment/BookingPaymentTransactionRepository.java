package com.lindtsey.pahiramcar.transactions.childClass.bookingPayment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface BookingPaymentTransactionRepository extends JpaRepository<BookingPaymentTransaction, Integer> {

    @Query("SELECT SUM(CASE WHEN t.isRefundableDepositClaimed " +
            "THEN t.amountPaid - t.depositAmount " +
            "ELSE t.amountPaid " +
            "END) FROM BookingPaymentTransaction t")
    Double totalBookingPaymentRevenue();

    @Query("SELECT SUM(CASE WHEN t.isRefundableDepositClaimed " +
            "THEN t.amountPaid - t.depositAmount " +
            "ELSE t.amountPaid " +
            "END) FROM BookingPaymentTransaction t " +
            "WHERE t.transactionDateTime " +
            "BETWEEN :startDateTime and :endDateTime")
    Double totalBookingPaymentRevenueBetween(@Param("startDateTime")LocalDateTime startDateTime,
                                             @Param("endDateTime")LocalDateTime endDateTime);

    @Query("SELECT SUM(CASE WHEN t.isRefundableDepositClaimed " +
            "THEN t.amountPaid - t.depositAmount " +
            "ELSE t.amountPaid " +
            "END) FROM BookingPaymentTransaction t " +
            "WHERE t.transactionDateTime < :startDateTime")
    Double totalBookingPaymentRevenueBeforeThisMonth(@Param("startDateTime")LocalDateTime startDateTime);

    @Query("SELECT SUM(CASE WHEN t.isRefundableDepositClaimed " +
            "THEN t.amountPaid - t.depositAmount " +
            "ELSE t.amountPaid " +
            "END) FROM BookingPaymentTransaction t " +
            "WHERE t.booking.reservation.car.carId = :carId")
    Double totalBookingPaymentRevenueByCar(@Param("carId")Integer carId);

    @Query("SELECT SUM(CASE WHEN t.isRefundableDepositClaimed " +
            "THEN t.amountPaid - t.depositAmount " +
            "ELSE t.amountPaid " +
            "END) FROM BookingPaymentTransaction t " +
            "WHERE t.transactionDateTime " +
            "BETWEEN :startDateTime AND :endDateTime " +
            "AND t.booking.reservation.car.carId = :carId")
    Double totalBookingPaymentRevenueByCarBetween(@Param("startDateTime")LocalDateTime startDateTime,
                                                  @Param("endDateTime")LocalDateTime endDateTime,
                                                  @Param("carId")Integer carId);
}
