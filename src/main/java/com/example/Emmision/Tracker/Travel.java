package com.example.Emmision.Tracker;

import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;

public record Travel(String id, String method, OffsetDateTime datetime, float distance, int unitEmission, float emission)
    implements Comparable<Travel>{

    private static int COUNTER = 0;
    public static Travel CreateTravel(String method, OffsetDateTime datetime, float distance) {
        int unitEmission = getUnitEmissions(method);
        return new Travel(String.valueOf(++COUNTER), method, datetime,
                distance, unitEmission, distance*unitEmission);
    }

    public static List<String> TRAVEL_METHODS = Arrays.asList(
            "FOOT", "BICYCLE", "DIESEL_CAR", "ELECTRIC_CAR", "BUS", "SUBWAY", "FERRY", "AIRPLANE"
    );
    public static int getUnitEmissions(String method) {
        return switch (method) {
            case "DIESEL_CAR" -> 171;
            case "ELECTRIC_CAR" -> 47;
            case "BUS" -> 97;
            case "SUBWAY" -> 28;
            case "FERRY" -> 19;
            case "AIRPLANE" -> 246;
            default -> 0;
        };
    }

    @Override
    public int compareTo(Travel other) {
        if (this.datetime.equals(other.datetime)) return 0;
        else if (this.datetime.isAfter(other.datetime)) return -1;
        else return 1;
    }
}
