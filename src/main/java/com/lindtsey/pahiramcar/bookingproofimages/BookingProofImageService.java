package com.lindtsey.pahiramcar.bookingproofimages;

import com.lindtsey.pahiramcar.bookings.Booking;
import com.lindtsey.pahiramcar.bookings.BookingRepository;
import com.lindtsey.pahiramcar.cloudinary.CloudinaryService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class BookingProofImageService {

    private final BookingProofImageRepository bookingProofImageRepository;
    private final CloudinaryService cloudinaryService;
    private final BookingRepository bookingRepository;

    public BookingProofImageService(BookingProofImageRepository bookingProofImageRepository, CloudinaryService cloudinaryService, BookingRepository bookingRepository) {
        this.bookingProofImageRepository = bookingProofImageRepository;
        this.cloudinaryService = cloudinaryService;
        this.bookingRepository = bookingRepository;
    }

    public List<BookingProofImage> save(Integer bookingId, MultipartFile[] multipartFiles) throws IOException {

        List<BookingProofImage> bookingProofImages = new ArrayList<>();
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
            Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new RuntimeException("Booking not found"));
            bookingProofImage.setBooking(booking);

            bookingProofImageRepository.save(bookingProofImage);
            bookingProofImages.add(bookingProofImage);

        }
        return bookingProofImages;
    }
    
    public void deleteBookingProofImageById(Integer bookingProofImageId) {
        bookingProofImageRepository.deleteById(bookingProofImageId);
    }


}
