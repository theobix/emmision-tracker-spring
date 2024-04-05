package com.example.emmisiontracker.domain.travel;

import com.example.emmisiontracker.constants.TravelMethod;
import io.leangen.graphql.annotations.GraphQLQuery;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.function.ToDoubleFunction;
import java.util.stream.Collectors;

public record Travel(String id, LocalDate date, WorldPoint start, TravelStop[] stops, double distance, double emission)
        implements Comparable<Travel> {

    public Travel(String id, LocalDate date, WorldPoint start, TravelStop[] stops) {
        this(id, date, start, stops,
                reduceStops(stops, TravelStop::distance),
                reduceStops(stops, TravelStop::emission));
    }
    public static double reduceStops(TravelStop[] stops, ToDoubleFunction<TravelStop> reduce) {
        return Arrays.stream(stops).mapToDouble(reduce).sum();
    }

    public Collection<TravelMethod> travelMethods() {
        return Arrays.stream(stops).map(TravelStop::getTravelMethod)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public Collection<TravelStop> getStopsWithMethod(TravelMethod method) {
        return Arrays.stream(stops).filter(s -> s.getTravelMethod().equals(method))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @GraphQLQuery(name = "stops", description = "Get all travel stops of this travel")
    public TravelStop[] getAllStops() { return stops; }

    @GraphQLQuery(name = "start", description = "Get the first point of this travel")
    public WorldPoint getStartPoint() { return start; }

    @GraphQLQuery(name = "end", description = "Get the first point of this travel")
    public WorldPoint getLastPoint() { return stops[stops.length - 1].getPoint(); }


    @Override
    public int compareTo(Travel other) {
        if (this.date.equals(other.date)) return 0;
        else if (this.date.isAfter(other.date)) return -1;
        else return 1;
    }
}
