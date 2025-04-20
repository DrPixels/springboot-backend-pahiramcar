package com.lindtsey.pahiramcar.bookingproofimages;


import com.lindtsey.pahiramcar.bookings.Booking;
import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
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
}
