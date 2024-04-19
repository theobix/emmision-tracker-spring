package com.example.emmisiontracker.service;

import com.example.emmisiontracker.domain.travel.Travel;
import com.example.emmisiontracker.domain.travel.TravelDto;
import com.example.emmisiontracker.domain.user.User;
import com.example.emmisiontracker.repository.TravelRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class TravelService {

    private final UserService userService;
    private final TravelRepository travelRepository;


    @PreAuthorize("hasRole('ROLE_USER')")
    public Travel addTravel(TravelDto travelDto) {
        if (travelDto.getDate() == null) travelDto.setDate(LocalDate.now());

        Travel travel = new Travel(travelDto.getDate(), travelDto.getStart(), travelDto.getStops());
        travel.setOwner(userService.getUserFromContext());

        return travelRepository.save(travel);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    public List<Travel> travelHistory(int page, int count, boolean descending) {
        User user = userService.getUserFromContext();

        Sort sort = descending ? Sort.by("date").descending() : Sort.by("date");
        Pageable pageable = PageRequest.of(page, count, sort);

        return travelRepository.findByOwnerId(user.getId(), pageable).toList();
    }

}
