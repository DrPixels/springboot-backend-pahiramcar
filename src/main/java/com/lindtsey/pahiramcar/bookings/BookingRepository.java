package com.lindtsey.pahiramcar.bookings;

import com.lindtsey.pahiramcar.enums.BookingStatus;
import com.lindtsey.pahiramcar.reports.CarPerformance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {

    List<Booking> findBookingsByReservation_Customer_UserId(Integer customerId);
//    List<Booking> findBookingsByCar_CarId(Integer carId);

    List<Booking> findBookingsByReservation_Customer_UserIdAndStatus(Integer customer_Id, BookingStatus status);

    @Query("SELECT COUNT(b) > 0 FROM Booking b " +
            "WHERE b.driverLicenseNumber = :driverLicenseNumber " +
            "AND b.status = :bookingStatus")
    boolean isDriverLicenseCurrentlyUsedInBooking(@Param("driverLicenseNumber") String driverLicenseNumber,
                                                  @Param("bookingStatus")BookingStatus status);

    @Modifying
    @Query("UPDATE Booking b " +
            "SET b.status = :newStatus " +
            "WHERE b.actualReturnDate IS NULL " +
            "AND b.endDateTime < :currentTime " +
            "AND b.status = :oldStatus ")
    void updateDueBooking(@Param("newStatus") BookingStatus newStatus,
                          @Param("currentTime") LocalDateTime now,
                          @Param("oldStatus") BookingStatus oldStatus);

    int countActiveBookingsByReservation_Customer_UserIdAndStatus(Integer customerId, BookingStatus bookingStatus);

    int countActiveBookingsByReservation_Customer_UserIdAndStatusAndStartDateTimeBetween(Integer customerId, BookingStatus bookingStatus, LocalDateTime startDateTime, LocalDateTime endDateTime);

    // For Report
    // To get the total bookings between time
    int countBookingsByStartDateTimeBetween(LocalDateTime startDateTime, LocalDateTime endDateTime);

    int countBookingsByStartDateTimeBefore(LocalDateTime startDateTime);

    @Query("SELECT b.startDateTime, b.endDateTime " +
            "FROM Booking b")
    List<Object[]> findStartAndEndDateTimes();

    @Query("SELECT b.startDateTime, b.endDateTime " +
            "FROM Booking b " +
            "WHERE b.startDateTime < :startDateTime")
    List<Object[]> findStartAndEndDateTimesBefore(@Param("startDateTime") LocalDateTime startDateTime);

    @Query("SELECT b.startDateTime, b.endDateTime " +
            "FROM Booking b " +
            "WHERE b.startDateTime " +
            "BETWEEN :startDateTime AND :endDateTime")
    List<Object[]> findStartAndEndDateTimesBetween(@Param("startDateTime") LocalDateTime startDateTime,
                                                   @Param("endDateTime") LocalDateTime endDateTime);

    @Query("SELECT new com.lindtsey.pahiramcar.reports.CarPerformance(b.reservation.car, COUNT(*)) " +
            "FROM Booking b " +
            "GROUP BY b.reservation.car.carId")
    List<CarPerformance> findAllBookedCarsAndCount();

    @Query("SELECT b.reservation.car, COUNT(*) " +
            "FROM Booking b " +
            "WHERE b.startDateTime " +
            "BETWEEN :startDateTime AND :endDateTime " +
            "GROUP BY b.reservation.car.carId ")
    List<CarPerformance> findAllBookedCarsAndCountBetween(@Param("startDateTime") LocalDateTime startDateTime,
                                                          @Param("endDateTime") LocalDateTime endDateTime);
}
