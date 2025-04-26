package com.lindtsey.pahiramcar.reports.adminReport;

import com.lindtsey.pahiramcar.reports.CarPerformance;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class AdminCustomReport {

    private double totalRevenueBetween;
    private int totalBookingsCountBetween;
    private double averageRentalDaysBetween;
    private int totalCustomersCountBetween;
    private double totalRevenueByCashBetween;
    private double totalRevenueByDebitCardBetween;
    private double totalRevenueByCreditCardBetween;
    private double totalRevenueByBookingBetween;
    private double totalRevenueByLateReturnFeeBetween;
    private double getTotalRevenueByDamageRepairFeeBetween;
    private List<CarPerformance> topCarPerformancesBetween;
}
