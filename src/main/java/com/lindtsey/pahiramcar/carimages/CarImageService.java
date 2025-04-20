package com.lindtsey.pahiramcar.carimages;

import com.lindtsey.pahiramcar.car.Car;
import com.lindtsey.pahiramcar.car.CarRepository;
import com.lindtsey.pahiramcar.cloudinary.CloudinaryService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CarImageService {

    private final CarImageRepository carImageRepository;
    private final CarRepository carRepository;
    private final CloudinaryService cloudinaryService;

    public CarImageService(CarImageRepository carImageRepository, CarRepository carRepository, CloudinaryService cloudinaryService) {
        this.carImageRepository = carImageRepository;
        this.carRepository = carRepository;
        this.cloudinaryService = cloudinaryService;
    }

    public List<CarImage> findAllCarImageByCarId(Integer carId) {
        return carImageRepository.findCarImageByCar_CarId(carId);
    }

    public Optional<CarImage> findCarImageById(Integer carId) {
        return carImageRepository.findById(carId);
    }

    public List<CarImage> saveCarImages(Integer carId, MultipartFile[] multipartFiles) throws IOException {

        List<CarImage> carImages = new ArrayList<>();
        for (MultipartFile file: multipartFiles) {
            //Uploading the image in the Cloudinary
            BufferedImage bufferedImage = ImageIO.read(file.getInputStream());
            if( bufferedImage == null ) {
                throw new IOException("Failed to read the image.");
            }

            Map result = cloudinaryService.upload(file);
            String carImageName = (String) result.get("original_filename");
            String carImageUrl = (String) result.get("url");
            String carImageCloudinaryId = (String) result.get("public_id");

            CarImage carImage = new CarImage(carImageName, carImageUrl, carImageCloudinaryId);
            Car car = carRepository.findById(carId).orElseThrow(() -> new RuntimeException("Car not found"));
            carImage.setCar(car);

            carImageRepository.save(carImage);
            carImages.add(carImage);

        }
        return carImages;
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
