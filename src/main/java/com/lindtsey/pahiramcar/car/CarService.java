package com.lindtsey.pahiramcar.car;

import com.lindtsey.pahiramcar.enums.CarStatus;
import com.lindtsey.pahiramcar.enums.ImageOwnerType;
import com.lindtsey.pahiramcar.images.Image;
import com.lindtsey.pahiramcar.images.ImageService;
import com.lindtsey.pahiramcar.utils.exceptions.CarHasBookingCannotDeleteException;
import com.lindtsey.pahiramcar.utils.exceptions.CarMustHaveAtLeastOneImage;
import com.lindtsey.pahiramcar.utils.sorter.CarSorter;
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
        List<Car> allCars = carRepository.findAll();

        CarSorter.mergeSortBookings(allCars);

        return allCars;
    }

    public List<Car> findAvailableCars() {
        List<Car> availableCars = carRepository.findCarsByStatusAndIsArchived(CarStatus.AVAILABLE, false);

        // Sorts the bookings based on their start date and status
        // Refer to the BookingStatus enum for ordering
        CarSorter.mergeSortBookings(availableCars);

        return availableCars;
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

    public void deleteImage(Integer carId, Integer imageId) throws IOException {

        // We need to ensure that we will still have at least 1 picture
        Car car = carRepository.findById(carId).orElseThrow(() -> new RuntimeException("Car not found"));
        if(car.getCarImages().size() == 1) {
            throw new CarMustHaveAtLeastOneImage();
        }

        imageService.deleteImage(imageId);
    }

    public Car saveCarEdit(Integer carId, CarDTO dto)  {
        Car car = carRepository.findById(carId).orElseThrow(() -> new RuntimeException("Car not found"));

        car.setName(dto.name());
        car.setYear(dto.year());
        car.setMileage(dto.mileage());
        car.setPlateNumber(dto.plateNumber());
        car.setCarType(dto.carType());
        car.setTransmissionType(dto.transmissionType());
        car.setFuelType(dto.fuelType());
        car.setEngineNumber(dto.engineNumber());
        car.setChassisNumber(dto.chassisNumber());
        car.setSeats(dto.seats());
        car.setPricePerDay(dto.pricePerDay());
        car.setDescription(dto.description());

        return carRepository.save(car);
    }

    public Car findCarById(int id) {
        return carRepository.findById(id).orElse(new Car());
    }

//    public void deleteCarById(int id) {
//
//        // We can delete, if only reservations
//
//
//        // We can't delete if booked
//        if(carRepository.existsCarByCarIdAndStatus(id, CarStatus.BOOKED)) {
//            throw new CarHasBookingCannotDeleteException();
//        }
//
//        carRepository.deleteById(id);
//    }

    public void archiveCar(int id) {
        Car car = carRepository.findById(id).orElseThrow(() -> new RuntimeException("Car not found"));

        car.setArchived(true);

        carRepository.save(car);
    }

    public void unarchiveCar(int id) {
        Car car = carRepository.findById(id).orElseThrow(() -> new RuntimeException("Car not found"));

        car.setArchived(false);

        carRepository.save(car);
    }


    public Car toCar(CarDTO dto) {
        Car car = new Car();
        car.setName(dto.name());
        car.setYear(dto.year());
        car.setMileage(dto.mileage());
        car.setPlateNumber(dto.plateNumber());
        car.setCarType(dto.carType());
        car.setTransmissionType(dto.transmissionType());
        car.setFuelType(dto.fuelType());
        car.setSeats(dto.seats());
        car.setPricePerDay(dto.pricePerDay());
        car.setDescription(dto.description());
        car.setChassisNumber(dto.chassisNumber());
        car.setEngineNumber(dto.engineNumber());

        return car;
    }
}
