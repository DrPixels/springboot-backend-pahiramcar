package com.lindtsey.pahiramcar.reservations;

import com.lindtsey.pahiramcar.customer.Customer;
import com.lindtsey.pahiramcar.enums.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

    List<Reservation> findReservationsByCustomer_UserIdOrderByStartDateTimeAsc(Integer customerId);

    @Query("SELECT COUNT(r) > 0 FROM Reservation r " +
            "WHERE r.car.carId = :carId " +
            "AND (:startDateTime <= r.endDateTime AND :endDateTime >= r.startDateTime) " +
            "AND r.status = :status")
    boolean isCarReserved(@Param("carId") Integer carId,
                          @Param("startDateTime") LocalDateTime startDateTime,
                          @Param("endDateTime") LocalDateTime endDateTime,
                          @Param("status") ReservationStatus status
    );

    @Modifying
    @Query("UPDATE Reservation r " +
            "SET r.status = :newStatus " +
            "WHERE r.endDateTime < :now " +
            "AND r.status = :currentStatus")
    void updatedExpiredReservation(@Param("now") LocalDateTime now,
                                   @Param("currentStatus") ReservationStatus currentStatus,
                                   @Param("newStatus") ReservationStatus newStatus);

    // For counting reservation per Customer
    int countActiveReservationsByCustomer_UserIdAndStatus(Integer customerId, ReservationStatus status);

    int countActiveReservationsByCustomer_UserIdAndStatusAndStartDateTimeBetween(Integer customerId, ReservationStatus status, LocalDateTime startDateTime, LocalDateTime endDateTime);

    Integer customer(Customer customer);
}
