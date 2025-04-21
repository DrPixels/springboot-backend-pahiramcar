package com.lindtsey.pahiramcar.car;

import com.lindtsey.pahiramcar.enums.ImageOwnerType;
import com.lindtsey.pahiramcar.images.Image;
import com.lindtsey.pahiramcar.images.ImageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

@Service
public class CarService {

    private final CarRepository carRepository;
    private final ImageService imageService;

    public CarService(CarRepository carRepository, ImageService imageService) {
        this.carRepository = carRepository;
        this.imageService = imageService;
    }

    public List<Car> findAllCars() {
        return carRepository.findAll();
    }

    @Transactional
    public Car saveCarWithImages(CarDTO dto, MultipartFile[] multipartFiles) throws IOException {
        Car car = toCar(dto);
        Car savedCar = carRepository.save(car);
        Integer carId = savedCar.getCarId();

        List<Image> carImages = imageService.saveImages(multipartFiles, ImageOwnerType.CAR, carId);

        savedCar.setCarImages(carImages);

        return savedCar;
    }

    public Car saveImages(Integer carId, MultipartFile[] multipartFiles) throws IOException {
        Car car = carRepository.findById(carId).orElseThrow(() -> new RuntimeException("Car not found"));

        List<Image> savedCarImages = imageService.saveImages(multipartFiles, ImageOwnerType.CAR, carId);

        car.getCarImages().addAll(savedCarImages);

        return car;
    }

    public void deleteImage(Integer imageId) throws IOException {
        imageService.deleteImage(imageId);
    }

    public Car findCarById(int id) {
        return carRepository.findById(id).orElse(new Car());
    }

    public void deleteCarById(int id) {
        carRepository.deleteById(id);
    }

    public Car toCar(CarDTO dto) {
        Car car = new Car();
        car.setName(dto.name());
        car.setYear(dto.year());
        car.setPlateNumber(dto.plateNumber());
        car.setCarType(dto.carType());
        car.setTransmissionType(dto.transmissionType());
        car.setFuelType(dto.fuelType());
        car.setSeats(dto.seats());
        car.setPricePerDay(dto.pricePerDay());
        car.setStatus(dto.status());
        car.setDescription(dto.description());

        return car;
    }
}
