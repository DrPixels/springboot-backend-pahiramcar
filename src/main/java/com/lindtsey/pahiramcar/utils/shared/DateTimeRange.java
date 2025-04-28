package com.lindtsey.pahiramcar.utils.shared;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DateTimeRange {

    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
}
