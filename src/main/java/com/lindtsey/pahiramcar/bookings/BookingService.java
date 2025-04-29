package com.lindtsey.pahiramcar.bookings;

import com.lindtsey.pahiramcar.car.Car;
import com.lindtsey.pahiramcar.car.CarRepository;
import com.lindtsey.pahiramcar.customer.CustomerRepository;
import com.lindtsey.pahiramcar.enums.*;
import com.lindtsey.pahiramcar.reports.Time;
import com.lindtsey.pahiramcar.reservations.Reservation;
import com.lindtsey.pahiramcar.reservations.ReservationRepository;
import com.lindtsey.pahiramcar.images.Image;
import com.lindtsey.pahiramcar.images.ImageService;
import com.lindtsey.pahiramcar.transactions.Transaction;
import com.lindtsey.pahiramcar.transactions.TransactionRepository;
import com.lindtsey.pahiramcar.transactions.TransactionService;
import com.lindtsey.pahiramcar.transactions.childClass.bookingPayment.BookingPaymentTransaction;
import com.lindtsey.pahiramcar.transactions.childClass.bookingPayment.BookingPaymentTransactionDTO;
import com.lindtsey.pahiramcar.utils.constants;
import com.lindtsey.pahiramcar.utils.exceptions.DriversLicenseCurrentlyUsedInBookingException;
import com.lindtsey.pahiramcar.utils.exceptions.ReservationCancelledOrExpiredException;
import com.lindtsey.pahiramcar.utils.sorter.BookingSorter;
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
    private final TransactionRepository transactionRepository;

    public BookingService(BookingRepository bookingRepository,
                          ImageService imageService,
                          CustomerRepository customerRepository,
                          CarRepository carRepository,
                          ReservationRepository reservationRepository,
                          TransactionService transactionService, TransactionRepository transactionRepository) {
        this.bookingRepository = bookingRepository;
        this.imageService = imageService;
        this.customerRepository = customerRepository;
        this.carRepository = carRepository;
        this.reservationRepository = reservationRepository;
        this.transactionService = transactionService;
        this.transactionRepository = transactionRepository;
    }

    @Transactional
    public Booking saveWithBookingProofImages(BookingDTO bookingDTO, BookingPaymentTransactionDTO dto, MultipartFile[] multipartFiles) throws IOException {

        // Check if the reservation is valid
        Reservation reservation = reservationRepository.findById(bookingDTO.reservationId()).orElseThrow(() -> new RuntimeException("Reservation not found"));
        if(reservation.getStatus() == ReservationStatus.EXPIRED || reservation.getStatus() == ReservationStatus.CANCELLED) {
            throw new ReservationCancelledOrExpiredException();
        }

        this.isDriverLicenseCurrentlyUsedInBooking(bookingDTO.driverLicenseNumber());

        // Get the booking details

        // Before we change the reservation, we change the status of the car
        Integer carId = reservation.getCar().getCarId();
        Car car = carRepository.findById(carId).orElseThrow(() -> new RuntimeException("Car not found"));
        car.setStatus(CarStatus.BOOKED);
        carRepository.save(car);

        //Before we save the booking, we change the status of the reservation
        reservation.setStatus(ReservationStatus.BOOKED);
        reservationRepository.save(reservation);

        // Begin saving the booking
        Booking booking = toBooking(bookingDTO);
        booking.setCarInitialMileage(car.getMileage());
        Booking savedBooking = bookingRepository.save(booking);

        Integer bookingId = savedBooking.getBookingId();

        List<Image> bookingProofImages = imageService.saveImages(multipartFiles, ImageOwnerType.BOOKING, bookingId);

        booking.setBookingProofImages(bookingProofImages);

        // Create the associated transaction within the booking
        // Transaction Type: Booking Payment
        Transaction savedTransaction = transactionService.saveTransactionFromBooking(savedBooking, dto);
        savedBooking.getTransactions().add(savedTransaction);

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
            throw new DriversLicenseCurrentlyUsedInBookingException();
        }

    }

    // Find the bookings of the customer based on its ID
    public List<Booking> findBookingByCustomerId(Integer customerId) {

        // Unsorted version from the database
        List<Booking> bookings = bookingRepository.findBookingsByReservation_Customer_UserId(customerId);

        // Sorts the bookings based on their start date and status
        // Refer to the BookingStatus enum for ordering
        BookingSorter.mergeSortBookings(bookings);

        return bookings;
    }

    public List<Booking> findCompletedBookingsByCustomerId(Integer customerId) {

        // Unsorted Version
        List<Booking> bookings = bookingRepository.findBookingsByReservation_Customer_UserIdAndStatus(customerId, BookingStatus.COMPLETED);

        BookingSorter.mergeSortBookings(bookings);

        return bookings;
    }

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
        booking.setNumberOfPassengers(dto.numberOfPassengers());
        booking.setRenterFullName(dto.renterFullName());
        booking.setDriverLicenseNumber(dto.driverLicenseNumber());
        booking.setStartDateTime(dto.startDateTime());
        booking.setEndDateTime(dto.endDateTime());

//        long minutesDuration = Duration.between(booking.getStartDateTime(), booking.getEndDateTime()).toMinutes();
//        double days = Math.ceil((double) minutesDuration / constants.PahiramCarConstants.MINUTES_PER_DAY);

        Reservation reservation = reservationRepository.findById(dto.reservationId()).orElseThrow(() -> new RuntimeException("Reservation not found"));
//        double totalAmount = reservation.getCar().getPricePerDay() * days + constants.PahiramCarConstants.REFUNDABLE_DEPOSIT;
        booking.setTotalAmount(dto.totalAmountPaid());

        booking.setStatus(BookingStatus.ONGOING);

//        Customer customer = customerRepository.findById(dto.customerId()).orElseThrow(() -> new RuntimeException("Customer not found"));
//        Car car = carRepository.findById(dto.carId()).orElseThrow(() -> new RuntimeException("Car not found"));

        booking.setReservation(reservation);

        return booking;
    }

    @Transactional
    public void returnCar(Integer bookingId, float afterMileage) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new RuntimeException("Booking not found"));

        booking.setCarAfterMileage(afterMileage);
        booking.setStatus(BookingStatus.COMPLETED);
        booking.setActualReturnDate(LocalDateTime.now());
        bookingRepository.save(booking);

        // We change the status of the car
        Car car = carRepository.findById(booking.getReservation().getCar().getCarId()).orElseThrow(() -> new RuntimeException("Car not found"));
        car.setStatus(CarStatus.AVAILABLE);
        car.setMileage(afterMileage);
        carRepository.save(car);

        // Deduct the refundable deposit
        BookingPaymentTransaction transaction = (BookingPaymentTransaction) transactionRepository.findTransactionByBooking_BookingIdAndTransactionType(bookingId, TransactionType.BOOKING_PAYMENT);
        transaction.setRefundableDepositClaimed(true);

        transactionRepository.save(transaction);
    }

    public double calculateAverageRentalTimeInDays() {
        List<Object[]> results = bookingRepository.findStartAndEndDateTimes();

        long totalDurationInSeconds = 0;

        for (Object[] result : results) {
            LocalDateTime startDateTime = (LocalDateTime) result[0];
            LocalDateTime endDateTime = (LocalDateTime) result[1];

            totalDurationInSeconds += Duration.between(startDateTime, endDateTime).getSeconds();
        }

        return results.isEmpty() ? 0 : totalDurationInSeconds / (double) results.size() / constants.PahiramCarConstants.SECONDS_PER_DAY;
    }

    public double calculateAverageRentalTimeInDaysBeforeThisMonth() {
        List<Object[]> results = bookingRepository.findStartAndEndDateTimesBefore(Time.startOfThisMonth());

        long totalDurationInSeconds = 0;

        for (Object[] result : results) {
            LocalDateTime startDateTime = (LocalDateTime) result[0];
            LocalDateTime endDateTime = (LocalDateTime) result[1];

            totalDurationInSeconds += Duration.between(startDateTime, endDateTime).getSeconds();
        }

        return results.isEmpty() ? 0 : totalDurationInSeconds / (double) results.size() / constants.PahiramCarConstants.SECONDS_PER_DAY;
    }

    public double calculateAverageRentalTimeInDaysBetween(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        List<Object[]> results = bookingRepository.findStartAndEndDateTimesBetween(startDateTime, endDateTime);

        long totalDurationInSeconds = 0;

        for (Object[] result : results) {
            LocalDateTime resultStartDateTime = (LocalDateTime) result[0];
            LocalDateTime resultEndDateTime = (LocalDateTime) result[1];

            totalDurationInSeconds += Duration.between(resultStartDateTime, resultEndDateTime).getSeconds();
        }

        return results.isEmpty() ? 0 : totalDurationInSeconds / (double) results.size() / constants.PahiramCarConstants.SECONDS_PER_DAY;
    }

    //For auto-updating the status of the booking once they are due already
    @Transactional
    @Scheduled(fixedDelayString = "PT1M")
    protected void updatedDueBooking() {
        LocalDateTime now = LocalDateTime.now();
        bookingRepository.updateDueBooking(BookingStatus.DUE_ALREADY, now, BookingStatus.ONGOING);
    }
}
