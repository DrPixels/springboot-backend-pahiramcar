package com.lindtsey.pahiramcar.bookings;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lindtsey.pahiramcar.transactions.childClass.bookingPayment.BookingPaymentTransactionDTO;
import com.lindtsey.pahiramcar.utils.shared.DriversLicenseNumber;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@Tag(name = "Booking")
public class BookingController {

    private final BookingService bookingService;
    private final ObjectMapper objectMapper;
    private final Validator validator;

    public BookingController(BookingService bookingService, ObjectMapper objectMapper, Validator validator) {
        this.bookingService = bookingService;
        this.objectMapper = objectMapper;
        this.validator = validator;
    }

    // Accessible by Employee
    // To get all of the bookings
    @Operation(
            summary = "Retrieves a list of all bookings in the system."
    )
    @ApiResponse(
            responseCode = "200",
            content = @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = Booking.class)))
    )
    @GetMapping("/api/employee/bookings")
    public ResponseEntity<List<Booking>> findAllBookings() {
        List<Booking> bookings = bookingService.findAllBooking();
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }

    // Accessible by Employee
    // Check if the driver's license is being used in the booking
    @Operation(
            description = "Returns 200 if the driver's license is not currently being used in booking. Returns 400 if its being used.",
            summary = "Checks if a driver's license number is currently being used in any active booking."
    )
    @PostMapping("/api/employee/booking/check-driver-license-number")
    public ResponseEntity<?> isDriverLicenseCurrentlyUsedInBooking(@RequestBody DriversLicenseNumber driversLicenseNumber) {

        bookingService.isDriverLicenseCurrentlyUsedInBooking(driversLicenseNumber.getDriverLicenseNumber());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(
            description = "Returns the booking by start date. The most recent bookings will come first.",
            summary = "Retrieves all bookings associated with a specific customer using its ID."
    )
    @ApiResponse(
            responseCode = "200",
            content = @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = Booking.class)))
    )
    // Accessible by the Customer
    @GetMapping("/api/customer/{customer-id}/bookings")
    public ResponseEntity<?> findBookingByCustomerId(@PathVariable("customer-id") Integer customerId) {

        List<Booking> customerBookings = this.bookingService.findBookingByCustomerId(customerId);

        return new ResponseEntity<>(customerBookings, HttpStatus.OK);
    }

    @Operation(
            description = "Returns the booking by start date. The most recent bookings will come first.",
            summary = "Retrieves all completed bookings for a specific customer using its ID."
    )
    @ApiResponse(
            responseCode = "200",
            content = @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = Booking.class)))
    )
    @GetMapping("/api/customer/bookings/{customer-id}/complete")
    public ResponseEntity<?> findCompletedBookingByCustomerId(@PathVariable("customer-id") Integer customerId) {

        List<Booking> customerBookings = this.bookingService.findCompletedBookingsByCustomerId(customerId);

        return new ResponseEntity<>(customerBookings, HttpStatus.OK);
    }



//    @GetMapping("/api/bookings/{car-id}")
//    public ResponseEntity<?> findBookingByCarId(@PathVariable("car-id") Integer carId) {
//
//        List<Booking> carBookings = this.bookingService.findBookingByCarId(carId);
//
//        return new ResponseEntity<>(carBookings, HttpStatus.OK);
//    }

    @Operation(
            description = "When saving a new booking, you don't need to separately save the booking images. You can just pass it all once",
            summary = "Creates a new booking along with associated payment transaction and proof images."
    )
    @ApiResponse(
            responseCode = "200",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Booking.class))
    )
    // Accessible by Employee
    // Add new booking
    @PostMapping("/api/employee/bookings/add-booking")
    public ResponseEntity<?> saveBooking(@RequestPart("bookingJson") String bookingJson,
                                         @RequestPart("transactionJson") String bookingPaymentTransactionJson,
                                         @RequestPart("images") MultipartFile[] multipartFiles) throws IOException {


        BookingDTO bookingDTO = objectMapper.readValue(bookingJson, BookingDTO.class);
        BookingPaymentTransactionDTO transactionDTO = objectMapper.readValue(bookingPaymentTransactionJson, BookingPaymentTransactionDTO.class);

        Set<ConstraintViolation<BookingDTO>> bookingViolations = validator.validate(bookingDTO);

        if (!bookingViolations.isEmpty()) {
            String errorMessage = bookingViolations.stream()
                    .map(violation -> violation.getMessage())
                    .collect(Collectors.joining(", "));
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }

        Set<ConstraintViolation<BookingPaymentTransactionDTO>> transactionViolations = validator.validate(transactionDTO);

        if (!transactionViolations.isEmpty()) {
            String errorMessage = transactionViolations.stream()
                    .map(violation -> violation.getMessage())
                    .collect(Collectors.joining(", "));
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }

        Booking booking = bookingService.saveWithBookingProofImages(bookingDTO, transactionDTO, multipartFiles);

        return new ResponseEntity<>(booking, HttpStatus.CREATED);
    }

    // Accessible by Employee
    // Add new booking images
    @Operation(
            summary = "Adds new proof images to an existing booking."
    )
    @ApiResponse(
            responseCode = "200",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Booking.class))
    )
    @PostMapping("/api/employee/bookings/{booking-id}/images")
    public ResponseEntity<?> saveBookingImage(@PathVariable("booking-id") Integer bookingId, @RequestPart("images")MultipartFile[] multipartFiles) throws IOException {

        Booking booking = bookingService.saveBookingImages(bookingId, multipartFiles);

        return new ResponseEntity<>(booking, HttpStatus.CREATED);
    }

    // Accessible by Employee
    // Return the car
    @Operation(
            description = "Returns 200 with a successful message if the car has been successfully returned",
            summary = "Marks a booking as completed, indicating that the car has been returned."
    )
    @ApiResponse(
            responseCode = "200",
            content = @Content(mediaType = "application/json")
    )
    @PutMapping("/api/employee/bookings/{booking-id}/return-car")
    public ResponseEntity<?> returnCar(@PathVariable("booking-id") Integer bookingId,
                                       @RequestBody Map<String, Float> request) {

        float afterMileage = request.get("afterMileage");

        bookingService.returnCar(bookingId, afterMileage);

        return new ResponseEntity<>("Booking was successfully completed.", HttpStatus.OK);
    }

    // Accessible by Employee
    // Delete a booking
    @Hidden
    @DeleteMapping("/api/employee/bookings/{booking-id}")
    public ResponseEntity<?> deleteBooking(@PathVariable("booking-id") Integer bookingId) {
        bookingService.deleteBookingById(bookingId);
        return new ResponseEntity<>("Booking was successfully deleted.", HttpStatus.OK);
    }

    // Accessible by employee
    // Delete a booking image
    @Operation(
            description = "Returns 200 with a successful message if the booking image has been successfully deleted",
            summary = "Deletes a specific booking image by its ID."
    )
    @ApiResponse(
            responseCode = "200",
            content = @Content(mediaType = "application/json")
    )
    @DeleteMapping("/api/employee/bookings/{image-id}")
    public ResponseEntity<?> deleteBookingImageById(@PathVariable("image-id") Integer imageId) throws IOException {
        bookingService.deleteImage(imageId);

        return new ResponseEntity<>("Booking image was successfully deleted.", HttpStatus.OK);
    }
}
