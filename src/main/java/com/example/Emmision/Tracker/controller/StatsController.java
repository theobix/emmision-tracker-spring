package com.example.Emmision.Tracker.controller;

import com.example.Emmision.Tracker.constants.TravelMethod;
import com.example.Emmision.Tracker.domain.StatEntry;
import com.example.Emmision.Tracker.domain.StatsGroup;
import com.example.Emmision.Tracker.domain.Travel;
import com.example.Emmision.Tracker.repository.TravelRepository;
import com.example.Emmision.Tracker.util.DateUtil;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;
import java.util.stream.Stream;

@Controller
public class StatsController {

    private final TravelRepository travelRepository;

    public StatsController(TravelRepository travelRepository) {
        this.travelRepository = travelRepository;
    }

    @QueryMapping
    public StatsGroup stats(@Argument String unit, @Argument int delta) {
        OffsetDateTime truncatedDate = DateUtil.truncateDate(OffsetDateTime.now(), unit);
        OffsetDateTime startDate = DateUtil.deltaDate(truncatedDate, unit, delta);

        Function<OffsetDateTime, OffsetDateTime> stepDate = switch(unit) {
            case "HOUR" -> d -> d.plusMinutes(1);
            case "DAY" -> d -> d.plusHours(1);
            case "WEEK", "MONTH" -> d -> d.plusDays(1);
            case "YEAR" -> d -> d.plusMonths(1);
            default -> d -> d.plusYears(1);
        };
        int stepCount = switch (unit) {
            case "HOUR" -> 60;
            case "DAY" -> 24;
            case "WEEK" -> 7;
            case "MONTH" -> 31;
            case "YEAR" -> 12;
            default -> 0;
        };

        return getStatGroup(startDate, stepDate, stepCount);
    }

    @QueryMapping
    public Travel[] overallStats() {
        return new Travel[0];
        //return travelRepository.getAll();
    }

    @SchemaMapping(typeName = "StatsGroup", field = "emissions")
    public StatEntry[] getEmissions(StatsGroup statsGroup, @Argument boolean separateMethods) {
        return getStats(statsGroup, Travel::emission, separateMethods, "Emission");
    }

    @SchemaMapping(typeName = "StatsGroup", field = "distances")
    public StatEntry[] getDistances(StatsGroup statsGroup, @Argument boolean separateMethods) {
        return getStats(statsGroup, Travel::distance, separateMethods, "Distance");
    }

    @SchemaMapping(typeName = "OverallStats", field = "totalEmission")
    public float getTotalEmission() {
        return (float) travelRepository.getStream().mapToDouble(Travel::emission).sum();
    }

    @SchemaMapping(typeName = "OverallStats", field = "totalDistance")
    public float getTotalDistance() {
        return (float) travelRepository.getStream().mapToDouble(Travel::distance).sum();
    }

    @SchemaMapping(typeName = "OverallStats", field="totalTravels")
    public int getTravelCount() {
        return travelRepository.getCount();
    }

    @SchemaMapping(typeName = "OverallStats", field = "methodsUsed")
    public TravelMethod[] getAllMethodsUsed() {
        return travelRepository.getStream().map(Travel::method).distinct().toArray(TravelMethod[]::new);
    }

    @SchemaMapping(typeName = "OverallStats", field = "methodDistribution")
    public float[] getMethodDistribution() {
        return getDistribution(s -> (float)s.count());
    }

    @SchemaMapping(typeName = "OverallStats", field = "emissionDistribution")
    public float[] getEmissionDistribution() {
        return getDistribution(s -> (float)s.mapToDouble(Travel::emission).sum());
    }

    private StatsGroup getStatGroup(OffsetDateTime startDate, Function<OffsetDateTime, OffsetDateTime> step, int stepCount) {
        ArrayList<Travel[]> travelsGroups = new ArrayList<>();
        OffsetDateTime[] dates = new OffsetDateTime[stepCount];

        for (int i = 0; i < stepCount; i++) {
            travelsGroups.add(travelRepository.getTravelsBetween(startDate, step.apply(startDate)));

            dates[i] = startDate;
            startDate = step.apply(startDate);
        }

        return new StatsGroup(dates, travelsGroups);
    }


    private StatEntry[] getStats(StatsGroup statsGroup, Function<Travel, Float> getter, boolean separateMethods, String statName) {
        HashMap<String, float[]> stats = new HashMap<>();

        int size = statsGroup.travelGroups().size();
        for (int i = 0; i < size; i++) {
            Travel[] travels = statsGroup.travelGroups().get(i);

            for (Travel travel : travels) {
                String name = separateMethods ? travel.method().toString() : statName;

                if (!stats.containsKey(name))
                    stats.put(name, new float[size]);

                stats.get(name)[i] += getter.apply(travel);
            }
        }

        return stats.entrySet().stream()
                .map(e -> new StatEntry(e.getKey(), e.getValue())).toArray(StatEntry[]::new);
    }

    private float[] getDistribution(Function<Stream<Travel>, Float> reduce)
    {
        TravelMethod[] usedMethods = getAllMethodsUsed();
        float[] methodDistribution = new float[usedMethods.length];

        for (int i = 0; i < usedMethods.length; i++) {
            final TravelMethod method = usedMethods[i];
            Stream<Travel> stream = travelRepository.getStream().filter(t -> t.method().equals(method));

            methodDistribution[i] = reduce.apply(stream);
        }

        return methodDistribution;
    }

}

