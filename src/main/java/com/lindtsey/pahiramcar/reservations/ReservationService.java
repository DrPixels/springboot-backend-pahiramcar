package com.lindtsey.pahiramcar.reservations;

import com.lindtsey.pahiramcar.car.Car;
import com.lindtsey.pahiramcar.car.CarRepository;
import com.lindtsey.pahiramcar.customer.Customer;
import com.lindtsey.pahiramcar.customer.CustomerRepository;
import com.lindtsey.pahiramcar.enums.CarStatus;
import com.lindtsey.pahiramcar.enums.ReservationStatus;
import com.lindtsey.pahiramcar.utils.sorter.ReservationSorter;
import com.lindtsey.pahiramcar.utils.constants;
import com.lindtsey.pahiramcar.utils.exceptions.CarAlreadyReservedException;
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


        // Unsorted version from the database
        List<Reservation> reservations = reservationRepository.findReservationsByCustomer_UserIdOrderByStartDateTimeAsc(customerId);

        // Sorts the reservations based on their start date and status
        // Refer to the ReservationStatus enum for ordering
        ReservationSorter.mergeSortReservations(reservations);

        return reservations;
    }

    public void deleteReservation(Integer reservationId) {
        reservationRepository.deleteById(reservationId);
    }

    @Transactional
    public Reservation saveReservation(Integer customerId, ReservationDTO dto) {

        // Check if the car is already reserved
        Car car = carRepository.findById(dto.carId()).orElseThrow(() -> new RuntimeException("Car not found"));

        if(car.getStatus() == CarStatus.RESERVED || car.getStatus() == CarStatus.BOOKED) {
            throw new CarAlreadyReservedException();
        }

        car.setStatus(CarStatus.RESERVED);
        carRepository.save(car);

        // Save the reservation with the waiting for approval as default status
        Reservation reservation = toReservation(dto);
        System.out.println("DATEEE");
        System.out.println(reservation.getStartDateTime());
        System.out.println(reservation.getEndDateTime());

        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new RuntimeException("Customer not found"));
        reservation.setCustomer(customer);

        return reservationRepository.save(reservation);
    }

    public void cancelReservation(Integer reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId).orElseThrow(() -> new RuntimeException("Reservation not found"));

        reservation.setStatus(ReservationStatus.CANCELLED);

        reservationRepository.save(reservation);

        Car car = carRepository.findById(reservation.getCar().getCarId()).orElseThrow(() -> new RuntimeException("Car not found"));
        car.setStatus(CarStatus.AVAILABLE);
        carRepository.save(car);
    }

    private Reservation toReservation(ReservationDTO dto) {
        Reservation reservation = new Reservation();
        reservation.setStartDateTime(dto.startDateTime());
//        reservation.setEndDateTime(dto.startDateTime().plusMinutes(5));
        reservation.setEndDateTime(dto.startDateTime().plusDays(constants.PahiramCarConstants.RESERVATION_DAYS));

        Car car = carRepository.findById(dto.carId()).orElseThrow(() -> new RuntimeException("Car not found"));

        reservation.setCar(car);

        return reservation;
    }

    private boolean isCarReserved(Integer carId, LocalDateTime startDate, LocalDateTime endDate) {
        return reservationRepository.isCarReserved(carId, startDate, endDate, ReservationStatus.WAITING_FOR_APPROVAL);
    }

    //For auto-updating the status of the reservation once they are expired
    @Transactional
    @Scheduled(fixedDelayString = "PT1M")
    protected void updatedExpiredReservation() {
        reservationRepository.updatedExpiredReservation(
                ReservationStatus.EXPIRED,
                LocalDateTime.now(),
                ReservationStatus.WAITING_FOR_APPROVAL);

        carRepository.updateCarStatusForExpiredReservation(CarStatus.AVAILABLE,
                CarStatus.RESERVED,
                LocalDateTime.now(),
                ReservationStatus.EXPIRED,
                List.of(ReservationStatus.BOOKED, ReservationStatus.WAITING_FOR_APPROVAL));
    }

}
