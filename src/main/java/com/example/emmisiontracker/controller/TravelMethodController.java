package com.example.emmisiontracker.controller;

import com.example.emmisiontracker.annotation.GraphQLController;
import com.example.emmisiontracker.constants.TravelMethod;
import com.example.emmisiontracker.domain.stats.StatEntry;
import com.example.emmisiontracker.repository.TravelRepository;
import io.leangen.graphql.annotations.GraphQLQuery;

import java.util.Arrays;

@GraphQLController
public class TravelMethodController {

    @GraphQLQuery(description = "Get list of all possible travel methods")
    public TravelMethod[] travelMethods() {
        return TravelMethod.values();
    }

    @GraphQLQuery(description = "Get info about every possible travel method")
    public StatEntry[] travelMethodInfo() {
        return Arrays.stream(TravelMethod.values())
                .map(t -> StatEntry.Create(t.toString(), t.getEmissionPerKilometer()))
                .toArray(StatEntry[]::new);
    }
}