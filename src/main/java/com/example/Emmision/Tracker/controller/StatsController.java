package com.example.Emmision.Tracker.controller;

import com.example.Emmision.Tracker.constants.TimeUnit;
import com.example.Emmision.Tracker.constants.TravelMethod;
import com.example.Emmision.Tracker.domain.OverallStats;
import com.example.Emmision.Tracker.domain.StatEntry;
import com.example.Emmision.Tracker.domain.StatsGroup;
import com.example.Emmision.Tracker.domain.Travel;
import com.example.Emmision.Tracker.repository.TravelRepository;
import com.example.Emmision.Tracker.util.DateUtil;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLContext;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Stream;

@Component
@GraphQLApi
public class StatsController {

    private final TravelRepository travelRepository;
    public StatsController(TravelRepository travelRepository) {
        this.travelRepository = travelRepository;
    }


    @GraphQLQuery(description = "Get travel stats in given time period")
    public StatsGroup stats(@GraphQLArgument TimeUnit unit, @GraphQLArgument int delta) {
        LocalDate truncatedDate = DateUtil.truncateDate(LocalDate.now(), unit);
        LocalDate startDate = DateUtil.deltaDate(truncatedDate, unit, delta);

        return getStatGroup(startDate, TimeUnit.getUnitStep(unit), TimeUnit.getUnitCount(unit));
    }

    @GraphQLQuery(description = "Get the overall stats")
    public OverallStats overallStats() {
        return new OverallStats(travelRepository);
    }


    private StatsGroup getStatGroup(LocalDate startDate, Function<LocalDate, LocalDate> step, int stepCount) {
        ArrayList<Travel[]> travelsGroups = new ArrayList<>();
        LocalDate[] dates = new LocalDate[stepCount];

        for (int i = 0; i < stepCount; i++) {
            travelsGroups.add(travelRepository.getTravelsBetween(startDate, step.apply(startDate)));

            dates[i] = startDate;
            startDate = step.apply(startDate);
        }

        return new StatsGroup(dates, travelsGroups);
    }


}

