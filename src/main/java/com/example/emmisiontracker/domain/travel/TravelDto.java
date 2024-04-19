package com.example.emmisiontracker.domain.travel;

import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.types.GraphQLType;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@GraphQLType
@Data
public class TravelDto {

    private LocalDate date;
    private WorldPoint start;
    private List<TravelStop> stops;

}
