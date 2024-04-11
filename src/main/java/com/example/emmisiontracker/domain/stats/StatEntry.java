package com.example.emmisiontracker.domain.stats;

public record StatEntry(String name, double[] data) {

    public static StatEntry Create(String name,  double... data) {
        return new StatEntry(name, data);
    }

}
