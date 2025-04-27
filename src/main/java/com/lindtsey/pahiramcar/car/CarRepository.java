package com.lindtsey.pahiramcar.car;

import com.lindtsey.pahiramcar.enums.CarStatus;
import com.lindtsey.pahiramcar.enums.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Integer> {

    boolean existsCarByCarIdAndStatus(Integer carId, CarStatus status);

    List<Car> findCarsByStatusAndIsArchived(CarStatus status, boolean isArchived);

    @Modifying
    @Query("UPDATE Car c SET c.status = :newCarStatus " +
            "WHERE c.status = :currentCarStatus " +
            "AND c.carId IN " +
            "(SELECT r.car.carId FROM Reservation r " +
            "WHERE r.status = :reservationStatus AND r.endDateTime < :now) " +
            "AND c.carId NOT IN " +
            "(SELECT r2.car.carId FROM Reservation r2 " +
            "WHERE r2.status NOT IN :activeReservationStatuses)")
    void updateCarStatusForExpiredReservation(@Param("newCarStatus") CarStatus newStatus,
                                              @Param("currentCarStatus") CarStatus currentStatus,
                                              @Param("now") LocalDateTime now,
                                              @Param("reservationStatus") ReservationStatus reservationStatus,
                                              @Param("activeReservationStatuses") List<ReservationStatus> activeReservationStatuses
                                            );
}
