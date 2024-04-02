package com.example.Emmision.Tracker.domain;

import com.example.Emmision.Tracker.constants.TravelMethod;

import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;

public record Travel(String id, TravelMethod method, OffsetDateTime datetime, float distance, float unitEmission, float emission)
    implements Comparable<Travel> {

    @Override
    public int compareTo(Travel other) {
        if (this.datetime.equals(other.datetime)) return 0;
        else if (this.datetime.isAfter(other.datetime)) return -1;
        else return 1;
    }
}
