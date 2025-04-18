package com.lindtsey.pahiramcar.carimages;

import com.lindtsey.pahiramcar.car.Car;
import com.lindtsey.pahiramcar.car.CarService;
import com.lindtsey.pahiramcar.cloudinary.CloudinaryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class CarImageService {

    private final CarImageRepository carImageRepository;

    public CarImageService(CarImageRepository carImageRepository) {
        this.carImageRepository = carImageRepository;
    }

    public List<CarImage> findAllCarImageByCarId(Integer carId) {
        return carImageRepository.findCarImageByCar_CarId(carId);
    }

    public Optional<CarImage> findCarImageById(Integer carId) {
        return carImageRepository.findById(carId);
    }

    public CarImage save(CarImage carImage) throws IOException {

        return carImageRepository.save(carImage);
    }

    public void deleteCarImageById(Integer carImageId) {
        carImageRepository.deleteById(carImageId);
    }

    public boolean exists(Integer carImageId) {
        return carImageRepository.existsById(carImageId);
    }

    public CarImage toCarImage(CarImageDTO dto) {
        var carImage = new CarImage();
        carImage.setImageName(dto.imageName());
        carImage.setImageUrl(dto.imageUrl());
        carImage.setCloudinaryImageId(dto.cloudinaryImageId());

        var car = new Car();
        car.setCarId(dto.carId());

        carImage.setCar(car);

        return carImage;
    }

}
