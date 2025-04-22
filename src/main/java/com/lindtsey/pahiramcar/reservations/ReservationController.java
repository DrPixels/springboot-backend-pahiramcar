package com.lindtsey.pahiramcar.reservations;

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

    @GetMapping("/api/customers/{customer-id}/reservations")
    public ResponseEntity<?> findReservationsByCustomerId(@PathVariable("customer-id") Integer customerId) {
        List<Reservation> customerReservations = reservationService.findReservationsByCustomerId(customerId);
        return new ResponseEntity<>(customerReservations, HttpStatus.OK);
    }

    @PostMapping("/api/reservations")
    public ResponseEntity<?> saveReservation(@RequestBody ReservationDTO dto) {
        Reservation reservation = reservationService.saveReservation(dto);

        return new ResponseEntity<>(reservation, HttpStatus.CREATED);
    }

    @PutMapping("/api/reservation/{reservation-id}/status")
    public ResponseEntity<?> updateReservationStatus(@PathVariable("reservation-id") Integer reservationId, @RequestBody Map<String, String> request) {

        String newStatus = request.get("status");

        reservationService.updateStatus(reservationId, newStatus);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/api/reservations/{reservation-id}")
    public ResponseEntity<?> deleteReservation(@PathVariable("reservation-id") Integer reservationId) {
        reservationService.deleteReservation(reservationId);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
