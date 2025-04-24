package com.lindtsey.pahiramcar.bookings;

import com.lindtsey.pahiramcar.car.Car;
import com.lindtsey.pahiramcar.car.CarRepository;
import com.lindtsey.pahiramcar.customer.CustomerRepository;
import com.lindtsey.pahiramcar.enums.BookingStatus;
import com.lindtsey.pahiramcar.enums.CarStatus;
import com.lindtsey.pahiramcar.enums.ImageOwnerType;
import com.lindtsey.pahiramcar.enums.ReservationStatus;
import com.lindtsey.pahiramcar.reservations.Reservation;
import com.lindtsey.pahiramcar.reservations.ReservationRepository;
import com.lindtsey.pahiramcar.images.Image;
import com.lindtsey.pahiramcar.images.ImageService;
import com.lindtsey.pahiramcar.transactions.Transaction;
import com.lindtsey.pahiramcar.transactions.TransactionDTO;
import com.lindtsey.pahiramcar.transactions.TransactionService;
import com.lindtsey.pahiramcar.utils.constants;
import com.lindtsey.pahiramcar.utils.exceptions.DriversLicenseCurrentlyUsedInBookingException;
import com.lindtsey.pahiramcar.utils.exceptions.ReservationCancelledOrExpiredException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final ImageService imageService;
    private final CustomerRepository customerRepository;
    private final CarRepository carRepository;
    private final ReservationRepository reservationRepository;
    private final TransactionService transactionService;

    public BookingService(BookingRepository bookingRepository,
                          ImageService imageService,
                          CustomerRepository customerRepository,
                          CarRepository carRepository,
                          ReservationRepository reservationRepository,
                          TransactionService transactionService) {
        this.bookingRepository = bookingRepository;
        this.imageService = imageService;
        this.customerRepository = customerRepository;
        this.carRepository = carRepository;
        this.reservationRepository = reservationRepository;
        this.transactionService = transactionService;
    }

    @Transactional
    public Booking saveWithBookingProofImages(BookingDTO bookingDTO, TransactionDTO transactionDTO, MultipartFile[] multipartFiles) throws IOException {

        // Check if the reservation is valid
        Reservation reservation = reservationRepository.findById(bookingDTO.reservationId()).orElseThrow(() -> new RuntimeException("Reservation not found"));
        if(reservation.getStatus() == ReservationStatus.EXPIRED || reservation.getStatus() == ReservationStatus.CANCELLED) {
            throw new ReservationCancelledOrExpiredException("Reservation is not valid. Either it is already cancelled or expired.");
        }

        this.isDriverLicenseCurrentlyUsedInBooking(bookingDTO.driverLicenseNumber());


        // Before we save the reservation, we change the status of the car
        Car car = carRepository.findById(bookingDTO.carId()).orElseThrow(() -> new RuntimeException("Car not found"));
        car.setStatus(CarStatus.BOOKED);
        carRepository.save(car);

        //Before we save the booking, we change the status of the reservation
        reservation.setStatus(ReservationStatus.BOOKED);
        reservationRepository.save(reservation);

        // Begin saving the booking
        Booking booking = toBooking(bookingDTO);
        Booking savedBooking = bookingRepository.save(booking);

        Integer bookingId = savedBooking.getBookingId();

        List<Image> bookingProofImages = imageService.saveImages(multipartFiles, ImageOwnerType.BOOKING, bookingId);

        booking.setBookingProofImages(bookingProofImages);

        // Create the associated transaction within the booking
        Transaction savedTransaction = transactionService.saveTransactionFromBooking(booking, transactionDTO);
        booking.setTransaction(savedTransaction);

        return savedBooking;
    }

    public Booking saveBookingImages(Integer bookingId, MultipartFile[] multipartFiles) throws IOException {
       Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new RuntimeException("Booking not found"));

       List<Image> savedImages = imageService.saveImages(multipartFiles, ImageOwnerType.BOOKING, bookingId);

       booking.getBookingProofImages().addAll(savedImages);

       return booking;
    }

    public void isDriverLicenseCurrentlyUsedInBooking(String driverLicenseNumber) {
        if(bookingRepository.isDriverLicenseCurrentlyUsedInBooking(driverLicenseNumber, BookingStatus.ONGOING)) {
            throw new DriversLicenseCurrentlyUsedInBookingException("Driver's License Number is being used already from someone's booking.");
        }

    }

//    public List<Booking> findBookingByCustomerId(Integer customerId) {
//        return bookingRepository.findBookingsByCustomer_CustomerId(customerId);
//    }
//
//    public List<Booking> findBookingByCarId(Integer customerId) {
//        return bookingRepository.findBookingsByCar_CarId(customerId);
//    }

    public List<Booking> findAllBooking() {
        return bookingRepository.findAll();
    }

    public void deleteImage(Integer imageId) throws IOException {
        imageService.deleteImage(imageId);
    }

    public void deleteBookingById(Integer bookingId) {
        bookingRepository.deleteById(bookingId);
    }

    public Booking toBooking(BookingDTO dto) {
        var booking = new Booking();
        booking.setRenterFullName(dto.renterFullName());
        booking.setDriverLicenseNumber(dto.driverLicenseNumber());
        booking.setStartDateTime(dto.startDateTime());
        booking.setEndDateTime(dto.endDateTime());

        long minutesDuration = Duration.between(booking.getStartDateTime(), booking.getEndDateTime()).toMinutes();
        double days = Math.ceil((double) minutesDuration / constants.PahiramCarConstants.MINUTES_PER_DAY);

        Reservation reservation = reservationRepository.findById(dto.reservationId()).orElseThrow(() -> new RuntimeException("Reservation not found"));
        double totalAmount = reservation.getCar().getPricePerDay() * days;
        booking.setTotalAmount(totalAmount);

        booking.setStatus(BookingStatus.ONGOING);

//        Customer customer = customerRepository.findById(dto.customerId()).orElseThrow(() -> new RuntimeException("Customer not found"));
//        Car car = carRepository.findById(dto.carId()).orElseThrow(() -> new RuntimeException("Car not found"));

        booking.setReservation(reservation);

        return booking;
    }

    @Transactional
    public void returnCar(Integer bookingId) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new RuntimeException("Booking not found"));

        LocalDateTime actualReturnDate = LocalDateTime.now();

        if(actualReturnDate.isAfter(booking.getEndDateTime())) {

            // Set the overdue flag to true
            booking.setOverDue(true);

            // Compute the overDueDuration in minutes
            Long overDueDurationInMinutes = Duration.between(booking.getEndDateTime(), actualReturnDate).toMinutes();
            booking.setOverdueDurationInMinutes(overDueDurationInMinutes);

            // Compute the number of overdue days
            // In here, even an hour past day is considered already a day
            Long overDueDays = (long) Math.ceil((double) overDueDurationInMinutes / constants.PahiramCarConstants.MINUTES_PER_DAY);

            // Compute the penalty
            double penalty = overDueDays * booking.getReservation().getCar().getPricePerDay() * constants.PahiramCarConstants.PENALTY_RATE;

            booking.setPenalty(penalty);
        }


        booking.setStatus(BookingStatus.COMPLETED);
        bookingRepository.save(booking);

        // We change the status of the car
        Car car = carRepository.findById(booking.getReservation().getCar().getCarId()).orElseThrow(() -> new RuntimeException("Car not found"));
        car.setStatus(CarStatus.AVAILABLE);
        carRepository.save(car);
    }

    //For auto-updating the status of the booking once they are due already
    @Transactional
    @Scheduled(fixedDelayString = "PT1M")
    protected void updatedDueBooking() {
        LocalDateTime now = LocalDateTime.now();
        bookingRepository.updateDueBooking(BookingStatus.DUE_ALREADY, now, BookingStatus.ONGOING);
    }
}
