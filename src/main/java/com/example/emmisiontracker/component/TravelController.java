package com.example.emmisiontracker.component;

import com.example.emmisiontracker.constants.TravelMethod;
import com.example.emmisiontracker.domain.travel.Travel;
import com.example.emmisiontracker.domain.travel.TravelMethodInfo;
import com.example.emmisiontracker.domain.travel.TravelStop;
import com.example.emmisiontracker.domain.travel.WorldPoint;
import com.example.emmisiontracker.repository.TravelRepository;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Arrays;

@Component
@GraphQLApi
public class TravelController {

    private final TravelRepository travelRepository;
    public TravelController(TravelRepository travelRepository) {
        this.travelRepository = travelRepository;
    }

    @GraphQLQuery(description = "Get list of all possible travel methods")
    public TravelMethod[] travelMethods() {
        return TravelMethod.values();
    }

    @GraphQLQuery(description = "Get travel by it's unique ID")
    public Travel travelById(@GraphQLArgument String id) {
        return travelRepository.getById(id);
    }

    @GraphQLQuery(description = "Get travel history sorted by date and organised by pages")
    public Travel[] travelHistory(@GraphQLArgument int page, @GraphQLArgument int count, @GraphQLArgument boolean descending) {
        return travelRepository.getHistory(page, count, descending);
    }

    @GraphQLQuery(description = "Get info about every possible travel method")
    public TravelMethodInfo[] travelMethodInfo() {
        return Arrays.stream(TravelMethod.values())
                .map(t -> new TravelMethodInfo(t, TravelMethod.getUnitEmissions(t)))
                .toArray(TravelMethodInfo[]::new);
    }

    @GraphQLMutation
    public Travel addTravel(@GraphQLArgument LocalDate date,
                            @GraphQLArgument WorldPoint start,
                            @GraphQLArgument TravelStop[] stops) {

        if (date == null) date = LocalDate.now();

        WorldPoint previousPoint = start;
        for (TravelStop stop : stops) {
            stop.setData(previousPoint);
            previousPoint = stop.getPoint();
        }

        return travelRepository.CreateTravel(date, start, stops);
    }

}
