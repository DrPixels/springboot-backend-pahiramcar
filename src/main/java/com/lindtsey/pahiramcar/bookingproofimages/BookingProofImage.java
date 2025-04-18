package com.lindtsey.pahiramcar.bookingproofimages;


import com.lindtsey.pahiramcar.bookings.Booking;
import jakarta.persistence.*;

@Entity
@Table(name = "BookingProofImages")
public class BookingProofImage {
    @Id
    private Integer bookingProofImageId;

    @ManyToOne
    @JoinColumn(
            name = "booking_id"
    )
    private Booking booking;

    private String imageName;
    private String imageUrl;
    private String cloudinaryImageId;

    public BookingProofImage(String imageName, String imageUrl, String cloudinaryImageId) {
        this.imageName = imageName;
        this.imageUrl = imageUrl;
        this.cloudinaryImageId = cloudinaryImageId;
    }

    public Integer getBookingProofImageId() {
        return bookingProofImageId;
    }

    public void setBookingProofImageId(Integer bookingProofImageId) {
        this.bookingProofImageId = bookingProofImageId;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getCloudinaryImageId() {
        return cloudinaryImageId;
    }

    public void setCloudinaryImageId(String cloudinaryImageId) {
        this.cloudinaryImageId = cloudinaryImageId;
    }
}
