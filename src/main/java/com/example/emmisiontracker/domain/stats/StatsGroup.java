package com.example.emmisiontracker.domain.stats;

import com.example.emmisiontracker.domain.travel.Travel;
import com.example.emmisiontracker.domain.travel.TravelStop;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLQuery;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;

public record StatsGroup(LocalDate[] labels, List<Travel[]> travelGroups) {


    @GraphQLQuery(name = "emissions")
    public StatEntry[] getEmissions(@GraphQLArgument boolean separateMethods) {
        return getStats(TravelStop::emission, separateMethods, "Emission");
    }

    @GraphQLQuery(name = "distances")
    public StatEntry[] getDistances(@GraphQLArgument boolean separateMethods) {
        return getStats(TravelStop::distance, separateMethods, "Distance");
    }

    private StatEntry[] getStats(Function<TravelStop, Double> getter, boolean separateMethods, String statName) {
        HashMap<String, double[]> stats = new HashMap<>();

        int size = travelGroups.size();
        for (int i = 0; i < size; i++) {
            Travel[] travels = travelGroups.get(i);

            for (Travel travel : travels) {
                for (TravelStop travelStop : travel.stops()) {
                    String name = separateMethods ? travelStop.getTravelMethod().toString() : statName;

                    if (!stats.containsKey(name))
                        stats.put(name, new double[size]);

                    stats.get(name)[i] += getter.apply(travelStop);
                }
            }
        }

        return stats.entrySet().stream()
                .map(e -> new StatEntry(e.getKey(), e.getValue())).toArray(StatEntry[]::new);
    }

}
