package com.lindtsey.pahiramcar.reports.adminReport;

import com.lindtsey.pahiramcar.bookings.BookingRepository;
import com.lindtsey.pahiramcar.bookings.BookingService;
import com.lindtsey.pahiramcar.customer.CustomerService;
import com.lindtsey.pahiramcar.enums.PaymentMode;
import com.lindtsey.pahiramcar.enums.TransactionType;
import com.lindtsey.pahiramcar.reports.CarPerformance;
import com.lindtsey.pahiramcar.reports.Time;
import com.lindtsey.pahiramcar.transactions.TransactionRepository;
import com.lindtsey.pahiramcar.transactions.childClass.bookingPayment.BookingPaymentTransactionRepository;
import com.lindtsey.pahiramcar.transactions.childClass.damageRepairFee.DamageRepairFeeTransactionRepository;
import com.lindtsey.pahiramcar.transactions.childClass.lateReturnFee.LateReturnFeeTransactionRepository;
import com.lindtsey.pahiramcar.utils.constants;
import com.lindtsey.pahiramcar.utils.sorter.TopCarPerformanceSorter;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AdminReportService {

    private final TransactionRepository transactionRepository;
    private final BookingRepository bookingRepository;
    private final BookingService bookingService;
    private final CustomerService customerService;
    private final BookingPaymentTransactionRepository bookingPaymentTransactionRepository;
    private final LateReturnFeeTransactionRepository lateReturnFeeTransactionRepository;
    private final DamageRepairFeeTransactionRepository damageRepairFeeTransactionRepository;

    public AdminReportService(TransactionRepository transactionRepository, BookingRepository bookingRepository, BookingService bookingService, CustomerService customerService, BookingPaymentTransactionRepository bookingPaymentTransactionRepository, BookingPaymentTransactionRepository bookingPaymentTransactionRepository1, LateReturnFeeTransactionRepository lateReturnFeeTransactionRepository, DamageRepairFeeTransactionRepository damageRepairFeeTransactionRepository) {
        this.transactionRepository = transactionRepository;
        this.bookingRepository = bookingRepository;
        this.bookingService = bookingService;
        this.customerService = customerService;
        this.bookingPaymentTransactionRepository = bookingPaymentTransactionRepository1;
        this.lateReturnFeeTransactionRepository = lateReturnFeeTransactionRepository;
        this.damageRepairFeeTransactionRepository = damageRepairFeeTransactionRepository;
    }

    public AdminReport getAdminReport() {

        return AdminReport.builder()
                .totalRevenue(totalRevenue())
                .totalRevenueBeforeThisMonth(totalRevenueBeforeThisMonth())
                .totalBookingsCount(totalBookingsCount())
                .totalBookingsCountBeforeThisMonth(totalBookingsCountBeforeThisMonth())
                .averageRentalDays(averageRentalDays())
                .averageRentalDaysBeforeThisMonth(averageRentalDaysBeforeThisMonth())
                .totalCustomersCount(totalCustomersCount())
                .totalCustomersCountBeforeThisMonth(totalCustomerCountBeforeThisMonth())
                .totalRevenueByCash(totalRevenueByPaymentMode(PaymentMode.CASH))
                .totalRevenueByDebitCard(totalRevenueByPaymentMode(PaymentMode.DEBIT_CARD))
                .totalRevenueByCreditCard(totalRevenueByPaymentMode(PaymentMode.CREDIT_CARD))
                .totalRevenueByBooking(totalRevenueByTransactionType(TransactionType.BOOKING_PAYMENT))
                .totalRevenueByLateReturnFee(totalRevenueByTransactionType(TransactionType.LATE_RETURN_FEE))
                .getTotalRevenueByDamageRepairFee(totalRevenueByTransactionType(TransactionType.DAMAGE_REPAIR_FEE))
                .topCarPerformances(carPerformances())
                .build();
    }

    public AdminCustomReport getAdminCustomReport(LocalDateTime start, LocalDateTime end) {

        return AdminCustomReport.builder()
                .totalRevenueBetween(totalRevenueBetween(start, end))
                .totalBookingsCountBetween(totalBookingsCountBetween(start, end))
                .averageRentalDaysBetween(averageRentalDaysBetween(start, end))
                .totalCustomersCountBetween(totalCustomerCountBetween(start, end))
                .totalRevenueByCashBetween(totalRevenueByPaymentModeBetween(PaymentMode.CASH, start, end))
                .totalRevenueByDebitCardBetween(totalRevenueByPaymentModeBetween(PaymentMode.DEBIT_CARD, start, end))
                .totalRevenueByCreditCardBetween(totalRevenueByPaymentModeBetween(PaymentMode.CREDIT_CARD, start, end))
                .totalRevenueByBookingBetween(totalRevenueByTransactionTypeBetween(TransactionType.BOOKING_PAYMENT, start, end))
                .totalRevenueByLateReturnFeeBetween(totalRevenueByTransactionTypeBetween(TransactionType.LATE_RETURN_FEE, start, end))
                .getTotalRevenueByDamageRepairFeeBetween(totalRevenueByTransactionTypeBetween(TransactionType.DAMAGE_REPAIR_FEE, start, end))
                .topCarPerformancesBetween(carPerformancesBetween(start, end))
                .build();
    }

    public double totalRevenue() {
        return bookingPaymentTransactionRepository.totalBookingPaymentRevenue() +
                lateReturnFeeTransactionRepository.totalLateReturnFeeFeeRevenue() +
                damageRepairFeeTransactionRepository.totalDamageRepairFeeRevenue();
    }

    public double totalRevenueBeforeThisMonth() {
        return bookingPaymentTransactionRepository.totalBookingPaymentRevenueBeforeThisMonth(Time.startOfThisMonth()) +
                lateReturnFeeTransactionRepository.totalLateReturnFeeRevenueBeforeThisMonth(Time.startOfThisMonth()) +
                damageRepairFeeTransactionRepository.totalDamageRepairFeeRevenueBeforeThisMonth(Time.startOfThisMonth());
    }

    public double totalRevenueBetween(LocalDateTime startTime, LocalDateTime endTime) {
        return bookingPaymentTransactionRepository.totalBookingPaymentRevenueBetween(startTime, endTime) +
                lateReturnFeeTransactionRepository.totalLateReturnFeeRevenueBetween(startTime, endTime) +
                damageRepairFeeTransactionRepository.totalDamageRepairFeeRevenueBetween(startTime, endTime);
    }

    public int totalBookingsCount() {
        return (int) bookingRepository.count();
    }

    public int totalBookingsCountBeforeThisMonth() {
        return bookingRepository.countBookingsByStartDateTimeBefore(Time.startOfThisMonth());
    }

    public int totalBookingsCountBetween(LocalDateTime startTime, LocalDateTime endTime) {
        return (int) bookingRepository.countBookingsByStartDateTimeBetween(startTime, endTime);
    }

//    public int totalBookingsCountFromLastMonth() {
//        return (int) bookingRepository.countBookingsByStartDateTimeBetween(Time.startOfLastMonth(), Time.endOfLastMonth());
//    }

    public double averageRentalDays() {
        return bookingService.calculateAverageRentalTimeInDays();
    }

    public double averageRentalDaysBeforeThisMonth() {
        return bookingService.calculateAverageRentalTimeInDaysBeforeThisMonth();
    }

    public double averageRentalDaysBetween(LocalDateTime startTime, LocalDateTime endTime) {
        return bookingService.calculateAverageRentalTimeInDaysBetween(startTime, endTime);
    }

    public int totalCustomersCount() {
        return customerService.countTotalCustomer();
    }

    public int totalCustomerCountBeforeThisMonth() {
        return customerService.countTotalCustomerBeforeThisMonth();
    }

    public int totalCustomerCountBetween(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return customerService.countTotalCustomerBetween(startDateTime, endDateTime);
    }

    public double totalRevenueByPaymentMode(PaymentMode paymentMode) {
        return transactionRepository.totalRevenueByPaymentMode(paymentMode);
    }

    public double totalRevenueByPaymentModeBetween(PaymentMode paymentMode, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return transactionRepository.totalRevenueByPaymentModeBetween(paymentMode, startDateTime, endDateTime);
    }

    public double totalRevenueByTransactionType(TransactionType transactionType) {

        switch (transactionType) {
            case TransactionType.BOOKING_PAYMENT -> {
                return bookingPaymentTransactionRepository.totalBookingPaymentRevenue();
            }
            case LATE_RETURN_FEE -> {
                return lateReturnFeeTransactionRepository.totalLateReturnFeeFeeRevenue();
            }
            case DAMAGE_REPAIR_FEE -> {
                return damageRepairFeeTransactionRepository.totalDamageRepairFeeRevenue();
            }
            default -> throw new IllegalArgumentException("Unsupported transaction type: " + transactionType);
        }
    }

    public double totalRevenueByTransactionTypeBetween(TransactionType transactionType, LocalDateTime startDateTime, LocalDateTime endDateTime) {

        switch (transactionType) {
            case TransactionType.BOOKING_PAYMENT -> {
                return bookingPaymentTransactionRepository.totalBookingPaymentRevenueBetween(startDateTime, endDateTime);
            }
            case LATE_RETURN_FEE -> {
                return lateReturnFeeTransactionRepository.totalLateReturnFeeRevenueBetween(startDateTime, endDateTime);
            }
            case DAMAGE_REPAIR_FEE -> {
                return damageRepairFeeTransactionRepository.totalDamageRepairFeeRevenueBetween(startDateTime, endDateTime);
            }
            default -> throw new IllegalArgumentException("Unsupported transaction type: " + transactionType);
        }
    }

    public List<CarPerformance> carPerformances() {
        List<CarPerformance> tentativeCarPerformances = bookingRepository.findAllBookedCarsAndCount();

        for(CarPerformance carPerformance: tentativeCarPerformances) {
            Integer carId = carPerformance.getCar().getCarId();

            carPerformance.setRevenueGenerated(bookingPaymentTransactionRepository.totalBookingPaymentRevenueByCar(carId) +
                    lateReturnFeeTransactionRepository.totalLateReturnFeeFeeRevenueByCar(carId) +
                    damageRepairFeeTransactionRepository.totalDamageRepairFeeRevenueByCar(carId));
        }
        TopCarPerformanceSorter.mergeSortBookings(tentativeCarPerformances);

        return tentativeCarPerformances;
    }

    public List<CarPerformance> carPerformancesBetween(LocalDateTime startTime, LocalDateTime endTime) {
        List<CarPerformance> tentativeCarPerformances = bookingRepository.findAllBookedCarsAndCountBetween(startTime, endTime);

        for(CarPerformance carPerformance: tentativeCarPerformances) {
            Integer carId = carPerformance.getCar().getCarId();

            carPerformance.setRevenueGenerated(bookingPaymentTransactionRepository.totalBookingPaymentRevenueByCar(carId) +
                    lateReturnFeeTransactionRepository.totalLateReturnFeeFeeRevenueByCar(carId) +
                    damageRepairFeeTransactionRepository.totalDamageRepairFeeRevenueByCar(carId));
        }
        TopCarPerformanceSorter.mergeSortBookings(tentativeCarPerformances);

        return tentativeCarPerformances;
    }



}
