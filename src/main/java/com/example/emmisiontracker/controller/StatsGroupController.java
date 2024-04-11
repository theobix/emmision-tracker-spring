package com.example.emmisiontracker.controller;

import com.example.emmisiontracker.annotation.GraphQLController;
import com.example.emmisiontracker.constants.TimeUnit;
import com.example.emmisiontracker.domain.stats.OverallStats;
import com.example.emmisiontracker.domain.stats.StatsGroup;
import com.example.emmisiontracker.domain.travel.Travel;
import com.example.emmisiontracker.repository.StatsGroupRepository;
import com.example.emmisiontracker.repository.TravelRepository;
import com.example.emmisiontracker.util.DateUtil;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLQuery;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;

@GraphQLController
public class StatsGroupController {

    private final StatsGroupRepository statsGroupRepository;
    public StatsGroupController(StatsGroupRepository statsGroupRepository) {
        this.statsGroupRepository = statsGroupRepository;
    }

    @GraphQLQuery(description = "Get travel stats in given time period")
    public StatsGroup stats(@GraphQLArgument TimeUnit unit, @GraphQLArgument int delta) {
        LocalDate truncatedDate = DateUtil.truncateDate(LocalDate.now(), unit);
        LocalDate startDate = DateUtil.deltaDate(truncatedDate, unit, delta);

        return statsGroupRepository.getStatGroup(
                startDate,
                TimeUnit.getUnitStep(unit),
                TimeUnit.getUnitCount(unit));
    }


}

