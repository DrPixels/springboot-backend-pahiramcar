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
    @Query("UPDATE Car c SET c.status = :newStatus WHERE c.carId IN " +
            "(SELECT r.car.carId FROM Reservation r " +
            "WHERE r.status = :reservationStatus AND r.endDateTime < :now)")
    void updateCarStatusForExpiredReservation(@Param("newStatus") CarStatus newStatus,
                                              @Param("reservationStatus") ReservationStatus reservationStatus,
                                              @Param("now") LocalDateTime now);
}
