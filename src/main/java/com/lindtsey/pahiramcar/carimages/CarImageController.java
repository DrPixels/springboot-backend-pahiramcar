package com.lindtsey.pahiramcar.carimages;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
public class CarImageController {

    private final CarImageService carImageService;

    public CarImageController(CarImageService carImageService) {
        this.carImageService = carImageService;
    }

    @PostMapping("/api/car/images")
    public ResponseEntity<?> saveCarImages(Integer carId, @RequestPart("images") MultipartFile[] multipartFiles) throws IOException {

        List<CarImage> carImages = carImageService.saveCarImages(carId, multipartFiles);
        return new ResponseEntity<>(carImages, HttpStatus.CREATED);
    }

    @DeleteMapping("/api/car/images/{image-id}")
    public ResponseEntity<?> deleteCarImage(@PathVariable("image-id") Integer imageId) {
        carImageService.deleteCarImageById(imageId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
