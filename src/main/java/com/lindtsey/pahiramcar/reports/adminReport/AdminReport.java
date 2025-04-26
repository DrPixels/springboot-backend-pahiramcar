package com.lindtsey.pahiramcar.reports.adminReport;

import com.lindtsey.pahiramcar.reports.CarPerformance;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class AdminReport {

    private double totalRevenue;
    private double totalRevenueBeforeThisMonth;
    private int totalBookingsCount;
    private int totalBookingsCountBeforeThisMonth;
    private double averageRentalDays;
    private double averageRentalDaysBeforeThisMonth;
    private int totalCustomersCount;
    private int totalCustomersCountBeforeThisMonth;
    private double totalRevenueByCash;
    private double totalRevenueByDebitCard;
    private double totalRevenueByCreditCard;
    private double totalRevenueByBooking;
    private double totalRevenueByLateReturnFee;
    private double getTotalRevenueByDamageRepairFee;
    private List<CarPerformance> topCarPerformances;
}
