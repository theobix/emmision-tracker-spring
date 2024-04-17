package com.example.emmisiontracker.domain.travel;

import io.leangen.graphql.annotations.types.GraphQLType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@GraphQLType
@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class WorldPoint {

    @Id
    @GeneratedValue
    private Integer id;

    private String label;
    private float[] coordinates;
    private String category;

}
