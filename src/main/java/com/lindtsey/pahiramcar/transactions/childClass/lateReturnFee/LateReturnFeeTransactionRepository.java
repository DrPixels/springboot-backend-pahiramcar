package com.lindtsey.pahiramcar.transactions.childClass.lateReturnFee;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface LateReturnFeeTransactionRepository extends JpaRepository<LateReturnFeeTransaction, Integer> {

    @Query("SELECT COALESCE(SUM(t.amountPaid), 0.0) FROM LateReturnFeeTransaction t")
    double totalLateReturnFeeFeeRevenue();

    @Query("SELECT COALESCE(SUM(t.amountPaid), 0.0) FROM LateReturnFeeTransaction t " +
            "WHERE t.transactionDateTime " +
            "BETWEEN :startDateTime and :endDateTime")
    double totalLateReturnFeeRevenueBetween(@Param("startDateTime") LocalDateTime startDateTime,
                                              @Param("endDateTime")LocalDateTime endDateTime);

    @Query("SELECT COALESCE(SUM(t.amountPaid), 0.0) FROM LateReturnFeeTransaction t " +
            "WHERE t.transactionDateTime < :startDateTime")
    double totalLateReturnFeeRevenueBeforeThisMonth(@Param("startDateTime")LocalDateTime startDateTime);

    @Query("SELECT COALESCE(SUM(t.amountPaid), 0.0) " +
            "FROM LateReturnFeeTransaction t " +
            "WHERE t.booking.reservation.car.carId = :carId")
    double totalLateReturnFeeFeeRevenueByCar(@Param("carId")Integer carId);

    @Query("SELECT COALESCE(SUM(t.amountPaid), 0.0) FROM LateReturnFeeTransaction t " +
            "WHERE t.transactionDateTime " +
            "BETWEEN :startDateTime and :endDateTime " +
            "AND t.booking.reservation.car.carId = :carId")
    double totalLateReturnFeeRevenueByCarBetween(@Param("startDateTime") LocalDateTime startDateTime,
                                            @Param("endDateTime")LocalDateTime endDateTime,
                                                 @Param("carId")Integer carId);
}

