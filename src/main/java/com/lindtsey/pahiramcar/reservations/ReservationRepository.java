package com.lindtsey.pahiramcar.reservations;

import com.lindtsey.pahiramcar.customer.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

    public List<Reservation> findReservationsByCustomer_CustomerId(Integer customerId);

}
