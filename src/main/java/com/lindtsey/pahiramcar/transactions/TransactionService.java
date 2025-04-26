package com.lindtsey.pahiramcar.transactions;

import com.lindtsey.pahiramcar.bookings.Booking;
import com.lindtsey.pahiramcar.bookings.BookingRepository;
import com.lindtsey.pahiramcar.car.Car;
import com.lindtsey.pahiramcar.customer.Customer;
import com.lindtsey.pahiramcar.employee.Employee;
import com.lindtsey.pahiramcar.employee.EmployeeRepository;
import com.lindtsey.pahiramcar.reservations.Reservation;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final BookingRepository bookingRepository;
    private final EmployeeRepository employeeRepository;

    public TransactionService(TransactionRepository transactionRepository, BookingRepository bookingRepository, EmployeeRepository employeeRepository) {
        this.transactionRepository = transactionRepository;
        this.bookingRepository = bookingRepository;
        this.employeeRepository = employeeRepository;
    }

    public Transaction saveTransactionFromBooking(Booking booking, TransactionDTO dto) {
        Transaction transaction = toTransaction(dto);
        transaction.setBooking(booking);

        return transactionRepository.save(transaction);
    }

    public void updateTransactionDueToPenalty(Integer transactionId, double penalty) {
        Transaction transaction = transactionRepository.findById(transactionId).orElseThrow(() -> new RuntimeException("Transaction not found"));

        transaction.setPenaltyPaid(penalty);
        transactionRepository.save(transaction);
    }

    public List<Transaction> findCustomerTransactionsByUserId(Integer id) {
        return transactionRepository.findTransactionsByBooking_Reservation_Customer_UserId(id);
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
        transaction.setCarRentalPaid(dto.carRentalPaid());
        transaction.setPaymentMode(dto.paymentMode());

        Employee employee = employeeRepository.findById(dto.employeeId()).orElseThrow(() -> new RuntimeException("Employee not found"));

        transaction.setEmployee(employee);

        return transaction;
    }
}
