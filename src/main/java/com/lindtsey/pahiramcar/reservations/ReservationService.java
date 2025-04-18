package com.lindtsey.pahiramcar.reservations;

import com.lindtsey.pahiramcar.car.Car;
import com.lindtsey.pahiramcar.car.CarRepository;
import com.lindtsey.pahiramcar.customer.Customer;
import com.lindtsey.pahiramcar.customer.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final CarRepository carRepository;
    private final CustomerRepository customerRepository;

    public ReservationService(ReservationRepository reservationRepository, CarRepository carRepository, CustomerRepository customerRepository) {
        this.reservationRepository = reservationRepository;
        this.carRepository = carRepository;
        this.customerRepository = customerRepository;
    }


    public List<Reservation> findReservationsByCustomerId(Integer customerId) {
        return reservationRepository.findReservationsByCustomer_CustomerId(customerId);
    }

    public void deleteReservation(Integer reservationId) {
        reservationRepository.deleteById(reservationId);
    }

    public Reservation saveReservation(ReservationDTO dto) {

        Reservation reservation = toReservation(dto);
        return reservationRepository.save(reservation);
    }

    private Reservation toReservation(ReservationDTO dto) {
        Reservation reservation = new Reservation();
        reservation.setReservationStartDate(dto.reservationStartDate());
        reservation.setReservationEndDate(dto.reservationEndDate());
        reservation.setStatus(dto.status());

        Car car = carRepository.findById(dto.carId()).orElseThrow(() -> new RuntimeException("Car not found"));
        Customer customer = customerRepository.findById(dto.customerId()).orElseThrow(() -> new RuntimeException("Customer not found"));

        reservation.setCustomer(customer);
        reservation.setCar(car);

        return reservation;
    }

}
