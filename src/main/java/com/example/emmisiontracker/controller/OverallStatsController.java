package com.example.emmisiontracker.controller;

import com.example.emmisiontracker.annotation.GraphQLController;
import com.example.emmisiontracker.domain.stats.OverallStats;
import com.example.emmisiontracker.repository.TravelRepository;
import io.leangen.graphql.annotations.GraphQLQuery;

@GraphQLController
public class OverallStatsController {

    private final TravelRepository travelRepository;
    public OverallStatsController(TravelRepository travelRepository) {
        this.travelRepository = travelRepository;
    }

    @GraphQLQuery(description = "Get the overall stats")
    public OverallStats overallStats() {
        return new OverallStats(travelRepository);
    }

}
