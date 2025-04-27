package com.lindtsey.pahiramcar.reports;

import com.lindtsey.pahiramcar.car.Car;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CarPerformance {

    private Car car;
    private long totalBookings;
    private double revenueGenerated;

    public CarPerformance(Car car, long totalBookings) {
        this.car = car;
        this.totalBookings = totalBookings;
    }
}
