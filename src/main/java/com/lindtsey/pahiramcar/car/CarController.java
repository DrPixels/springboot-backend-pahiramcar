package com.lindtsey.pahiramcar.car;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@Tag(name = "Car")
public class CarController {

    private final CarService carService;
    private final ObjectMapper objectMapper;
    private final Validator validator;

    public CarController(CarService carService, ObjectMapper objectMapper, Validator validator) {
        this.carService = carService;
        this.objectMapper = objectMapper;
        this.validator = validator;
    }

    // No authentication/authorization required
    // Get all of the cars available
    @Operation(
            description = "Returns the cars based on its status. AVAILABLE -> RESERVED -> BOOKED -> ARCHIVED",
            summary = "Retrieves a list of all cars in the system."
    )
    @ApiResponse(
            responseCode = "200",
            content = @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = Car.class)))
    )
    @GetMapping("/api/admin/cars")
    public ResponseEntity<?> findAllCars() {
        List<Car> cars = carService.findAllCars();

        return new ResponseEntity<>(cars, HttpStatus.OK);
    }

    @Operation(
            summary = "Retrieves a list of all cars that are currently available for reservation."
    )
    @ApiResponse(
            responseCode = "200",
            content = @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = Car.class)))
    )
    // Get the available cars
    @GetMapping("/api/cars/available")
    public ResponseEntity<?> findAvailableCars() {

        List<Car> availableCars = carService.findAvailableCars();

        return new ResponseEntity<>(availableCars, HttpStatus.OK);
    }

    // No authentication/authorization required
    // Get the car based on its ID
    @Operation(
            summary = "Retrieves details of a specific car based on its ID."
    )
    @ApiResponse(
            responseCode = "200",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Car.class))
    )
    @GetMapping("/api/cars/{car-id}")
    public ResponseEntity<?> findCarById(@PathVariable("car-id") Integer carId) {
        Car car = carService.findCarById(carId);

        return new ResponseEntity<>(car, HttpStatus.OK);
    }

    // Accessible by Administrator
    // Adding new car
    @Operation(
            description = "When adding a new car, you don't need to separately save the images, just pass it all once",
            summary = "Adds a new car to the system along with associated images."
    )
    @ApiResponse(
            responseCode = "201",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Car.class))
    )
    @PostMapping(value = "/api/admin/cars", consumes = "multipart/form-data")
    public ResponseEntity<?> addCarWithImages(@RequestPart("carJson") String carJson, @RequestPart("images") MultipartFile[] multipartFiles) throws IOException {

        CarDTO dto = objectMapper.readValue(carJson, CarDTO.class);

        Set<ConstraintViolation<CarDTO>> violations = validator.validate(dto);

        if (!violations.isEmpty()) {
            String errorMessage = violations.stream()
                    .map(violation -> violation.getMessage())
                    .collect(Collectors.joining(", "));
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }

        Car savedCar = carService.saveCarWithImages(dto, multipartFiles);

        return new ResponseEntity<>(savedCar, HttpStatus.CREATED);
    }
//    @PostMapping(value = "/api/admin/cars", consumes = "multipart/form-data")
//    public ResponseEntity<?> addCarWithImages(@RequestPart("car") @Valid CarDTO dto, @RequestPart("images") MultipartFile[] multipartFiles) throws IOException {
//        Car savedCar = carService.saveCarWithImages(dto, multipartFiles);
//
//        return new ResponseEntity<>(savedCar, HttpStatus.CREATED);
//    }

    // Accessible by Administrator
    // Adding new image for a car
    @Operation(
            summary = "Uploads new images for an existing car."
    )
    @ApiResponse(
            responseCode = "200",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Car.class))
    )
    @PostMapping("/api/admin/cars/{car-id}/images")
    public ResponseEntity<?> uploadImage(@PathVariable("car-id") Integer carId, @RequestPart("images") MultipartFile[] multipartFiles) throws IOException {

        Car car = carService.saveImages(carId, multipartFiles);
        return new ResponseEntity<>(car, HttpStatus.CREATED);
    }

    // Accessible by Administrator
    // Set the car as inactive
    @Operation(
            description = "Returns 200 when the car has been archived successfully.",
            summary = "Marks a car as inactive (archived)."
    )
    @PatchMapping("/api/admin/cars/{car-id}/archive")
    public ResponseEntity<?> archiveCarById(@PathVariable("car-id") Integer carId) {
        carService.archiveCar(carId);
        return new ResponseEntity<>("Car has been archived successfully", HttpStatus.OK);
    }

    // Accessible by Administrator
    // Set the car as active
    @Operation(
            description = "Returns 200 when the car has been unarchived successfully.",
            summary = "Marks a car as active (unarchived)."
    )
    @PatchMapping("/api/admin/cars/{car-id}/unarchive")
    public ResponseEntity<?> unarchiveCarById(@PathVariable("car-id") Integer carId) {
        carService.unarchiveCar(carId);
        return new ResponseEntity<>("Car has been unarchived successfully", HttpStatus.OK);
    }

    @Operation(
            summary = "Updates the details of an existing car."
    )
    @ApiResponse(
            responseCode = "200",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Car.class))

    )
    @PutMapping("/api/admin/cars/{car-id}/edit")
    public ResponseEntity<?> editCarById(@PathVariable("car-id") Integer carId,
                                         @RequestBody CarDTO dto) {
        Car editedCar = carService.saveCarEdit(carId, dto);

        return new ResponseEntity<>(editedCar, HttpStatus.OK);
    }

    // Accessible by Administrator
    // Delete a car image
    @Operation(
            description = "Returns 200 when the car image has been deleted successfully.",
            summary = "Deletes a specific image associated with a car."
    )
    @DeleteMapping("/api/admin/cars/{car-id}/images/{image-id}")
    public ResponseEntity<?> deleteCarImageById(@PathVariable("car-id") Integer carId,
                                                @PathVariable("image-id") Integer imageId) throws IOException {
        carService.deleteImage(carId, imageId);

        return new ResponseEntity<>("Car image has been deleted successfully", HttpStatus.OK);
    }
}
