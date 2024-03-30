package com.example.Emmision.Tracker;

import java.time.DayOfWeek;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;

public record Stats(String name, float[] data) {

    public static OffsetDateTime truncateDate(OffsetDateTime original, String unit) {
        if (unit.equals("WEEK")) {
            while (original.getDayOfWeek() != DayOfWeek.MONDAY) original = original.minusDays(1);
            return original.truncatedTo(ChronoUnit.DAYS);
        };

        return switch (unit) {
            case "HOUR" -> original.minusHours(1);
            case "DAY" -> original.truncatedTo(ChronoUnit.DAYS);
            case "MONTH" -> original.with(TemporalAdjusters.firstDayOfMonth()).truncatedTo(ChronoUnit.DAYS);
            case "YEAR" -> original.with(TemporalAdjusters.firstDayOfYear()).truncatedTo(ChronoUnit.DAYS);
            default -> original;
        };
    }

    public static OffsetDateTime deltaDate(OffsetDateTime original, String unit, int i) {
        return switch(unit) {
            case "HOUR" -> original.minusHours(i);
            case "DAY" -> original.minusDays(i);
            case "WEEK" -> original.minusWeeks(i);
            case "MONTH" -> original.minusMonths(i);
            case "YEAR" -> original.minusYears(i);
            default -> original;
        };
    }
}
