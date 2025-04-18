package com.lindtsey.pahiramcar.bookingproofimages;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

@Service
public class BookingProofImageService {

    BookingProofImageRepository bookingProofImageRepository;

    public BookingProofImageService(BookingProofImageRepository bookingProofImageRepository) {
        this.bookingProofImageRepository = bookingProofImageRepository;
    }

    public BookingProofImage save(BookingProofImage bookingProofImage) {
        return bookingProofImageRepository.save(bookingProofImage);
    }
    
    public void deleteBookingProofImageById(Integer bookingProofImageId) {
        bookingProofImageRepository.deleteById(bookingProofImageId);
    }


}
