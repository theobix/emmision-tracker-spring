package com.example.emmisiontracker.domain.travel;

import com.example.emmisiontracker.constants.TravelMethod;
import com.example.emmisiontracker.domain.user.User;
import io.leangen.graphql.annotations.GraphQLQuery;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.ToDoubleFunction;
import java.util.stream.Collectors;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
@Builder
public class Travel implements Comparable<Travel> {

    @Id @GeneratedValue
    private Integer id;

    @ManyToOne
    @JoinColumn
    private User ownedBy;

    @Column(nullable = false, updatable = false)
    private LocalDate date;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private WorldPoint start;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn
    private List<TravelStop> stops;

    private double distance;
    private double emission;


    public Travel(LocalDate date, WorldPoint start, List<TravelStop> stops) {
        this.date = date;
        this.start = start;
        this.stops = stops;

        WorldPoint previousPoint = start;
        for (TravelStop stop : stops) {
            stop.setData(this, previousPoint);
            previousPoint = stop.getPoint();
        }

        this.emission = reduceStops(TravelStop::emission);
        this.distance = reduceStops(TravelStop::distance);
    }
    public double reduceStops(ToDoubleFunction<TravelStop> reduce) {
        return getStops().stream().mapToDouble(reduce).sum();
    }


    public Collection<TravelMethod> travelMethods() {
        return stops.stream().map(TravelStop::getTravelMethod)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public Collection<TravelStop> getStopsWithMethod(TravelMethod method) {
        return stops.stream().filter(s -> s.getTravelMethod().equals(method))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @GraphQLQuery(name = "stops", description = "Get all travel stops of this travel")
    public List<TravelStop> getAllStops() { return stops; }

    @GraphQLQuery(name = "start", description = "Get the first point of this travel")
    public WorldPoint getStartPoint() { return start; }

    @GraphQLQuery(name = "end", description = "Get the first point of this travel")
    public WorldPoint getLastPoint() { return stops.get(stops.size() - 1).getPoint(); }


    @Override
    public int compareTo(Travel other) {
        if (this.date.equals(other.date)) return 0;
        else if (this.date.isAfter(other.date)) return -1;
        else return 1;
    }
}
