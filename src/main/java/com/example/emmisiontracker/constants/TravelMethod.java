package com.example.emmisiontracker.constants;

public enum TravelMethod {
    FOOT,
    BICYCLE,
    DIESEL_CAR,
    ELECTRIC_CAR,
    BUS,
    SUBWAY,
    TRAIN,
    FERRY,
    AIRPLANE;

    public static float getUnitEmissions(TravelMethod method) {
        return switch (method) {
            case DIESEL_CAR -> .171f;
            case ELECTRIC_CAR -> .047f;
            case BUS -> .097f;
            case SUBWAY -> .028f;
            case FERRY -> .019f;
            case AIRPLANE -> .246f;
            case TRAIN -> .035f;
            default -> 0f;
        };
    }
}
