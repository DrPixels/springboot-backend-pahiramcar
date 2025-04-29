package com.lindtsey.pahiramcar.utils.shared;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class DateRange {

    private LocalDate startDate;
    private LocalDate endDate;
}
