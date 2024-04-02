package com.example.Emmision.Tracker.constants;

import java.time.OffsetDateTime;
import java.util.function.Function;

public enum TimeUnit {
    HOUR,
    DAY,
    WEEK,
    MONTH,
    YEAR;

    public static int getUnitCount(TimeUnit unit) {
        return switch (unit) {
            case HOUR -> 60;
            case DAY -> 24;
            case WEEK -> 7;
            case MONTH -> 31;
            case YEAR -> 12;
        };
    }

    public static Function<OffsetDateTime, OffsetDateTime> getUnitStep(TimeUnit unit) {
        return switch(unit) {
            case HOUR -> d -> d.plusMinutes(1);
            case DAY -> d -> d.plusHours(1);
            case WEEK, MONTH -> d -> d.plusDays(1);
            case YEAR -> d -> d.plusMonths(1);
        };
    }
}
