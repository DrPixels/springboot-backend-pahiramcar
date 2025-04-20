package com.lindtsey.pahiramcar.car;

import com.lindtsey.pahiramcar.carimages.CarImage;
import com.lindtsey.pahiramcar.carimages.CarImageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

@Service
public class CarService {

    private final CarRepository carRepository;
    private final CarImageService carImageService;

    public CarService(CarRepository carRepository, CarImageService carImageService) {
        this.carRepository = carRepository;
        this.carImageService = carImageService;
    }

    public List<Car> findAllCars() {
        return carRepository.findAll();
    }

    @Transactional
    public Car saveCarWithImages(Car car, MultipartFile[] multipartFiles) throws IOException {
        Car savedCar = carRepository.save(car);
        Integer carId = savedCar.getCarId();

        List<CarImage> carImages = carImageService.saveCarImages(carId, multipartFiles);

        savedCar.setCarImages(carImages);

        return savedCar;
    }

    public Car findCarById(int id) {
        return carRepository.findById(id).orElse(new Car());
    }

    public void deleteCarById(int id) {
        carRepository.deleteById(id);
    }
}
