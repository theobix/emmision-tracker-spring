package com.example.Emmision.Tracker;

import com.example.Emmision.Tracker.Repositories.TravelsRepository;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Function;
import java.util.stream.Stream;

@Controller
public class StatsController {

    private final TravelsRepository travelsRepository;

    public StatsController(TravelsRepository travelsRepository) {
        this.travelsRepository = travelsRepository;
    }

    @QueryMapping
    public StatsGroup stats(@Argument String unit, @Argument int delta) {
        OffsetDateTime truncatedDate = Stats.truncateDate(OffsetDateTime.now(), unit);
        OffsetDateTime startDate = Stats.deltaDate(truncatedDate, unit, delta);

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
    public Travel[] overallStats() { return travelsRepository.getAll(); }

    @SchemaMapping(typeName = "StatsGroup", field = "emissions")
    public Stats[] getEmissions(StatsGroup statsGroup, @Argument boolean separateMethods) {
        return getStats(statsGroup, Travel::emission, separateMethods, "Emission");
    }

    @SchemaMapping(typeName = "StatsGroup", field = "distances")
    public Stats[] getDistances(StatsGroup statsGroup, @Argument boolean separateMethods) {
        return getStats(statsGroup, Travel::distance, separateMethods, "Distance");
    }

    @SchemaMapping(typeName = "OverallStats", field = "totalEmission")
    public float getTotalEmission() {
        return (float)travelsRepository.getStream().mapToDouble(Travel::emission).sum();
    }

    @SchemaMapping(typeName = "OverallStats", field = "totalDistance")
    public float getTotalDistance() {
        return (float)travelsRepository.getStream().mapToDouble(Travel::distance).sum();
    }

    @SchemaMapping(typeName = "OverallStats", field="totalTravels")
    public int getTravelCount() {
        return travelsRepository.getCount();
    }

    @SchemaMapping(typeName = "OverallStats", field = "methodsUsed")
    public String[] getAllMethodsUsed() {
        return travelsRepository.getStream().map(Travel::method).distinct().toArray(String[]::new);
    }

    @SchemaMapping(typeName = "OverallStats", field = "methodDistribution")
    public float[] getMethodDistribution() {
        return getDistribution(s -> (float)s.mapToDouble(Travel::distance).sum());
    }

    @SchemaMapping(typeName = "OverallStats", field = "emissionDistribution")
    public float[] getEmissionDistribution() {
        return getDistribution(s -> (float)s.mapToDouble(Travel::emission).sum());
    }

    private StatsGroup getStatGroup(OffsetDateTime startDate, Function<OffsetDateTime, OffsetDateTime> step, int stepCount) {
        ArrayList<Travel[]> travelsGroups = new ArrayList<>();
        OffsetDateTime[] dates = new OffsetDateTime[stepCount];

        for (int i = 0; i < stepCount; i++) {
            travelsGroups.add(travelsRepository.getTravelsBetween(startDate, step.apply(startDate)));

            dates[i] = startDate;
            startDate = step.apply(startDate);
        }

        return new StatsGroup(dates, travelsGroups);
    }


    private Stats[] getStats(StatsGroup statsGroup, Function<Travel, Float> getter, boolean separateMethods, String statName) {
        HashMap<String, float[]> stats = new HashMap<>();

        int size = statsGroup.travelGroups().size();
        for (int i = 0; i < size; i++) {
            Travel[] travels = statsGroup.travelGroups().get(i);

            for (Travel travel : travels) {
                String name = separateMethods ? travel.method() : statName;

                if (!stats.containsKey(name))
                    stats.put(name, new float[size]);

                stats.get(name)[i] += getter.apply(travel);
            }
        }

        return stats.entrySet().stream()
                .map(e -> new Stats(e.getKey(), e.getValue())).toArray(Stats[]::new);
    }

    private float[] getDistribution(Function<Stream<Travel>, Float> reduce)
    {
        String[] usedMethods = getAllMethodsUsed();
        float[] methodDistribution = new float[usedMethods.length];

        for (int i = 0; i < usedMethods.length; i++) {
            final String method = usedMethods[i];
            Stream<Travel> stream = travelsRepository.getStream().filter(t -> t.method().equals(method));

            methodDistribution[i] = reduce.apply(stream);
        }

        return methodDistribution;
    }

}

