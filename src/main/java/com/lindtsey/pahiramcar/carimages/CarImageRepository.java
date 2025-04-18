package com.lindtsey.pahiramcar.carimages;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarImageRepository extends JpaRepository<CarImage, Integer> {

    List<CarImage> findCarImageByCar_CarId(Integer carId);
}
