package com.lindtsey.pahiramcar.customer;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.lindtsey.pahiramcar.bookings.Booking;
import com.lindtsey.pahiramcar.reservations.Reservation;
import com.lindtsey.pahiramcar.transactions.Transaction;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "Customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer customerId;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column
    private String customerImageURL;

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

    @OneToMany(
            mappedBy = "customer"
    )
    @JsonManagedReference
    private List<Reservation> reservations;

    @OneToMany(
            mappedBy = "customer"
    )
    @JsonManagedReference
    private List<Booking> bookings;

    @OneToMany(
            mappedBy = "customer"
    )
    @JsonManagedReference
    private List<Transaction> transactions;

    public Customer() {}

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getCustomerImageURL() {
        return customerImageURL;
    }

    public void setCustomerImageURL(String customerImageURL) {
        this.customerImageURL = customerImageURL;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }
}
