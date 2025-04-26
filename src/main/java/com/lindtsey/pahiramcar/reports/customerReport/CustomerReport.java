package com.lindtsey.pahiramcar.reports.customerReport;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerReport {

    private int totalCustomerActiveReservation;
    private int totalCustomerActiveReservationWithinThisMonth;
    private int totalCustomerActiveBookings;
    private int totalCustomerActiveBookingsWithinThisMonth;
    private int totalCustomerCompletedBookings;
    private int totalCustomerCompletedBookingsWithinThisMonth;

}
