package com.example.emmisiontracker.domain;

import com.example.emmisiontracker.constants.TravelMethod;

import java.time.LocalDate;

public record Travel(String id, TravelMethod method, LocalDate date, float distance, float emission)
    implements Comparable<Travel> {

    @Override
    public int compareTo(Travel other) {
        if (this.date.equals(other.date)) return 0;
        else if (this.date.isAfter(other.date)) return -1;
        else return 1;
    }
}
