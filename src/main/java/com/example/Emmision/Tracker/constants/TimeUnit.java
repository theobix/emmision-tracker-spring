package com.example.Emmision.Tracker.constants;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.function.Function;

public enum TimeUnit {
    DAY,
    WEEK,
    MONTH,
    YEAR;

    public static int getUnitCount(TimeUnit unit) {
        return switch (unit) {
            case DAY -> 24;
            case WEEK -> 7;
            case MONTH -> 31;
            case YEAR -> 12;
        };
    }

    public static Function<LocalDate, LocalDate> getUnitStep(TimeUnit unit) {
        return switch(unit) {
            case WEEK, MONTH -> d -> d.plusDays(1);
            case YEAR -> d -> d.plusMonths(1);
            default -> d -> d.plusYears(1);
        };
    }
}
