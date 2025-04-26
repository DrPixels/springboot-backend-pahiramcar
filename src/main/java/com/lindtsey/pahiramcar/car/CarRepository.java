package com.lindtsey.pahiramcar.car;

import com.lindtsey.pahiramcar.enums.CarStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Integer> {

    boolean existsCarByCarIdAndStatus(Integer carId, CarStatus status);

    List<Car> findCarByStatus(CarStatus status);
}
