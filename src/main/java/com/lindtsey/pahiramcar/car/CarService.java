package com.lindtsey.pahiramcar.car;

import com.lindtsey.pahiramcar.carimages.CarImage;
import com.lindtsey.pahiramcar.carimages.CarImageRepository;
import com.lindtsey.pahiramcar.carimages.CarImageService;
import com.lindtsey.pahiramcar.cloudinary.CloudinaryService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class CarService {

    private final CarRepository carRepository;
    private final CloudinaryService cloudinaryService;
    private final CarImageService carImageService;

    public CarService(CarRepository carRepository, CloudinaryService cloudinaryService, CarImageService carImageService) {
        this.carRepository = carRepository;
        this.cloudinaryService = cloudinaryService;
        this.carImageService = carImageService;
    }

    public List<Car> findAllCars() {
        return carRepository.findAll();
    }

    public Car saveCarWithImages(Car car, MultipartFile[] multipartFiles) throws IOException {
        Car savedCar = carRepository.save(car);
        Integer carId = savedCar.getCarId();

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
            carImage.setCar(savedCar);

            CarImage savedCarImage = carImageService.save(carImage);

            savedCar.getCarImages().add(savedCarImage);
        }

        return savedCar;
    }

    public Car findCarById(int id) {
        return carRepository.findById(id).orElse(new Car());
    }

    public void deleteCarById(int id) {
        carRepository.deleteById(id);
    }
}
