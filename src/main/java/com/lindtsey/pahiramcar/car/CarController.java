package com.lindtsey.pahiramcar.car;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
public class CarController {

    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    // No authentication/authorization required
    // Get all of the cars available
    @GetMapping("/api/cars")
    public ResponseEntity<?> findAllCars() {
        List<Car> cars = carService.findAllCars();

        return new ResponseEntity<>(cars, HttpStatus.OK);
    }

    // Get the available cars
    @GetMapping("/api/cars/available")
    public ResponseEntity<?> findAvailableCars() {
        List<Car> availableCars = carService.findAvailableCars();

        return new ResponseEntity<>(availableCars, HttpStatus.OK);
    }

    // No authentication/authorization required
    // Get the car based on its ID
    @GetMapping("/api/cars/{car-id}")
    public ResponseEntity<?> findAllCars(@PathVariable("car-id") Integer carId) {
        Car car = carService.findCarById(carId);

        return new ResponseEntity<>(car, HttpStatus.OK);
    }

    // Accessible by Administrator
    // Adding new car
    @PostMapping("/api/admin/cars")
    public ResponseEntity<?> addCarWithImages(@RequestPart("car") @Valid CarDTO dto, @RequestPart("images") MultipartFile[] multipartFiles) throws IOException {
        Car savedCar = carService.saveCarWithImages(dto, multipartFiles);

        return new ResponseEntity<>(savedCar, HttpStatus.CREATED);
    }

    // Accessible by Administrator
    // Adding new image for a car
    @PostMapping("/api/admin/cars/{car-id}/images")
    public ResponseEntity<?> uploadImage(@PathVariable("car-id") Integer carId, @RequestPart("images") MultipartFile[] multipartFiles) throws IOException {

        Car car = carService.saveImages(carId, multipartFiles);
        return new ResponseEntity<>(car, HttpStatus.CREATED);
    }

    // Accessible by Administrator
    // Delete a car
    @DeleteMapping("/api/admin/cars/{car-id}")
    public ResponseEntity<?> deleteCarById(@PathVariable("car-id") Integer carId) {
        carService.deleteCarById(carId);
        return new ResponseEntity<>("Car has been deleted successfully", HttpStatus.OK);
    }

    // Accessible by Administrator
    // Delete a car image
    @DeleteMapping("/api/admin/cars/images/{image-id}")
    public ResponseEntity<?> deleteCarImageById(@PathVariable("image-id") Integer imageId) throws IOException {
        carService.deleteImage(imageId);

        return new ResponseEntity<>("Car image has been deleted successfully", HttpStatus.OK);
    }
}
