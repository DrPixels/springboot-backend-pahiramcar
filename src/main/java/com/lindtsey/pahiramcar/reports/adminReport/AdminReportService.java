package com.lindtsey.pahiramcar.reports.adminReport;

import com.lindtsey.pahiramcar.bookings.BookingRepository;
import com.lindtsey.pahiramcar.bookings.BookingService;
import com.lindtsey.pahiramcar.customer.CustomerService;
import com.lindtsey.pahiramcar.enums.PaymentMode;
import com.lindtsey.pahiramcar.reports.Time;
import com.lindtsey.pahiramcar.transactions.TransactionRepository;
import com.lindtsey.pahiramcar.utils.constants;
import org.springframework.stereotype.Service;

@Service
public class AdminReportService {

    private final TransactionRepository transactionRepository;
    private final BookingRepository bookingRepository;
    private final BookingService bookingService;
    private final CustomerService customerService;

    public AdminReportService(TransactionRepository transactionRepository, BookingRepository bookingRepository, BookingService bookingService, CustomerService customerService) {
        this.transactionRepository = transactionRepository;
        this.bookingRepository = bookingRepository;
        this.bookingService = bookingService;
        this.customerService = customerService;
    }

    // Only includes the payments and penalties
    // Do not include the car damages
    public double totalRevenue() {
        return transactionRepository.totalRevenue(constants.PahiramCarConstants.REFUNDABLE_DEPOSIT);
    }

    public double totalRevenueBeforeThisMonth() {
        return transactionRepository.totalRevenueBeforeThisMonth(constants.PahiramCarConstants.REFUNDABLE_DEPOSIT, Time.startOfThisMonth());
    }

    // Only includes the payments and penalties
    // Do not include the car damages
    // For this current month
    public double totalRevenueWithinThisMonth() {
        return transactionRepository.totalRevenueBetweenThisTime(constants.PahiramCarConstants.REFUNDABLE_DEPOSIT, Time.startOfThisMonth(), Time.endOfThisMonth());
    }

    public double totalRevenueFromLastMonth() {
        return transactionRepository.totalRevenueBetweenThisTime(constants.PahiramCarConstants.REFUNDABLE_DEPOSIT, Time.startOfLastMonth(), Time.endOfLastMonth());
    }

    public int totalBookingsCount() {
        return (int) bookingRepository.count();
    }

    public int totalBookingsCountBeforeThisMonth() {
        return bookingRepository.countBookingsByStartDateTimeBefore(Time.startOfThisMonth());
    }

    public int totalBookingsCountBetweenThisMonth() {
        return (int) bookingRepository.countBookingsByStartDateTimeBetween(Time.startOfThisMonth(), Time.endOfThisMonth());
    }

    public int totalBookingsCountFromLastMonth() {
        return (int) bookingRepository.countBookingsByStartDateTimeBetween(Time.startOfLastMonth(), Time.endOfLastMonth());
    }

    public double averageRentalDays() {
        return bookingService.calculateAverageRentalTimeInDays();
    }

    public double averageRentalDaysBeforeThisMonth() {
        return bookingService.calculateAverageRentalTimeInDaysBeforeThisMonth();
    }

    public int totalCustomersCount() {
        return customerService.countTotalCustomer();
    }

    public int totalCustomerCountBeforeThisMonth() {
        return customerService.countTotalCustomerBeforeThisMonth();
    }

    public double totalRevenueByPaymentMode(PaymentMode paymentMode) {
        return transactionRepository.totalRevenueByPaymentMode(constants.PahiramCarConstants.REFUNDABLE_DEPOSIT, paymentMode);
    }





}
