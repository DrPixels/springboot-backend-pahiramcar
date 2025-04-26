package com.lindtsey.pahiramcar.reservations;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    // Accessible by customer
    // Get all the reservations per customer
    @GetMapping("/api/customer/reservations/{customer-id}")
    public ResponseEntity<?> findReservationsByCustomerId(@PathVariable("customer-id") Integer customerId) {
        List<Reservation> customerReservations = reservationService.findReservationsByCustomerId(customerId);
        return new ResponseEntity<>(customerReservations, HttpStatus.OK);
    }

    // Accessible by customer
    // Add new reservation
    @PostMapping("/api/customer/reservations")
    public ResponseEntity<?> saveReservation(@Valid @RequestBody ReservationDTO dto) {
        Reservation reservation = reservationService.saveReservation(dto);

        return new ResponseEntity<>(reservation, HttpStatus.CREATED);
    }

    // Accessible by customer
    // Cancel reservation
    @PutMapping("/api/customer/reservation/{reservation-id}/cancel")
    public ResponseEntity<?> cancelReservation(@PathVariable("reservation-id") Integer reservationId) {

        reservationService.cancelReservation(reservationId);

        return new ResponseEntity<>("Reservation cancelled successfully.", HttpStatus.OK);
    }

    // Temporarily Disabled
//    @DeleteMapping("/api/reservations/{reservation-id}")
//    public ResponseEntity<?> deleteReservation(@PathVariable("reservation-id") Integer reservationId) {
//        reservationService.deleteReservation(reservationId);
//
//        return new ResponseEntity<>("Reservation deleted successfully.", HttpStatus.OK);
//    }
}
