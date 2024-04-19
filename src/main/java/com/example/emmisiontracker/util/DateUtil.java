package com.example.emmisiontracker.util;

import com.example.emmisiontracker.constants.TimeUnit;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

public class DateUtil {

    public static LocalDate truncateDate(LocalDate original, TimeUnit unit) {
        if (unit.equals(TimeUnit.WEEK)) {
            while (original.getDayOfWeek() != DayOfWeek.MONDAY)
                original = original.minusDays(1);
            return original;
        };

        return switch (unit) {
            case MONTH -> original.with(TemporalAdjusters.firstDayOfMonth());
            case YEAR -> original.with(TemporalAdjusters.firstDayOfYear());
            default -> original;
        };
    }

    public static LocalDate deltaDate(LocalDate original, TimeUnit unit, int i) {
        return switch(unit) {
            case DAY -> original.minusDays(i);
            case WEEK -> original.minusWeeks(i);
            case MONTH -> original.minusMonths(i);
            case YEAR -> original.minusYears(i);
        };
    }

    public static LocalDate addDate(LocalDate original, TimeUnit unit, int i) {
        return switch(unit) {
            case DAY -> original.plusDays(i);
            case WEEK -> original.plusWeeks(i);
            case MONTH -> original.plusMonths(i);
            case YEAR -> original.plusYears(i);
        };
    }

    public static boolean isDateBetween(LocalDate localDate, LocalDate min, LocalDate max) {
        return localDate.isEqual(min) || (localDate.isAfter(min) && localDate.isBefore(max));
    }

}
