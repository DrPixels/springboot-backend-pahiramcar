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

    @GetMapping("/api/reservations/{customer-id}")
    public ResponseEntity<?> findReservationsByCustomerId(@PathVariable("customer-id") Integer customerId) {
        List<Reservation> customerReservations = reservationService.findReservationsByCustomerId(customerId);
        return new ResponseEntity<>(customerReservations, HttpStatus.OK);
    }

    @PostMapping("/api/reservations")
    public ResponseEntity<?> saveReservation(@RequestBody ReservationDTO dto) {
        Reservation reservation = reservationService.saveReservation(dto);

        return new ResponseEntity<>(reservation, HttpStatus.CREATED);
    }

    @PutMapping("/api/reservation/{reservation-id}/cancel")
    public ResponseEntity<?> cancelReservation(@PathVariable("reservation-id") Integer reservationId) {

        reservationService.cancelReservation(reservationId);

        return new ResponseEntity<>("Reservation cancelled successfully.", HttpStatus.OK);
    }

    @DeleteMapping("/api/reservations/{reservation-id}")
    public ResponseEntity<?> deleteReservation(@PathVariable("reservation-id") Integer reservationId) {
        reservationService.deleteReservation(reservationId);

        return new ResponseEntity<>("Reservation deleted successfully.", HttpStatus.OK);
    }
}
