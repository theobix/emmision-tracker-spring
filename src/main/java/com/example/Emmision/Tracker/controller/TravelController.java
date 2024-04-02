package com.example.Emmision.Tracker.controller;

import com.example.Emmision.Tracker.constants.TravelMethod;
import com.example.Emmision.Tracker.domain.Travel;
import com.example.Emmision.Tracker.domain.TravelMethodInfo;
import com.example.Emmision.Tracker.repository.TravelRepository;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.time.OffsetDateTime;
import java.util.Arrays;

@Controller
public class TravelController {

    private final TravelRepository travelRepository;
    public TravelController(TravelRepository travelRepository) {
        this.travelRepository = travelRepository;
    }

    @QueryMapping
    public TravelMethod[] travelMethods() {
        return TravelMethod.values();
    }

    @QueryMapping
    public Travel travelById(@Argument String id) {
        return travelRepository.getById(id);
    }

    @QueryMapping
    public Travel[] travelHistory(@Argument int page, @Argument int count, @Argument boolean descending) {
        return travelRepository.getHistory(page, count, descending);
    }

    @QueryMapping
    public TravelMethodInfo[] travelMethodInfo() {
        return Arrays.stream(TravelMethod.values())
                .map(t -> new TravelMethodInfo(t, TravelMethod.getUnitEmissions(t)))
                .toArray(TravelMethodInfo[]::new);
    }

    @MutationMapping
    public Travel addTravel(@Argument TravelMethod method, @Argument float distance, @Argument OffsetDateTime datetime) {
        datetime = datetime == null ? OffsetDateTime.now() : datetime;
        return travelRepository.CreateTravel(method, datetime, distance);
    }

}
