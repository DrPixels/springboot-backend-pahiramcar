package com.lindtsey.pahiramcar.reservations;

import com.lindtsey.pahiramcar.employee.Employee;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@Tag(name = "Reservation")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    // Accessible by customer
    // Get all the reservations per customer
    @Operation(
            summary = "Retrieves a list of all reservations made by a specific customer."
    )
    @ApiResponse(
            responseCode = "200",
            content = @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = Reservation.class)))
    )
    @GetMapping("/api/customer/reservations/{customer-id}")
    public ResponseEntity<?> findReservationsByCustomerId(@PathVariable("customer-id") Integer customerId) {
        List<Reservation> customerReservations = reservationService.findReservationsByCustomerId(customerId);
        return new ResponseEntity<>(customerReservations, HttpStatus.OK);
    }

    // Accessible by customer
    // Add new reservation
    @Operation(
            summary = "Creates a new reservation for a specific customer."
    )
    @ApiResponse(
            responseCode = "201",
            content = @Content(mediaType = "application/json",
                   schema = @Schema(implementation = Reservation.class))
    )
    @PostMapping("/api/customer/{customer-id}/reservations")
    public ResponseEntity<?> saveReservation(@PathVariable("customer-id") Integer customerId,
                                             @Valid @RequestBody ReservationDTO dto) {
        Reservation reservation = reservationService.saveReservation(customerId, dto);

        return new ResponseEntity<>(reservation, HttpStatus.CREATED);
    }

    // Accessible by customer
    // Cancel reservation
    @Operation(
            description = "Returns 200 when the reservation is successfully cancelled.",
            summary = "Cancels an existing reservation."
    )
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
