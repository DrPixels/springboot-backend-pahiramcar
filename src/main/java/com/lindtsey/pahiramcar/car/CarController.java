package com.lindtsey.pahiramcar.car;

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

    @GetMapping("/api/cars")
    public ResponseEntity<?> findAllCars() {
        List<Car> cars = carService.findAllCars();

        return new ResponseEntity<>(cars, HttpStatus.OK);
    }

    @GetMapping("/api/cars/{car-id}")
    public ResponseEntity<?> findAllCars(@PathVariable("car-id") Integer carId) {
        Car car = carService.findCarById(carId);

        return new ResponseEntity<>(car, HttpStatus.OK);
    }


    @PostMapping("/api/cars")
    public ResponseEntity<?> addCarWithImages(@RequestPart("car") Car car, @RequestPart("images") MultipartFile[] multipartFiles) throws IOException {
        Car savedCar = carService.saveCarWithImages(car, multipartFiles);

        return new ResponseEntity<>(savedCar, HttpStatus.CREATED);
    }

    @DeleteMapping("/api/cars/{car-id}")
    public ResponseEntity<?> deleteCarById(@PathVariable("car-id") Integer carId) {
        carService.deleteCarById(carId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
