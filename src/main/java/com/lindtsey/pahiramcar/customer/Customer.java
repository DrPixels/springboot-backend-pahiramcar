package com.lindtsey.pahiramcar.customer;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.lindtsey.pahiramcar.bookings.Booking;
import com.lindtsey.pahiramcar.reservations.Reservation;
import com.lindtsey.pahiramcar.images.Image;
import com.lindtsey.pahiramcar.transactions.Transaction;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Builder
@AllArgsConstructor
@Table(name = "Customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer customerId;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @NotBlank(message = "First name is required.")
    @Column(nullable = false)
    private String firstName;

    @Column
    private String middleName;

    @NotBlank(message = "Last name is required.")
    @Column(nullable = false)
    private String lastName;

    @NotNull(message = "Birthdate is required.")
    @Column(nullable = false)
    private LocalDate birthDate;

    @Pattern(regexp = "^09\\d{9}$", message = "Phone number must start with 09 and must be 11 digits long.")
    @Column(unique = true)
    private String mobilePhone;

    @Pattern(regexp= "^[a-z][a-z0-9]*@[a-z]+\\.com$", message = "Invalid Email Format")
    @Column(unique = true)
    private String email;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    @OneToOne(
            mappedBy = "customer",
            cascade = CascadeType.ALL
    )
    private Image customerImage;

    @OneToMany(
            mappedBy = "customer"
    )
    @JsonManagedReference
    private List<Reservation> reservations = new ArrayList<>();

    @OneToMany(
            mappedBy = "customer"
    )
    @JsonManagedReference
    private List<Booking> bookings = new ArrayList<>();

    @OneToMany(
            mappedBy = "customer"
    )
    @JsonManagedReference
    private List<Transaction> transactions = new ArrayList<>();

}
