package com.example.Emmision.Tracker;

import com.example.Emmision.Tracker.Repositories.TravelsRepository;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.time.OffsetDateTime;

@Controller
public class TravelController {

    private final TravelsRepository travelsRepository;
    public TravelController(TravelsRepository travelsRepository) {
        this.travelsRepository = travelsRepository;
    }

    @QueryMapping
    public String[] travelMethods() {
        return Travel.TRAVEL_METHODS.toArray(String[]::new);
    }

    @QueryMapping
    public Travel travelById(@Argument String id) {
        return travelsRepository.getById(id);
    }

    @QueryMapping
    public Travel[] travelHistory(@Argument int page, @Argument int count, @Argument boolean descending) {
        return travelsRepository.getHistory(page, count, descending);
    }

    @QueryMapping
    public TravelMethodInfo[] travelMethodInfo() {
        return Travel.TRAVEL_METHODS.stream()
                .map(t -> new TravelMethodInfo(t, Travel.getUnitEmissions(t)))
                .toArray(TravelMethodInfo[]::new);
    }

    @MutationMapping
    public Travel addTravel(@Argument String method, @Argument float distance, @Argument OffsetDateTime datetime) {
        datetime = datetime == null ? OffsetDateTime.now() : datetime;
        return travelsRepository.addTravel(method, distance, datetime);
    }

}
