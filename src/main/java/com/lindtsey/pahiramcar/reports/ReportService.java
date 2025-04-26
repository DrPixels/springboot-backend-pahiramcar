package com.lindtsey.pahiramcar.reports;

import com.lindtsey.pahiramcar.bookings.BookingRepository;
import com.lindtsey.pahiramcar.customer.CustomerRepository;
import com.lindtsey.pahiramcar.enums.BookingStatus;
import com.lindtsey.pahiramcar.enums.ReservationStatus;
import com.lindtsey.pahiramcar.reservations.ReservationRepository;
import org.antlr.v4.runtime.misc.Pair;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;

@Service
public class ReportService {

    private final CustomerRepository customerRepository;
    private final ReservationRepository reservationRepository;
    private final BookingRepository bookingRepository;

    public ReportService(CustomerRepository customerRepository, ReservationRepository reservationRepository, BookingRepository bookingRepository) {
        this.customerRepository = customerRepository;

        this.reservationRepository = reservationRepository;
        this.bookingRepository = bookingRepository;
    }

    // Get the customer total active reservation
    public int countCustomerTotalActiveReservation(Integer customerId) {
        return reservationRepository.countReservationsByCustomer_UserIdAndStatus(customerId, ReservationStatus.WAITING_FOR_APPROVAL);
    }

    // Get the customer total active reservation
    public int countCustomerTotalActiveReservationWithinThisMonth(Integer customerId) {

        Pair<LocalDateTime, LocalDateTime> startEndOfThisMonth = startEndOfThisMonth();


        return reservationRepository.countReservationsByCustomer_UserIdAndStatus(customerId, ReservationStatus.WAITING_FOR_APPROVAL);
    }

    // Get the customer total active bookings
    public int countCustomerTotalActiveRentals(Integer customerId) {
        return bookingRepository.countBookingsByReservation_Customer_UserIdAndStatus(customerId, BookingStatus.ONGOING);
    }

    // Get the customer total completed bookings
    public int countCustomerTotalCompletedRentals(Integer customerId) {
        return bookingRepository.countBookingsByReservation_Customer_UserIdAndStatus(customerId, BookingStatus.COMPLETED);
    }

    private Pair<LocalDateTime, LocalDateTime> startEndOfThisMonth() {
        LocalDateTime now = LocalDateTime.now();

        LocalDateTime startOfThisMonth = now.with(TemporalAdjusters.firstDayOfMonth());
        LocalDateTime endOfThisMonth = now.with(TemporalAdjusters.lastDayOfMonth());

        return new Pair<>(startOfThisMonth, endOfThisMonth);
    }


}
