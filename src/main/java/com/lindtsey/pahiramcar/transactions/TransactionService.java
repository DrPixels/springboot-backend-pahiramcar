package com.lindtsey.pahiramcar.transactions;

import com.lindtsey.pahiramcar.bookings.Booking;
import com.lindtsey.pahiramcar.bookings.BookingRepository;
import com.lindtsey.pahiramcar.employee.Employee;
import com.lindtsey.pahiramcar.employee.EmployeeRepository;
import com.lindtsey.pahiramcar.enums.ImageOwnerType;
import com.lindtsey.pahiramcar.enums.TransactionType;
import com.lindtsey.pahiramcar.images.Image;
import com.lindtsey.pahiramcar.images.ImageService;
import com.lindtsey.pahiramcar.transactions.childClass.BookingPaymentTransaction;
import com.lindtsey.pahiramcar.transactions.childClass.DamageRepairFeeTransaction;
import com.lindtsey.pahiramcar.transactions.childClass.LateReturnFeeTransaction;
import com.lindtsey.pahiramcar.transactions.miscellaneous.CarDamageRequest;
import com.lindtsey.pahiramcar.transactions.miscellaneous.PenaltyRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final BookingRepository bookingRepository;
    private final EmployeeRepository employeeRepository;
    private final ImageService imageService;

    public TransactionService(TransactionRepository transactionRepository, BookingRepository bookingRepository, EmployeeRepository employeeRepository, ImageService imageService) {
        this.transactionRepository = transactionRepository;
        this.bookingRepository = bookingRepository;
        this.employeeRepository = employeeRepository;
        this.imageService = imageService;
    }

    public BookingPaymentTransaction saveTransactionFromBooking(Integer bookingId, TransactionDTO dto) {
        BookingPaymentTransaction transaction = toBookingPaymentTransaction(dto);

        return transactionRepository.save(transaction);
    }

    @Transactional
    public LateReturnFeeTransaction saveTransactionDueToPenalty(Integer bookingId, TransactionDTO dto) {

        LateReturnFeeTransaction transaction = toLateReturnFeeTransaction(dto);

        return transactionRepository.save(transaction);
    }

    @Transactional
    public DamageRepairFeeTransaction saveTransactionDueToCarDamage(Integer bookingId,
                                                     TransactionDTO dto,
                                                MultipartFile[] multipartFiles) throws IOException {

        DamageRepairFeeTransaction transaction = toDamageRepairFeeTransaction(dto);

        Transaction savedTransaction = transactionRepository.save(transaction);
        Integer transactionId = transaction.getTransactionId();

        List<Image> carDamageImages = imageService.saveImages(multipartFiles, ImageOwnerType.TRANSACTION, transactionId);
        transaction.setCarDamageImages(carDamageImages);

        return transactionRepository.save(transaction);
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

    private DamageRepairFeeTransaction toDamageRepairFeeTransaction(TransactionDTO dto) {
        var transaction = new DamageRepairFeeTransaction();
        transaction.setAmountPaid(dto.amountPaid());
        transaction.setPaymentMode(dto.paymentMode());
        transaction.setTransactionType(TransactionType.DAMAGE_REPAIR_FEE);
        transaction.setCarDamageDescription(dto.carDamageDescription());

        Booking booking = bookingRepository.findById(dto.bookingId()).orElseThrow(() -> new RuntimeException("Booking not found"));
        transaction.setBooking(booking);

        Employee employee = employeeRepository.findById(dto.employeeId()).orElseThrow(() -> new RuntimeException("Employee not found"));

        transaction.setEmployee(employee);

        return transaction;
    }

    private LateReturnFeeTransaction toLateReturnFeeTransaction(TransactionDTO dto) {
        var transaction = new LateReturnFeeTransaction();
        transaction.setAmountPaid(dto.amountPaid());
        transaction.setPaymentMode(dto.paymentMode());
        transaction.setTransactionType(TransactionType.LATE_RETURN_FEE);

        Booking booking = bookingRepository.findById(dto.bookingId()).orElseThrow(() -> new RuntimeException("Booking not found"));
        transaction.setBooking(booking);

        Employee employee = employeeRepository.findById(dto.employeeId()).orElseThrow(() -> new RuntimeException("Employee not found"));

        transaction.setEmployee(employee);

        return transaction;
    }

    private BookingPaymentTransaction toBookingPaymentTransaction(TransactionDTO dto) {
        var transaction = new BookingPaymentTransaction();
        transaction.setAmountPaid(dto.amountPaid());
        transaction.setPaymentMode(dto.paymentMode());
        transaction.setTransactionType(TransactionType.BOOKING_PAYMENT);

        Booking booking = bookingRepository.findById(dto.bookingId()).orElseThrow(() -> new RuntimeException("Booking not found"));
        transaction.setBooking(booking);

        Employee employee = employeeRepository.findById(dto.employeeId()).orElseThrow(() -> new RuntimeException("Employee not found"));

        transaction.setEmployee(employee);

        return transaction;
    }
}
