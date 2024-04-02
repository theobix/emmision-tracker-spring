package com.example.Emmision.Tracker.domain;

import java.time.DayOfWeek;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;

public record StatEntry(String name, float[] data) {

}
