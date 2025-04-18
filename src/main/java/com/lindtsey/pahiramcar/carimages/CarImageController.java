package com.lindtsey.pahiramcar.carimages;

import com.lindtsey.pahiramcar.car.Car;
import com.lindtsey.pahiramcar.cloudinary.CloudinaryService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class CarImageController {

    private final CarImageService carImageService;

    public CarImageController(CarImageService carImageService) {
        this.carImageService = carImageService;
    }

//    @PostMapping("/car/{car-id}/images")
//    public CarImage saveCarImage(@PathVariable("car-id") Integer carId, @RequestBody CarImageDTO carImageDTO) throws IOException {
//        CarImage savedCarImage = this.carImageService.toCarImage(carImageDTO);
//
//        return carImageService.save(savedCarImage);
//    }

}
