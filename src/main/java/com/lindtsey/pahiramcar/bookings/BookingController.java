package com.lindtsey.pahiramcar.bookings;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
public class BookingController {

    private final BookingService bookingService;
    private final BookingRepository bookingRepository;

    public BookingController(BookingService bookingService, BookingRepository bookingRepository) {
        this.bookingService = bookingService;
        this.bookingRepository = bookingRepository;
    }

    @GetMapping("/api/bookings")
    public ResponseEntity<List<Booking>> findAllBookings() {
        List<Booking> bookings = bookingService.findAllBooking();
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }

    @GetMapping("/api/bookings/{customer-id}")
    public ResponseEntity<?> findBookingByCustomerId(@PathVariable("customer-id") Integer customerId) {

        List<Booking> customerBookings = this.bookingService.findBookingByCustomerId(customerId);

        return new ResponseEntity<>(customerBookings, HttpStatus.OK);
    }

    @GetMapping("/api/bookings/{car-id}")
    public ResponseEntity<?> findBookingByCarId(@PathVariable("car-id") Integer carId) {

        List<Booking> carBookings = this.bookingService.findBookingByCarId(carId);

        return new ResponseEntity<>(carBookings, HttpStatus.OK);
    }

    @PostMapping("/api/bookings")
    public ResponseEntity<?> saveBooking(@RequestPart("booking") BookingDTO bookingDTO, @RequestPart("images")MultipartFile[] multipartFiles) throws IOException {
        Booking booking = bookingService.saveWithBookingProofImages(bookingDTO, multipartFiles);

        return new ResponseEntity<>(booking, HttpStatus.CREATED);
    }

    @PostMapping("/api/bookings/{booking-id}/images")
    public ResponseEntity<?> saveBookingImage(@PathVariable("booking-id") Integer bookingId, @RequestPart("images")MultipartFile[] multipartFiles) throws IOException {
        Booking booking = bookingService.saveBookingImages(bookingId, multipartFiles);

        return new ResponseEntity<>(booking, HttpStatus.CREATED);
    }

    @DeleteMapping("/api/bookings/{booking-id}")
    public ResponseEntity<?> deleteBooking(@PathVariable("booking-id") Integer bookingId) {
        bookingService.deleteBookingById(bookingId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/api/bookings/{image-id}")
    public ResponseEntity<?> deleteCarImageById(@PathVariable("image-id") Integer imageId) throws IOException {
        bookingService.deleteImage(imageId);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
