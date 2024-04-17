package com.example.emmisiontracker.repository;

import com.example.emmisiontracker.constants.TravelMethod;
import com.example.emmisiontracker.domain.travel.Travel;
import com.example.emmisiontracker.domain.travel.TravelStop;
import com.example.emmisiontracker.domain.travel.WorldPoint;
import jakarta.annotation.PostConstruct;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

@Repository
public interface TravelRepository extends JpaRepository<Travel, Integer> {

    List<Travel> findByDateBetween(LocalDate start, LocalDate end);

}
