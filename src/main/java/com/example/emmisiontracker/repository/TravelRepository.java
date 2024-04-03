package com.example.emmisiontracker.repository;

import com.example.emmisiontracker.constants.TravelMethod;
import com.example.emmisiontracker.domain.travel.Travel;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
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

    public Travel CreateTravel(TravelMethod method, LocalDate date, float distance) {
        float emission = TravelMethod.getUnitEmissions(method) * distance;
        Travel travel = new Travel(
                String.valueOf(travels.size()),
                method,
                date,
                distance,
                emission);

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

    public Travel[] getTravelsBetween(LocalDate after, LocalDate before) {
        return travels.stream().filter(travel ->
                    travel.date().equals(after) ||
                            (travel.date().isAfter(after) && travel.date().isBefore(before))
        ).toArray(Travel[]::new);
    }

}
