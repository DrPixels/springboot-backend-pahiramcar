package com.lindtsey.pahiramcar.images;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.lindtsey.pahiramcar.bookings.Booking;
import com.lindtsey.pahiramcar.car.Car;
import com.lindtsey.pahiramcar.customer.Customer;
import com.lindtsey.pahiramcar.employee.Employee;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Images")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer imageId;

    private String imageName;
    private String imageUrl;
    private String publicId;

    public Image(String imageName, String imageUrl, String publicId) {
        this.imageName = imageName;
        this.imageUrl = imageUrl;
        this.publicId = publicId;
    }

    @ManyToOne
    @JoinColumn(
            name = "car_id"
    )
    @JsonBackReference
    private Car car;

    @ManyToOne
    @JoinColumn(
            name = "booking_id"
    )
    @JsonBackReference
    private Booking booking;

    @OneToOne
    @JoinColumn(
            name = "customer_id"
    )
    @JsonBackReference
    private Customer customer;

    @OneToOne
    @JoinColumn(
            name = "employee_id"
    )
    @JsonBackReference
    private Employee employee;
}
