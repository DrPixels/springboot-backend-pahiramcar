package com.lindtsey.pahiramcar.transactions;

import com.lindtsey.pahiramcar.bookings.Booking;
import com.lindtsey.pahiramcar.car.Car;
import com.lindtsey.pahiramcar.customer.Customer;
import com.lindtsey.pahiramcar.employee.Employee;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public Transaction save(TransactionDTO dto) {
        Transaction transaction = toTransaction(dto);
        return transactionRepository.save(transaction);
    }

    public List<Transaction> findAllTransactions() {
        return transactionRepository.findAll();
    }

    public Optional<Transaction> findTransactionById (Integer id) {
        return transactionRepository.findById(id);
    }

    public void delete(Integer transactionId) {
        transactionRepository.deleteById(transactionId);
    }

    private Transaction toTransaction(TransactionDTO dto) {
        var transaction = new Transaction();
        transaction.setAmountDue(dto.amountDue());
        transaction.setAmountPaid(dto.amountPaid());
        transaction.setPaymentMode(dto.paymentMode());

        var employee = new Employee();
        employee.setEmployeeId(dto.employeeId());

        var booking = new Booking();
        booking.setBookingId(dto.bookingId());

        var customer = new Customer();
        customer.setCustomerId(dto.customerId());

        var car = new Car();
        car.setCarId(dto.carId());

        transaction.setEmployee(employee);
        transaction.setBooking(booking);
        transaction.setCustomer(customer);
        transaction.setCar(car);

        return transaction;
    }
}
