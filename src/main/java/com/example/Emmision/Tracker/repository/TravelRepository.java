package com.example.Emmision.Tracker.repository;

import com.example.Emmision.Tracker.constants.TravelMethod;
import com.example.Emmision.Tracker.domain.Travel;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

@Repository
public class TravelRepository {

    private ArrayList<Travel> travels;
    @PostConstruct
    public void initializeTravels() {
        travels = new ArrayList<>();
    }

    public Travel CreateTravel(TravelMethod method, OffsetDateTime datetime, float distance) {
        float unitEmission = TravelMethod.getUnitEmissions(method);
        Travel travel = new Travel(
                String.valueOf(travels.size()),
                method,
                datetime,
                distance,
                unitEmission,
                distance*unitEmission);

        travels.add(travel);
        return travel;
    }


    public Travel[] getAll() { return travels.toArray(Travel[]::new); }
    public int getCount() { return travels.size(); }
    public Stream<Travel> getStream() { return  travels.stream(); }

    public Travel getById(String id) {
        return travels.stream().filter(travel -> travel.id().equals(id)).findFirst().orElse(null);
    }

    public Travel[] getHistory(int page, int count, boolean descending) {
        int sliceFrom = Math.max(0,page-1) * count;
        if (sliceFrom >= travels.size()) return new Travel[0];

        int sliceTo = Math.min(sliceFrom + count, travels.size());

        List<Travel> sortedTravels = new ArrayList<>(travels.stream().sorted().toList());
        if (!descending)
            Collections.reverse(sortedTravels);

        return sortedTravels.subList(sliceFrom, sliceTo).toArray(Travel[]::new);
    }

    public Travel[] getTravelsBetween(OffsetDateTime after, OffsetDateTime before) {
        return travels.stream().filter(travel ->
                    travel.datetime().isAfter(after) && travel.datetime().isBefore(before)
        ).toArray(Travel[]::new);
    }

}
