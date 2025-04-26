package com.lindtsey.pahiramcar.transactions.childClass.damageRepairFee;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface DamageRepairFeeTransactionRepository extends JpaRepository<DamageRepairFeeTransaction, Integer> {

    @Query("SELECT SUM(t.amountPaid) FROM DamageRepairFeeTransaction t")
    Double totalDamageRepairFeeRevenue();

    @Query("SELECT SUM(t.amountPaid) FROM DamageRepairFeeTransaction t " +
            "WHERE t.transactionDateTime " +
            "BETWEEN :startDateTime and :endDateTime")
    Double totalDamageRepairFeeRevenueBetween(@Param("startDateTime") LocalDateTime startDateTime,
                                             @Param("endDateTime")LocalDateTime endDateTime);

    @Query("SELECT SUM(t.amountPaid) FROM DamageRepairFeeTransaction t " +
            "WHERE t.transactionDateTime < :startDateTime")
    Double totalDamageRepairFeeRevenueBeforeThisMonth(@Param("startDateTime")LocalDateTime startDateTime);

    @Query("SELECT SUM(t.amountPaid) " +
            "FROM DamageRepairFeeTransaction t " +
            "WHERE t.booking.reservation.car.carId = :carId")
    Double totalDamageRepairFeeRevenueByCar(@Param("carId")Integer carId);

    @Query("SELECT SUM(t.amountPaid) FROM DamageRepairFeeTransaction t " +
            "WHERE t.transactionDateTime " +
            "BETWEEN :startDateTime and :endDateTime " +
            "AND t.booking.reservation.car.carId = :carId")
    Double totalDamageRepairFeeRevenueByCarBetween(@Param("startDateTime") LocalDateTime startDateTime,
                                              @Param("endDateTime")LocalDateTime endDateTime,
                                                   @Param("carId")Integer carId);
}
