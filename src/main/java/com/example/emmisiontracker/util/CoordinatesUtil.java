package com.example.emmisiontracker.util;

public class CoordinatesUtil {
    public static double distance(float[] pointA, float[] pointB) {
        float lat1 = pointA[0];
        float lon1 = pointA[1];

        float lat2 = pointB[0];
        float lon2 = pointB[1];

        double R = 6371e3; // metres
        double p1 = lat1 * Math.PI/180; // φ, λ in radians
        double p2 = lat2 * Math.PI/180;
        double delta_p = (lat2-lat1) * Math.PI/180;
        double delta_l = (lon2-lon1) * Math.PI/180;

        double a = Math.sin(delta_p/2) * Math.sin(delta_p/2) +
                        Math.cos(p1) * Math.cos(p2) *
                                Math.sin(delta_l/2) * Math.sin(delta_l/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

        double distance = R * c; // in meters

        return distance / 1000;
    }
}
