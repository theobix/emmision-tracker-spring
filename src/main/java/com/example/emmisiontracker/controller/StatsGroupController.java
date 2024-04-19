package com.example.emmisiontracker.controller;

import com.example.emmisiontracker.annotation.GraphQLController;
import com.example.emmisiontracker.constants.TimeUnit;
import com.example.emmisiontracker.domain.stats.StatsGroup;
import com.example.emmisiontracker.service.StatsService;
import com.example.emmisiontracker.util.DateUtil;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLQuery;

import java.time.LocalDate;

@GraphQLController
public class StatsGroupController {

    private final StatsService statsService;
    public StatsGroupController(StatsService statsService) {
        this.statsService = statsService;
    }

    @GraphQLQuery(description = "Get travel stats in given time period")
    public StatsGroup stats(@GraphQLArgument TimeUnit unit, @GraphQLArgument int delta) {
        return statsService.getStatGroup(unit, delta);
    }


}

