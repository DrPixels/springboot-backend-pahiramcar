package com.lindtsey.pahiramcar.reports.customerReport;

import com.lindtsey.pahiramcar.bookings.BookingRepository;
import com.lindtsey.pahiramcar.customer.CustomerRepository;
import com.lindtsey.pahiramcar.enums.BookingStatus;
import com.lindtsey.pahiramcar.enums.ReservationStatus;
import com.lindtsey.pahiramcar.reports.Time;
import com.lindtsey.pahiramcar.reservations.ReservationRepository;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

@Service
public class CustomerReportService {

    private final CustomerRepository customerRepository;
    private final ReservationRepository reservationRepository;
    private final BookingRepository bookingRepository;

    public CustomerReportService(CustomerRepository customerRepository, ReservationRepository reservationRepository, BookingRepository bookingRepository) {
        this.customerRepository = customerRepository;

        this.reservationRepository = reservationRepository;
        this.bookingRepository = bookingRepository;
    }

    public CustomerReport getCustomerReport(Integer customerId) {
        // Get the total active reservation count
        int totalCustomerActiveReservation = countCustomerTotalActiveReservation(customerId);
        // Get the total active reservation count for this month
        int totalCustomerActiveReservationWithinThisMonth = countCustomerTotalActiveReservationWithinThisMonth(customerId);
        // Get the total active bookings
        int totalCustomerActiveBookings = countCustomerTotalActiveRentals(customerId);
        // Get the total active bookings for this month
        int totalCustomerActiveBookingsWithinThisMonth = countCustomerTotalActiveRentalsWithinThisMonth(customerId);
        // Get the total completed bookings
        int totalCustomerCompletedBookings = countCustomerTotalCompletedRentals(customerId);
        // Get the total completed bookings for this month
        int totalCustomerCompletedBookingsWithinThisMonth = countCustomerTotalCompletedRentalsWithinThisMonth(customerId);

        return CustomerReport.builder()
                .totalCustomerActiveReservation(totalCustomerActiveReservation)
                .totalCustomerActiveReservationWithinThisMonth(totalCustomerActiveReservationWithinThisMonth)
                .totalCustomerActiveBookings(totalCustomerActiveBookings)
                .totalCustomerActiveBookingsWithinThisMonth(totalCustomerActiveBookingsWithinThisMonth)
                .totalCustomerCompletedBookings(totalCustomerCompletedBookings)
                .totalCustomerCompletedBookings(totalCustomerCompletedBookingsWithinThisMonth)
                .build();
    }

    // Get the customer total active reservation
    private int countCustomerTotalActiveReservation(Integer customerId) {
        return reservationRepository.countActiveReservationsByCustomer_UserIdAndStatus(customerId, ReservationStatus.WAITING_FOR_APPROVAL);
    }

    // Get the customer total active reservation
    private int countCustomerTotalActiveReservationWithinThisMonth(Integer customerId) {

        return reservationRepository.countActiveReservationsByCustomer_UserIdAndStatusAndStartDateTimeBetween(customerId, ReservationStatus.WAITING_FOR_APPROVAL, Time.startOfThisMonth(), Time.endOfThisMonth());
    }

    // Get the customer total active bookings
    private int countCustomerTotalActiveRentals(Integer customerId) {
        return bookingRepository.countActiveBookingsByReservation_Customer_UserIdAndStatus(customerId, BookingStatus.ONGOING);
    }

    private int countCustomerTotalActiveRentalsWithinThisMonth(Integer customerId) {

        return bookingRepository.countActiveBookingsByReservation_Customer_UserIdAndStatusAndStartDateTimeBetween(customerId, BookingStatus.ONGOING, Time.startOfThisMonth(), Time.endOfThisMonth());
    }

    private int countCustomerTotalCompletedRentalsWithinThisMonth(Integer customerId) {

        return bookingRepository.countActiveBookingsByReservation_Customer_UserIdAndStatusAndStartDateTimeBetween(customerId, BookingStatus.COMPLETED, Time.startOfThisMonth(), Time.endOfThisMonth());
    }

    // Get the customer total completed bookings
    private int countCustomerTotalCompletedRentals(Integer customerId) {
        return bookingRepository.countActiveBookingsByReservation_Customer_UserIdAndStatus(customerId, BookingStatus.COMPLETED);
    }

}
