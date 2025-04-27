package com.lindtsey.pahiramcar.transactions;

import com.lindtsey.pahiramcar.bookings.Booking;
import com.lindtsey.pahiramcar.bookings.BookingRepository;
import com.lindtsey.pahiramcar.employee.Employee;
import com.lindtsey.pahiramcar.employee.EmployeeRepository;
import com.lindtsey.pahiramcar.enums.ImageOwnerType;
import com.lindtsey.pahiramcar.enums.TransactionType;
import com.lindtsey.pahiramcar.images.Image;
import com.lindtsey.pahiramcar.images.ImageService;
import com.lindtsey.pahiramcar.transactions.childClass.bookingPayment.BookingPaymentTransaction;
import com.lindtsey.pahiramcar.transactions.childClass.bookingPayment.BookingPaymentTransactionDTO;
import com.lindtsey.pahiramcar.transactions.childClass.bookingPayment.BookingPaymentTransactionRepository;
import com.lindtsey.pahiramcar.transactions.childClass.damageRepairFee.DamageRepairFeeTransaction;
import com.lindtsey.pahiramcar.transactions.childClass.damageRepairFee.DamageRepairFeeTransactionDTO;
import com.lindtsey.pahiramcar.transactions.childClass.damageRepairFee.DamageRepairFeeTransactionRepository;
import com.lindtsey.pahiramcar.transactions.childClass.lateReturnFee.LateReturnFeeTransaction;
import com.lindtsey.pahiramcar.transactions.childClass.lateReturnFee.LateReturnFeeTransactionDTO;
import com.lindtsey.pahiramcar.transactions.childClass.lateReturnFee.LateReturnFeeTransactionRepository;
import com.lindtsey.pahiramcar.utils.constants;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final BookingRepository bookingRepository;
    private final EmployeeRepository employeeRepository;
    private final ImageService imageService;
    private final LateReturnFeeTransactionRepository lateReturnFeeTransactionRepository;
    private final BookingPaymentTransactionRepository bookingPaymentTransactionRepository;
    private final DamageRepairFeeTransactionRepository damageRepairFeeTransactionRepository;

    public TransactionService(TransactionRepository transactionRepository, BookingRepository bookingRepository, EmployeeRepository employeeRepository, ImageService imageService, LateReturnFeeTransactionRepository lateReturnFeeTransactionRepository, BookingPaymentTransactionRepository bookingPaymentTransactionRepository, DamageRepairFeeTransactionRepository damageRepairFeeTransactionRepository) {
        this.transactionRepository = transactionRepository;
        this.bookingRepository = bookingRepository;
        this.employeeRepository = employeeRepository;
        this.imageService = imageService;
        this.lateReturnFeeTransactionRepository = lateReturnFeeTransactionRepository;
        this.bookingPaymentTransactionRepository = bookingPaymentTransactionRepository;
        this.damageRepairFeeTransactionRepository = damageRepairFeeTransactionRepository;
    }

    public BookingPaymentTransaction saveTransactionFromBooking(Integer bookingId, BookingPaymentTransactionDTO dto) {

        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new RuntimeException("Booking not found"));

        BookingPaymentTransaction transaction = toBookingPaymentTransaction(dto);
        transaction.setAmountPaid(booking.getTotalAmount());
        transaction.setBooking(booking);

        return bookingPaymentTransactionRepository.save(transaction);
    }

    @Transactional
    public LateReturnFeeTransaction saveTransactionDueToPenalty(Integer bookingId, LateReturnFeeTransactionDTO dto) {

        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new RuntimeException("Booking not found"));
        booking.setOverDue(true);
        Booking savedBooking = bookingRepository.save(booking);

//        if(dto.amountPaid() < booking.getPenalty()) {
//            throw new InsufficientAmountPaidException();
//        }

        LateReturnFeeTransaction transaction = toLateReturnFeeTransaction(dto);
        transaction.setBooking(savedBooking);
        transaction.setOverDueHours(booking.getOverdueDurationInHours());
        transaction.setAmountPaid(booking.getPenalty());

        return lateReturnFeeTransactionRepository.save(transaction);
    }

    public Map<String, Double> getLateReturnFee(Integer bookingId) {

        Map<String, Double> result = new HashMap<>();

        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new RuntimeException("Booking not found"));

        LocalDateTime actualReturnDate = LocalDateTime.now();

        if(actualReturnDate.isAfter(booking.getEndDateTime())) {

            // Set the overdue flag to true
            booking.setOverDue(true);

            // Compute the overDueDuration in minutes
            long overDueDurationInMinutes = Duration.between(booking.getEndDateTime(), actualReturnDate).toMinutes();

            // Compute the number of overdue hours
            // In here, even a minute past hour is considered already a hour
            int overDueHours = (int) Math.ceil((double) overDueDurationInMinutes / constants.PahiramCarConstants.MINUTES_PER_HOUR);
            booking.setOverdueDurationInHours(overDueHours);

            // Compute the penalty
            double penalty = overDueHours * constants.PahiramCarConstants.PENALTY_PER_HOUR;

            booking.setPenalty(penalty);

            result.put("overDueHours", (double) overDueHours);
            result.put("penalty", penalty);

            bookingRepository.save(booking);
        }

        return result;

    }

    @Transactional
    public DamageRepairFeeTransaction saveTransactionDueToCarDamage(Integer bookingId,
                                                     DamageRepairFeeTransactionDTO dto,
                                                MultipartFile[] multipartFiles) throws IOException {

        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new RuntimeException("Booking not found"));
        booking.setCarReturnWithDamage(true);
        Booking savedBooking = bookingRepository.save(booking);

        DamageRepairFeeTransaction transaction = toDamageRepairFeeTransaction(dto);

        transaction.setBooking(savedBooking);

        Transaction savedTransaction = transactionRepository.save(transaction);
        Integer transactionId = savedTransaction.getTransactionId();

        List<Image> carDamageImages = imageService.saveImages(multipartFiles, ImageOwnerType.TRANSACTION, transactionId);
        transaction.setCarDamageImages(carDamageImages);

        return damageRepairFeeTransactionRepository.save(transaction);
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

    private DamageRepairFeeTransaction toDamageRepairFeeTransaction(DamageRepairFeeTransactionDTO dto) {
        var transaction = new DamageRepairFeeTransaction();
        transaction.setAmountPaid(dto.amountPaid());
        transaction.setPaymentMode(dto.paymentMode());
        transaction.setTransactionType(TransactionType.DAMAGE_REPAIR_FEE);
        transaction.setCarDamageDescription(dto.carDamageDescription());

        Employee employee = employeeRepository.findById(dto.employeeId()).orElseThrow(() -> new RuntimeException("Employee not found"));

        transaction.setEmployee(employee);

        return transaction;
    }

    private LateReturnFeeTransaction toLateReturnFeeTransaction(LateReturnFeeTransactionDTO dto) {

        var transaction = new LateReturnFeeTransaction();
        transaction.setPaymentMode(dto.paymentMode());
        transaction.setTransactionType(TransactionType.LATE_RETURN_FEE);

        Employee employee = employeeRepository.findById(dto.employeeId()).orElseThrow(() -> new RuntimeException("Employee not found"));

        transaction.setEmployee(employee);

        return transaction;
    }

    private BookingPaymentTransaction toBookingPaymentTransaction(BookingPaymentTransactionDTO dto) {
        var transaction = new BookingPaymentTransaction();
        transaction.setAmountPaid(dto.amountPaid());
        transaction.setPaymentMode(dto.paymentMode());
        transaction.setTransactionType(TransactionType.BOOKING_PAYMENT);
        transaction.setDepositAmount(constants.PahiramCarConstants.REFUNDABLE_DEPOSIT);

        Employee employee = employeeRepository.findById(dto.employeeId()).orElseThrow(() -> new RuntimeException("Employee not found"));

        transaction.setEmployee(employee);

        return transaction;
    }
}
