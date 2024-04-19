package com.example.emmisiontracker.service;

import com.example.emmisiontracker.domain.stats.StatsGroup;
import com.example.emmisiontracker.domain.travel.Travel;
import com.example.emmisiontracker.repository.TravelRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Service
@AllArgsConstructor
public class StatsService {

    private final TravelRepository travelRepository;

    public StatsGroup getStatGroup(LocalDate startDate, Function<LocalDate, LocalDate> step, int stepCount) {
        ArrayList<List<Travel>> travelsGroups = new ArrayList<>();
        LocalDate[] dates = new LocalDate[stepCount];

        for (int i = 0; i < stepCount; i++) {
            travelsGroups.add(travelRepository.findByDateBetween(startDate, step.apply(startDate).minusDays(1)));

            dates[i] = startDate;
            startDate = step.apply(startDate);
        }

        return new StatsGroup(dates, travelsGroups);
    }
}
