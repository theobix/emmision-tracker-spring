package com.example.emmisiontracker.service;

import com.example.emmisiontracker.constants.TimeUnit;
import com.example.emmisiontracker.domain.stats.OverallStats;
import com.example.emmisiontracker.domain.stats.StatsGroup;
import com.example.emmisiontracker.domain.travel.Travel;
import com.example.emmisiontracker.domain.user.User;
import com.example.emmisiontracker.repository.TravelRepository;
import com.example.emmisiontracker.util.DateUtil;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
public class StatsService {

    private final TravelRepository travelRepository;
    private final UserService userService;

    @PreAuthorize("hasRole('ROLE_USER')")
    public StatsGroup getStatGroup(TimeUnit unit, int delta) {
        User user = userService.getUserFromContext();

        LocalDate truncatedDate = DateUtil.truncateDate(LocalDate.now(), unit);
        LocalDate startDate = DateUtil.deltaDate(truncatedDate, unit, delta);
        LocalDate endDate = DateUtil.addDate(startDate, unit, 1);

        var step = TimeUnit.getUnitStep(unit);
        var stepCount = TimeUnit.getUnitCount(unit);

        ArrayList<List<Travel>> travelsGroups = new ArrayList<>();
        LocalDate[] dates = new LocalDate[stepCount];

        List<Travel> travelStream = travelRepository.findByOwnerIdAndDateBetween(user.getId(), startDate, endDate);

        for (int i = 0; i < stepCount; i++) {
            LocalDate finalStartDate = startDate;
            List<Travel> travels = travelStream.stream()
                    .filter(t -> DateUtil.isDateBetween(t.getDate(), finalStartDate, step.apply(finalStartDate)))
                    .toList();

            travelsGroups.add(travels);

            dates[i] = startDate;
            startDate = step.apply(startDate);
        }

        return new StatsGroup(dates, travelsGroups);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    public OverallStats getOverallStats() {
        User user = userService.getUserFromContext();
        return new OverallStats(travelRepository.findByOwnerId(user.getId()));
    }
}
