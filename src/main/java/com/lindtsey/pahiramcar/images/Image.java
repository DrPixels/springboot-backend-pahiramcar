package com.lindtsey.pahiramcar.images;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.lindtsey.pahiramcar.bookings.Booking;
import com.lindtsey.pahiramcar.car.Car;
import com.lindtsey.pahiramcar.customer.Customer;
import com.lindtsey.pahiramcar.employee.Employee;
import com.lindtsey.pahiramcar.transactions.childClass.DamageRepairFeeTransaction;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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

    @NotBlank(message = "Image name is required.")
    @Column(nullable = false)
    private String imageName;

    @NotBlank(message = "Image URL is required.")
    @Column(nullable = false)
    private String imageUrl;

    @NotBlank(message = "Public ID of image is required.")
    @Column(nullable = false)
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

    @ManyToOne
    @JoinColumn(
            name = "transaction_id"
    )
    @JsonBackReference
    private DamageRepairFeeTransaction damageRepairFeeTransaction;
}
