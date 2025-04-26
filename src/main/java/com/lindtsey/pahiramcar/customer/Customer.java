package com.lindtsey.pahiramcar.customer;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.lindtsey.pahiramcar.bookings.Booking;
import com.lindtsey.pahiramcar.enums.MaritalStatus;
import com.lindtsey.pahiramcar.reservations.Reservation;
import com.lindtsey.pahiramcar.images.Image;
import com.lindtsey.pahiramcar.transactions.Transaction;
import com.lindtsey.pahiramcar.user.User;
import com.lindtsey.pahiramcar.utils.customAnnotations.birthdate.BirthDateConstraint;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@Entity
@Builder
@AllArgsConstructor
@Table(name = "Customers")
public class Customer extends User {

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

    @OneToMany
    @JoinColumn(name = "user_id")
    private List<Booking> bookings = new ArrayList<>();

    @OneToMany
    @JoinColumn(name = "user_id")
    private List<Transaction> transactions = new ArrayList<>();

}
