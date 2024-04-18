package com.example.emmisiontracker.constants;

import com.example.emmisiontracker.domain.stats.StatEntry;
import io.leangen.graphql.annotations.types.GraphQLType;

import java.util.Arrays;

public enum TravelMethod {
    FOOT(0),
    BICYCLE(0),
    DIESEL_CAR(.171),
    ELECTRIC_CAR(.047),
    BUS(.097),
    SUBWAY(.028),
    TRAIN(0.035),
    FERRY(.019),
    AIRPLANE(.246);

    private final double emissionPerKilometer;
    TravelMethod(double emissionPerKilometer) {
        this.emissionPerKilometer = emissionPerKilometer;
    }

    public double getEmissionPerKilometer() { return emissionPerKilometer; }
    public static StatEntry[] getAllMethodStats() {
        return Arrays.stream(values())
                .map(t -> StatEntry.Create(t.toString(), t.getEmissionPerKilometer()))
                .toArray(StatEntry[]::new);
    }

}
