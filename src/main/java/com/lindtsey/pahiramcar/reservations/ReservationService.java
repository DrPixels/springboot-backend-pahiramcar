package com.lindtsey.pahiramcar.reservations;

import com.lindtsey.pahiramcar.car.Car;
import com.lindtsey.pahiramcar.car.CarRepository;
import com.lindtsey.pahiramcar.customer.Customer;
import com.lindtsey.pahiramcar.customer.CustomerRepository;
import com.lindtsey.pahiramcar.enums.ReservationStatus;
import com.lindtsey.pahiramcar.utils.CarAlreadyReservedException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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

        if(isCarReserved(dto.carId(), dto.reservationStartDate(), dto.reservationEndDate(), ReservationStatus.AVAILABLE)) {
            throw new CarAlreadyReservedException("Car is already reserved for the selected dates!");
        }

        Reservation reservation = toReservation(dto);
        reservation.setStatus(ReservationStatus.AVAILABLE);
        return reservationRepository.save(reservation);
    }

    public void updateStatus(Integer reservationId, String newStatus) {
        Reservation reservation = reservationRepository.findById(reservationId).orElseThrow(() -> new RuntimeException("Reservation not found"));

        reservation.setStatus(ReservationStatus.valueOf(newStatus.toUpperCase()));

        reservationRepository.save(reservation);
    }

    private Reservation toReservation(ReservationDTO dto) {
        Reservation reservation = new Reservation();
        reservation.setReservationStartDate(dto.reservationStartDate());
        reservation.setReservationEndDate(dto.reservationEndDate());

        Car car = carRepository.findById(dto.carId()).orElseThrow(() -> new RuntimeException("Car not found"));
        Customer customer = customerRepository.findById(dto.customerId()).orElseThrow(() -> new RuntimeException("Customer not found"));

        reservation.setCustomer(customer);
        reservation.setCar(car);

        return reservation;
    }

    private boolean isCarReserved(Integer carId, LocalDateTime startDate, LocalDateTime endDate, ReservationStatus status) {
        return reservationRepository.isCarReserved(carId, startDate, endDate, status);
    }

    //For auto-updating the status of the reservation once they are expired
    @Transactional
    @Scheduled(fixedDelayString = "PT1M")
    protected void updatedExpiredReservation() {
        LocalDateTime now = LocalDateTime.now();
        reservationRepository.updatedExpiredReservation(now,
                ReservationStatus.AVAILABLE,
                ReservationStatus.EXPIRED);
    }

}
