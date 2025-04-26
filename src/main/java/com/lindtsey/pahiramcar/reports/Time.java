package com.lindtsey.pahiramcar.reports;

import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

public class Time {

    public static LocalDateTime startOfThisMonth() {
        LocalDateTime now = LocalDateTime.now();

        return now.with(TemporalAdjusters.firstDayOfMonth());
    }

    public static LocalDateTime endOfThisMonth() {
        LocalDateTime now = LocalDateTime.now();
        return now.with(TemporalAdjusters.lastDayOfMonth());
    }

    public static LocalDateTime startOfLastMonth() {
        LocalDateTime now = LocalDateTime.now();

        return now.minusMonths(1).with(TemporalAdjusters.firstDayOfMonth());
    }

    public static LocalDateTime endOfLastMonth() {
        LocalDateTime now = LocalDateTime.now();
        return now.minusMonths(1).with(TemporalAdjusters.lastDayOfMonth());
    }
}
