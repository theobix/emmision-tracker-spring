package com.example.emmisiontracker.controller;

import com.example.emmisiontracker.annotation.GraphQLController;
import com.example.emmisiontracker.domain.stats.OverallStats;
import com.example.emmisiontracker.repository.TravelRepository;
import com.example.emmisiontracker.service.StatsService;
import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.AllArgsConstructor;

@GraphQLController
@AllArgsConstructor
public class OverallStatsController {


    private final StatsService statsService;

    @GraphQLQuery(description = "Get the overall stats")
    public OverallStats overallStats() {
        return statsService.getOverallStats();
    }

}
