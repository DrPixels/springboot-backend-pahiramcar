package com.lindtsey.pahiramcar.transactions;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.lindtsey.pahiramcar.bookings.Booking;
import com.lindtsey.pahiramcar.car.Car;
import com.lindtsey.pahiramcar.customer.Customer;
import com.lindtsey.pahiramcar.employee.Employee;
import com.lindtsey.pahiramcar.enums.PaymentMode;
import jakarta.persistence.*;

@Entity
@Table(name = "Transactions")
public class Transaction {

    @Id
    private Integer transactionId;

    @ManyToOne
    @JoinColumn(
            name = "employee_id"
    )
    private Employee employee;

    @OneToOne
    @JoinColumn(
            name = "booking_id"
    )
    private Booking booking;

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

    private Float amountDue;
    private Float amountPaid;
    private PaymentMode paymentMode;

    public Transaction() {}

    public Integer getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Integer transactionId) {
        this.transactionId = transactionId;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
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

    public Float getAmountDue() {
        return amountDue;
    }

    public void setAmountDue(Float amountDue) {
        this.amountDue = amountDue;
    }

    public Float getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(Float amountPaid) {
        this.amountPaid = amountPaid;
    }

    public PaymentMode getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(PaymentMode paymentMode) {
        this.paymentMode = paymentMode;
    }
}
