package com.lindtsey.pahiramcar.bookings;

import com.lindtsey.pahiramcar.bookingproofimages.BookingProofImage;
import com.lindtsey.pahiramcar.bookingproofimages.BookingProofImageService;
import com.lindtsey.pahiramcar.car.Car;
import com.lindtsey.pahiramcar.car.CarRepository;
import com.lindtsey.pahiramcar.carimages.CarImage;
import com.lindtsey.pahiramcar.cloudinary.CloudinaryService;
import com.lindtsey.pahiramcar.customer.Customer;
import com.lindtsey.pahiramcar.customer.CustomerRepository;
import com.lindtsey.pahiramcar.customer.CustomerService;
import com.lindtsey.pahiramcar.reservations.Reservation;
import com.lindtsey.pahiramcar.reservations.ReservationRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final BookingProofImageService bookingProofImageService;
    private final CloudinaryService cloudinaryService;
    private final CustomerRepository customerRepository;
    private final CarRepository carRepository;
    private final ReservationRepository reservationRepository;

    public BookingService(BookingRepository bookingRepository,
                          BookingProofImageService bookingProofImageService,
                          CloudinaryService cloudinaryService,
                          CustomerRepository customerRepository,
                          CarRepository carRepository,
                          ReservationRepository reservationRepository) {
        this.bookingRepository = bookingRepository;
        this.bookingProofImageService = bookingProofImageService;
        this.cloudinaryService = cloudinaryService;
        this.customerRepository = customerRepository;
        this.carRepository = carRepository;
        this.reservationRepository = reservationRepository;
    }

    public Booking saveWithBookingProofImages(BookingDTO dto, MultipartFile[] multipartFiles) throws IOException {
        Booking booking = toBooking(dto);
        Booking savedBooking = bookingRepository.save(booking);

        Integer bookingId = savedBooking.getBookingId();

        for (MultipartFile file: multipartFiles) {
            //Uploading the image in the Cloudinary
            BufferedImage bufferedImage = ImageIO.read(file.getInputStream());
            if( bufferedImage == null ) {
                throw new IOException("Failed to read the image.");
            }

            Map result = cloudinaryService.upload(file);
            String bookingProofImageName = (String) result.get("original_filename");
            String bookingProofImageURL = (String) result.get("url");
            String bookingProofImageCloudinaryId = (String) result.get("public_id");

            BookingProofImage bookingProofImage = new BookingProofImage(bookingProofImageName, bookingProofImageURL, bookingProofImageCloudinaryId);
            bookingProofImage.setBooking(booking);

            BookingProofImage savedBookingProofImage = bookingProofImageService.save(bookingProofImage);

            savedBooking.getBookingProofImages().add(savedBookingProofImage);
        }

        return savedBooking;
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
