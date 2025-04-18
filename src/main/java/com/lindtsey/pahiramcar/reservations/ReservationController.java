package com.lindtsey.pahiramcar.reservations;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/api/{customer-id}/reservations")
    public ResponseEntity<?> findReservationsByCustomerId(@PathVariable("customer-id") Integer customerId) {
        List<Reservation> customerReservations = reservationService.findReservationsByCustomerId(customerId);
        return new ResponseEntity<>(customerReservations, HttpStatus.OK);
    }

    @PostMapping("/api/reservations")
    public ResponseEntity<?> saveReservation(@RequestBody ReservationDTO dto) {
        System.out.println(dto.toString());
        Reservation reservation = reservationService.saveReservation(dto);

        return new ResponseEntity<>(reservation, HttpStatus.CREATED);
    }

    @DeleteMapping("/api/reservations/{reservation-id}")
    public ResponseEntity<?> deleteReservation(@PathVariable("reservation-id") Integer reservationId) {
        reservationService.deleteReservation(reservationId);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
