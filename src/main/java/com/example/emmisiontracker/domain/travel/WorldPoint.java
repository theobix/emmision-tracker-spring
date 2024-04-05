package com.example.emmisiontracker.domain.travel;

import io.leangen.graphql.annotations.types.GraphQLType;

@GraphQLType
public record WorldPoint(String label, float[] coordinates, String category) {
}
