package com.example.emmisiontracker.controller;

import com.example.emmisiontracker.annotation.GraphQLController;
import com.example.emmisiontracker.constants.TravelMethod;
import com.example.emmisiontracker.domain.stats.StatEntry;
import com.example.emmisiontracker.domain.travel.Travel;
import com.example.emmisiontracker.domain.travel.TravelDto;
import com.example.emmisiontracker.domain.travel.TravelStop;
import com.example.emmisiontracker.domain.travel.WorldPoint;
import com.example.emmisiontracker.repository.TravelRepository;
import com.example.emmisiontracker.service.TravelService;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@GraphQLController
@AllArgsConstructor
public class TravelController {

    private final TravelService travelService;

    @GraphQLQuery(description = "Get travel history sorted by date and organised by pages")
    public List<Travel> travelHistory(@GraphQLArgument int page, @GraphQLArgument int count, @GraphQLArgument boolean descending) {
        return travelService.travelHistory(page, count, descending);
    }


    @GraphQLMutation
    public Travel addTravel(TravelDto travelDto) {
        return travelService.addTravel(travelDto);
    }

}
