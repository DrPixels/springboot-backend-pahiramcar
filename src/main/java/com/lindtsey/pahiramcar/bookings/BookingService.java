package com.lindtsey.pahiramcar.bookings;

import com.lindtsey.pahiramcar.car.Car;
import com.lindtsey.pahiramcar.car.CarRepository;
import com.lindtsey.pahiramcar.customer.Customer;
import com.lindtsey.pahiramcar.customer.CustomerRepository;
import com.lindtsey.pahiramcar.enums.ImageOwnerType;
import com.lindtsey.pahiramcar.enums.ReservationStatus;
import com.lindtsey.pahiramcar.reservations.Reservation;
import com.lindtsey.pahiramcar.reservations.ReservationRepository;
import com.lindtsey.pahiramcar.images.Image;
import com.lindtsey.pahiramcar.images.ImageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final ImageService imageService;
    private final CustomerRepository customerRepository;
    private final CarRepository carRepository;
    private final ReservationRepository reservationRepository;

    public BookingService(BookingRepository bookingRepository,
                          ImageService imageService,
                          CustomerRepository customerRepository,
                          CarRepository carRepository,
                          ReservationRepository reservationRepository) {
        this.bookingRepository = bookingRepository;
        this.imageService = imageService;
        this.customerRepository = customerRepository;
        this.carRepository = carRepository;
        this.reservationRepository = reservationRepository;
    }

    @Transactional
    public Booking saveWithBookingProofImages(BookingDTO dto, MultipartFile[] multipartFiles) throws IOException {

        //Before we save the booking, we change the status of the reservation
        Reservation reservation = reservationRepository.findById(dto.reservationId()).orElseThrow(() -> new RuntimeException("Reservation not found"));
        reservation.setStatus(ReservationStatus.BOOKED);
        reservationRepository.save(reservation);

        // Begin saving the reservation
        Booking booking = toBooking(dto);
        Booking savedBooking = bookingRepository.save(booking);

        Integer bookingId = savedBooking.getBookingId();

        List<Image> bookingProofImages = imageService.saveImages(multipartFiles, ImageOwnerType.BOOKING, bookingId);

        booking.setBookingProofImages(bookingProofImages);

        return savedBooking;
    }

    public Booking saveBookingImages(Integer bookingId, MultipartFile[] multipartFiles) throws IOException {
       Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new RuntimeException("Booking not found"));

       List<Image> savedImages = imageService.saveImages(multipartFiles, ImageOwnerType.BOOKING, bookingId);

       booking.getBookingProofImages().addAll(savedImages);

       return booking;
    }

    public List<Booking> findBookingByCustomerId(Integer customerId) {
        return bookingRepository.findBookingsByCustomer_CustomerId(customerId);
    }

    public List<Booking> findBookingByCarId(Integer customerId) {
        return bookingRepository.findBookingsByCar_CarId(customerId);
    }

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
        booking.setDriverLicenseNumber(dto.driverLicenseNumber());
        booking.setStartDate(dto.startDate());
        booking.setEndDate(dto.endDate());
        booking.setTotalAmount(dto.totalAmount());

        Customer customer = customerRepository.findById(dto.customerId()).orElseThrow(() -> new RuntimeException("Customer not found"));
        Car car = carRepository.findById(dto.carId()).orElseThrow(() -> new RuntimeException("Car not found"));
        Reservation reservation = reservationRepository.findById(dto.reservationId()).orElseThrow(() -> new RuntimeException("Reservation not found"));

        booking.setCustomer(customer);
        booking.setCar(car);
        booking.setReservation(reservation);

        return booking;
    }
}
