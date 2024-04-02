package com.example.emmisiontracker.domain;

import com.example.emmisiontracker.constants.TravelMethod;
import com.example.emmisiontracker.repository.TravelRepository;
import io.leangen.graphql.annotations.GraphQLQuery;

import java.util.function.Function;
import java.util.stream.Stream;

public class OverallStats {

    private final TravelRepository travelRepository;
    public  OverallStats(TravelRepository travelRepository) {
        this.travelRepository = travelRepository;
    }

    @GraphQLQuery(name = "totalEmission")
    public float getTotalEmission() {
        return (float) travelRepository.getStream().mapToDouble(Travel::emission).sum();
    }

    @GraphQLQuery(name = "totalDistance")
    public float getTotalDistance() {
        return (float) travelRepository.getStream().mapToDouble(Travel::distance).sum();
    }

   @GraphQLQuery(name = "totalTravels")
    public int getTravelCount() {
        return travelRepository.getCount();
    }

    @GraphQLQuery(name = "methodsUsed")
    public TravelMethod[] getAllMethodsUsed() {
        return travelRepository.getStream().map(Travel::method).distinct().toArray(TravelMethod[]::new);
    }

    @GraphQLQuery(name = "methodDistribution")
    public float[] getMethodDistribution() {
        return getDistribution(s -> (float)s.mapToDouble(Travel::distance).sum());
    }

    @GraphQLQuery(name = "emissionDistribution")
    public float[] getEmissionDistribution() {
        return getDistribution(s -> (float)s.mapToDouble(Travel::emission).sum());
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
