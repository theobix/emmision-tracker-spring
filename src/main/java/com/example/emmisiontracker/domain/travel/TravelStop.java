package com.example.emmisiontracker.domain.travel;

import com.example.emmisiontracker.constants.TravelMethod;
import com.example.emmisiontracker.util.CoordinatesUtil;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.annotations.types.GraphQLType;

@GraphQLType
public class TravelStop {


    final private TravelMethod travelMethod;
    final private WorldPoint point;
    private WorldPoint previousPoint;
    private double distance = 0;
    private double emission = 0;

    @JsonCreator
    public TravelStop(TravelMethod travelMethod, WorldPoint point) {
        this.travelMethod = travelMethod;
        this.point = point;
    }

    public TravelMethod getTravelMethod() { return travelMethod; }
    public WorldPoint getPoint() { return point; }

    @GraphQLQuery(name = "previousPoint")
    public WorldPoint previousPoint() { return previousPoint; }

    @GraphQLQuery(name = "distance")
    public double distance() { return distance; }

    @GraphQLQuery(name = "emission")
    public double emission() { return emission; }



    public void setData(WorldPoint previousPoint) {
        this.previousPoint = previousPoint;
        distance = CoordinatesUtil.distance(previousPoint.coordinates(), point.coordinates());
        emission = distance * TravelMethod.getUnitEmissions(travelMethod);
    }

}
