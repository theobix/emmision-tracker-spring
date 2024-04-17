package com.example.emmisiontracker.controller;

import com.example.emmisiontracker.annotation.GraphQLController;
import com.example.emmisiontracker.constants.TravelMethod;
import com.example.emmisiontracker.domain.stats.StatEntry;
import com.example.emmisiontracker.domain.travel.Travel;
import com.example.emmisiontracker.domain.travel.TravelStop;
import com.example.emmisiontracker.domain.travel.WorldPoint;
import com.example.emmisiontracker.repository.TravelRepository;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@GraphQLController
public class TravelController {

    private final TravelRepository travelRepository;
    public TravelController(TravelRepository travelRepository) {
        this.travelRepository = travelRepository;
    }

    @GraphQLQuery(description = "Get travel by it's unique ID")
    public Travel travelById(@GraphQLArgument Integer id) {
        return travelRepository.getReferenceById(id);
    }

    @GraphQLQuery(description = "Get travel history sorted by date and organised by pages")
    public List<Travel> travelHistory(@GraphQLArgument int page, @GraphQLArgument int count, @GraphQLArgument boolean descending) {
        Sort sort = descending ? Sort.by("date").descending() : Sort.by("date");
        Pageable pageable = PageRequest.of(page, count, sort);

        return travelRepository.findAll(pageable).toList();
    }


    @GraphQLMutation
    public Travel addTravel(@GraphQLArgument LocalDate date, @GraphQLArgument WorldPoint start, @GraphQLArgument List<TravelStop> stops) {
        if (date == null) date = LocalDate.now();

        Travel travel = new Travel(date, start, stops);
        return travelRepository.save(travel);
    }

}
