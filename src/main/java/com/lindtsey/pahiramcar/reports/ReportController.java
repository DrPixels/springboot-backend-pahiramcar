package com.lindtsey.pahiramcar.reports;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReportController {

    private ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/api/customers/{customer_id}/active-reservations/count")
    public ResponseEntity<?> countTotalCustomerActiveReservation(@PathVariable("customer_id") Integer customerId) {
        int totalCustomerActiveReservation = reportService.countCustomerTotalActiveReservation(customerId);

        return new ResponseEntity<>(totalCustomerActiveReservation, HttpStatus.OK);
    }

    @GetMapping("/api/customers/{customer_id}/active-bookings/count")
    public ResponseEntity<?> countTotalCustomerActiveBookings(@PathVariable("customer_id") Integer customerId) {
        int totalCustomerActiveBookings = reportService.countCustomerTotalActiveRentals(customerId);

        return new ResponseEntity<>(totalCustomerActiveBookings, HttpStatus.OK);
    }

    @GetMapping("/api/customers/{customer_id}/active-bookings/count")
    public ResponseEntity<?> countTotalCustomerCompletedBookings(@PathVariable("customer_id") Integer customerId) {
        int totalCustomerCompletedBookings = reportService.countCustomerTotalCompletedRentals(customerId);

        return new ResponseEntity<>(totalCustomerCompletedBookings, HttpStatus.OK);
    }
}
