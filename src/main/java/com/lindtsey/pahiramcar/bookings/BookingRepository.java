package com.lindtsey.pahiramcar.bookings;

import com.lindtsey.pahiramcar.enums.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {

//    List<Booking> findBookingsByCustomer_CustomerId(Integer customerId);
//    List<Booking> findBookingsByCar_CarId(Integer carId);

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
}
