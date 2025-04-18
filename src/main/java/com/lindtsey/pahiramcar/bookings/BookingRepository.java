package com.lindtsey.pahiramcar.bookings;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {

    List<Booking> findBookingsByCustomer_CustomerId(Integer customerId);
    List<Booking> findBookingsByCar_CarId(Integer carId);
}
