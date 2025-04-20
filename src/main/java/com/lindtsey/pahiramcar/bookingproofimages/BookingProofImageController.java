package com.lindtsey.pahiramcar.bookingproofimages;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookingProofImageController {

    private final BookingProofImageService bookingProofImageService;

    public BookingProofImageController(BookingProofImageService bookingProofImageService) {
        this.bookingProofImageService = bookingProofImageService;
    }

    @DeleteMapping("/api/car-images/{car-image-id}")
    public ResponseEntity<?> deleteCarImage(@PathVariable("car-image-id") Integer carImageId) {
        this.bookingProofImageService.deleteBookingProofImageById(carImageId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
