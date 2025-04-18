package com.lindtsey.pahiramcar.bookings;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.lindtsey.pahiramcar.bookingproofimages.BookingProofImage;
import com.lindtsey.pahiramcar.car.Car;
import com.lindtsey.pahiramcar.customer.Customer;
import com.lindtsey.pahiramcar.enums.BookingStatus;
import com.lindtsey.pahiramcar.reservations.Reservation;
import com.lindtsey.pahiramcar.transactions.Transaction;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "Bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer bookingId;

    @OneToOne
    @JoinColumn(
            name = "transaction_id"
    )
    private Transaction transaction;

    @ManyToOne
    @JoinColumn(
            name = "customer_id"
    )
    @JsonBackReference
    private Customer customer;

    @OneToOne
    @JoinColumn(
            name = "car_id"
    )
    private Car car;

    @OneToOne
    @JoinColumn(
            name = "reservation_id"
    )
    private Reservation reservation;

    @OneToMany(
            mappedBy = "booking"
    )
    private List<BookingProofImage> bookingProofImages;

    private String driverLicenseNumber;
    private LocalDate startDate;
    private LocalDate endDate;
    private float totalAmount;
    private float penalty;
    private BookingStatus status;

    public Booking() {}

    public Integer getBookingId() {
        return bookingId;
    }

    public void setBookingId(Integer bookingId) {
        this.bookingId = bookingId;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public List<BookingProofImage> getBookingProofImages() {
        return bookingProofImages;
    }

    public void setBookingProofImages(List<BookingProofImage> bookingProofImages) {
        this.bookingProofImages = bookingProofImages;
    }

    public String getDriverLicenseNumber() {
        return driverLicenseNumber;
    }

    public void setDriverLicenseNumber(String driverLicenseNumber) {
        this.driverLicenseNumber = driverLicenseNumber;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public float getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(float totalAmount) {
        this.totalAmount = totalAmount;
    }

    public float getPenalty() {
        return penalty;
    }

    public void setPenalty(float penalty) {
        this.penalty = penalty;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }
}
