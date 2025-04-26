package com.lindtsey.pahiramcar.bookings;

import com.lindtsey.pahiramcar.transactions.TransactionDTO;
import jakarta.validation.Valid;
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

    // Accessible by Employee
    // To get all of the bookings
    @GetMapping("/api/employee/bookings")
    public ResponseEntity<List<Booking>> findAllBookings() {
        List<Booking> bookings = bookingService.findAllBooking();
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }

    // Accessible by Employee
    // Check if the driver's license is being used in the booking
    @PostMapping("/api/employee/booking/{driver-license-number}")
    public ResponseEntity<?> isDriverLicenseCurrentlyUsedInBooking(@PathVariable("driver-license-number") String driverLicenseNumber) {
        bookingService.isDriverLicenseCurrentlyUsedInBooking(driverLicenseNumber);


        return new ResponseEntity<>( HttpStatus.OK);
    }

    // Accessible by the Customer
    @GetMapping("/api/customer/bookings/{customer-id}")
    public ResponseEntity<?> findBookingByCustomerId(@PathVariable("customer-id") Integer customerId) {

        List<Booking> customerBookings = this.bookingService.findBookingByCustomerId(customerId);

        return new ResponseEntity<>(customerBookings, HttpStatus.OK);
    }

//    @GetMapping("/api/bookings/{car-id}")
//    public ResponseEntity<?> findBookingByCarId(@PathVariable("car-id") Integer carId) {
//
//        List<Booking> carBookings = this.bookingService.findBookingByCarId(carId);
//
//        return new ResponseEntity<>(carBookings, HttpStatus.OK);
//    }

    // Accessible by Employee
    // Add new booking
    @PostMapping("/api/bookings")
    public ResponseEntity<?> saveBooking(@RequestPart("booking") @Valid BookingDTO bookingDTO,
                                         @RequestPart("transaction") @Valid TransactionDTO transactionDTO,
                                         @RequestPart("images") MultipartFile[] multipartFiles) throws IOException {

        Booking booking = bookingService.saveWithBookingProofImages(bookingDTO, transactionDTO, multipartFiles);

        return new ResponseEntity<>(booking, HttpStatus.CREATED);
    }

    // Accessible by Employee
    // Add new booking images
    @PostMapping("/api/employee/bookings/{booking-id}/images")
    public ResponseEntity<?> saveBookingImage(@PathVariable("booking-id") Integer bookingId, @RequestPart("images")MultipartFile[] multipartFiles) throws IOException {

        Booking booking = bookingService.saveBookingImages(bookingId, multipartFiles);

        return new ResponseEntity<>(booking, HttpStatus.CREATED);
    }

    // Accessible by Employee
    // Return the car
    @PutMapping("/api/employee/bookings/{booking-id}/return-car")
    public ResponseEntity<?> returnCar(@PathVariable("booking-id") Integer bookingId) {
        bookingService.returnCar(bookingId);

        return new ResponseEntity<>("Booking was successfully completed.", HttpStatus.OK);
    }

    // Accessible by Employee
    // Delete a booking
    @DeleteMapping("/api/employee/bookings/{booking-id}")
    public ResponseEntity<?> deleteBooking(@PathVariable("booking-id") Integer bookingId) {
        bookingService.deleteBookingById(bookingId);
        return new ResponseEntity<>("Booking was successfully deleted.", HttpStatus.OK);
    }

    // Accessible by employee
    // Delete a booking image
    @DeleteMapping("/api/employee/bookings/{image-id}")
    public ResponseEntity<?> deleteCarImageById(@PathVariable("image-id") Integer imageId) throws IOException {
        bookingService.deleteImage(imageId);

        return new ResponseEntity<>("Booking image was successfully deleted.", HttpStatus.OK);
    }
}
