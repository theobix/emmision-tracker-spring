package com.example.emmisiontracker.domain.travel;

import com.example.emmisiontracker.constants.TravelMethod;
import com.example.emmisiontracker.util.CoordinatesUtil;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.leangen.graphql.annotations.GraphQLInputField;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.annotations.types.GraphQLType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@GraphQLType
@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class TravelStop {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(nullable=false)
    private Travel travel;

    private TravelMethod travelMethod;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private WorldPoint point;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private WorldPoint previousPoint;

    @GraphQLInputField(defaultValue = "0")
    private double distance = 0;
    @GraphQLInputField(defaultValue = "0")
    private double emission = 0;

    @JsonCreator
    public TravelStop(TravelMethod travelMethod, WorldPoint point) {
        this.travelMethod = travelMethod;
        this.point = point;
    }

    @GraphQLQuery(name = "previousPoint")
    public WorldPoint previousPoint() { return previousPoint; }

    @GraphQLQuery(name = "distance")
    public double distance() { return distance; }

    @GraphQLQuery(name = "emission")
    public double emission() { return emission; }



    public void setData(Travel travel, WorldPoint previousPoint) {
        this.travel = travel;
        this.previousPoint = previousPoint;

        distance = CoordinatesUtil.distance(previousPoint.getCoordinates(), point.getCoordinates());
        emission = distance * travelMethod.getEmissionPerKilometer();
    }

}
