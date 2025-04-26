package com.lindtsey.pahiramcar.transactions.childClass.lateReturnFee;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface LateReturnFeeTransactionRepository extends JpaRepository<LateReturnFeeTransaction, Integer> {

    @Query("SELECT SUM(t.amountPaid) FROM LateReturnFeeTransaction t")
    Double totalLateReturnFeeFeeRevenue();

    @Query("SELECT SUM(t.amountPaid) FROM LateReturnFeeTransaction t " +
            "WHERE t.transactionDateTime " +
            "BETWEEN :startDateTime and :endDateTime")
    Double totalLateReturnFeeRevenueBetween(@Param("startDateTime") LocalDateTime startDateTime,
                                              @Param("endDateTime")LocalDateTime endDateTime);

    @Query("SELECT SUM(t.amountPaid) FROM LateReturnFeeTransaction t " +
            "WHERE t.transactionDateTime < :startDateTime")
    Double totalLateReturnFeeRevenueBeforeThisMonth(@Param("startDateTime")LocalDateTime startDateTime);

    @Query("SELECT SUM(t.amountPaid) " +
            "FROM LateReturnFeeTransaction t " +
            "WHERE t.booking.reservation.car.carId = :carId")
    Double totalLateReturnFeeFeeRevenueByCar(@Param("carId")Integer carId);

    @Query("SELECT SUM(t.amountPaid) FROM LateReturnFeeTransaction t " +
            "WHERE t.transactionDateTime " +
            "BETWEEN :startDateTime and :endDateTime " +
            "AND t.booking.reservation.car.carId = :carId")
    Double totalLateReturnFeeRevenueByCarBetween(@Param("startDateTime") LocalDateTime startDateTime,
                                            @Param("endDateTime")LocalDateTime endDateTime,
                                                 @Param("carId")Integer carId);
}

