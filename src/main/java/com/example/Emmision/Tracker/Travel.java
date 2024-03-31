package com.example.Emmision.Tracker;

import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;

public record Travel(String id, String method, OffsetDateTime datetime, float distance, float unitEmission, float emission)
    implements Comparable<Travel>{

    private static int COUNTER = 0;
    public static Travel CreateTravel(String method, OffsetDateTime datetime, float distance) {
        float unitEmission = getUnitEmissions(method);
        return new Travel(String.valueOf(++COUNTER), method, datetime,
                distance, unitEmission, distance*unitEmission);
    }

    public static List<String> TRAVEL_METHODS = Arrays.asList(
            "FOOT", "BICYCLE", "DIESEL_CAR", "ELECTRIC_CAR", "BUS", "SUBWAY", "FERRY", "AIRPLANE"
    );
    public static float getUnitEmissions(String method) {
        return switch (method) {
            case "DIESEL_CAR" -> .171f;
            case "ELECTRIC_CAR" -> .047f;
            case "BUS" -> .097f;
            case "SUBWAY" -> .028f;
            case "FERRY" -> .019f;
            case "AIRPLANE" -> .246f;
            default -> 0f;
        };
    }

    @Override
    public int compareTo(Travel other) {
        if (this.datetime.equals(other.datetime)) return 0;
        else if (this.datetime.isAfter(other.datetime)) return -1;
        else return 1;
    }
}
