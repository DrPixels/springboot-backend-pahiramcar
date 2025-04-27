package com.lindtsey.pahiramcar.transactions.childClass.bookingPayment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface BookingPaymentTransactionRepository extends JpaRepository<BookingPaymentTransaction, Integer> {

    @Query("SELECT COALESCE(SUM(CASE WHEN t.isRefundableDepositClaimed " +
            "THEN t.amountPaid - t.depositAmount " +
            "ELSE t.amountPaid END), 0.0) " +
            "FROM BookingPaymentTransaction t")
    double totalBookingPaymentRevenue();

    @Query("SELECT COALESCE(SUM(CASE WHEN t.isRefundableDepositClaimed " +
            "THEN t.amountPaid - t.depositAmount " +
            "ELSE t.amountPaid END), 0.0) " +
            "FROM BookingPaymentTransaction t " +
            "WHERE t.transactionDateTime " +
            "BETWEEN :startDateTime and :endDateTime")
    double totalBookingPaymentRevenueBetween(@Param("startDateTime")LocalDateTime startDateTime,
                                             @Param("endDateTime")LocalDateTime endDateTime);

    @Query("SELECT COALESCE(SUM(CASE WHEN t.isRefundableDepositClaimed " +
            "THEN t.amountPaid - t.depositAmount " +
            "ELSE t.amountPaid END), 0.0) " +
            "FROM BookingPaymentTransaction t " +
            "WHERE t.transactionDateTime < :startDateTime")
    double totalBookingPaymentRevenueBeforeThisMonth(@Param("startDateTime")LocalDateTime startDateTime);

    @Query("SELECT COALESCE(SUM(CASE WHEN t.isRefundableDepositClaimed " +
            "THEN t.amountPaid - t.depositAmount " +
            "ELSE t.amountPaid END), 0.0) " +
            "FROM BookingPaymentTransaction t " +
            "WHERE t.booking.reservation.car.carId = :carId")
    double totalBookingPaymentRevenueByCar(@Param("carId")Integer carId);

    @Query("SELECT COALESCE(SUM(CASE WHEN t.isRefundableDepositClaimed " +
            "THEN t.amountPaid - t.depositAmount " +
            "ELSE t.amountPaid END), 0.0) " +
            "FROM BookingPaymentTransaction t " +
            "WHERE t.transactionDateTime " +
            "BETWEEN :startDateTime AND :endDateTime " +
            "AND t.booking.reservation.car.carId = :carId")
    double totalBookingPaymentRevenueByCarBetween(@Param("startDateTime")LocalDateTime startDateTime,
                                                  @Param("endDateTime")LocalDateTime endDateTime,
                                                  @Param("carId")Integer carId);
}
