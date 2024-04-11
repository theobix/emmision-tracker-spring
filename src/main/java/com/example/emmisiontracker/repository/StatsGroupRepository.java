package com.example.emmisiontracker.repository;

import com.example.emmisiontracker.domain.stats.StatsGroup;
import com.example.emmisiontracker.domain.travel.Travel;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.function.Function;

@Repository
public class StatsGroupRepository {

    private final TravelRepository travelRepository;
    public StatsGroupRepository(TravelRepository travelRepository) {
        this.travelRepository = travelRepository;
    }

    public StatsGroup getStatGroup(LocalDate startDate, Function<LocalDate, LocalDate> step, int stepCount) {
        ArrayList<Travel[]> travelsGroups = new ArrayList<>();
        LocalDate[] dates = new LocalDate[stepCount];

        for (int i = 0; i < stepCount; i++) {
            travelsGroups.add(travelRepository.getTravelsBetween(startDate, step.apply(startDate)));

            dates[i] = startDate;
            startDate = step.apply(startDate);
        }

        return new StatsGroup(dates, travelsGroups);
    }

}
