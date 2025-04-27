package com.lindtsey.pahiramcar.transactions.childClass.damageRepairFee;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface DamageRepairFeeTransactionRepository extends JpaRepository<DamageRepairFeeTransaction, Integer> {

    @Query("SELECT COALESCE(SUM(t.amountPaid), 0.0) FROM DamageRepairFeeTransaction t")
    double totalDamageRepairFeeRevenue();

    @Query("SELECT COALESCE(SUM(t.amountPaid), 0.0) FROM DamageRepairFeeTransaction t " +
            "WHERE t.transactionDateTime " +
            "BETWEEN :startDateTime and :endDateTime")
    double totalDamageRepairFeeRevenueBetween(@Param("startDateTime") LocalDateTime startDateTime,
                                             @Param("endDateTime")LocalDateTime endDateTime);

    @Query("SELECT COALESCE(SUM(t.amountPaid), 0.0) FROM DamageRepairFeeTransaction t " +
            "WHERE t.transactionDateTime < :startDateTime")
    double totalDamageRepairFeeRevenueBeforeThisMonth(@Param("startDateTime")LocalDateTime startDateTime);

    @Query("SELECT COALESCE(SUM(t.amountPaid), 0.0) " +
            "FROM DamageRepairFeeTransaction t " +
            "WHERE t.booking.reservation.car.carId = :carId")
    double totalDamageRepairFeeRevenueByCar(@Param("carId")Integer carId);

    @Query("SELECT COALESCE(SUM(t.amountPaid), 0.0) FROM DamageRepairFeeTransaction t " +
            "WHERE t.transactionDateTime " +
            "BETWEEN :startDateTime and :endDateTime " +
            "AND t.booking.reservation.car.carId = :carId")
    double totalDamageRepairFeeRevenueByCarBetween(@Param("startDateTime") LocalDateTime startDateTime,
                                              @Param("endDateTime")LocalDateTime endDateTime,
                                                   @Param("carId")Integer carId);
}
