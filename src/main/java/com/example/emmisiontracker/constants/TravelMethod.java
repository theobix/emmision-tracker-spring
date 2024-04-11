package com.example.emmisiontracker.constants;

import io.leangen.graphql.annotations.types.GraphQLType;

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

}
