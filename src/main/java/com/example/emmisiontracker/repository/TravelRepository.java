package com.example.emmisiontracker.repository;

import com.example.emmisiontracker.domain.travel.Travel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TravelRepository extends JpaRepository<Travel, Long> {

    @Query(value = "select t from Travel t where t.owner.id = ?1 group by t.id having t.date between ?2 and ?3")
    List<Travel> findByDateBetween(Integer userId, LocalDate start, LocalDate end);

    List<Travel> findByOwnerIdAndDateBetween(Long userId, LocalDate start, LocalDate end);

    Page<Travel> findByOwnerId(Long userId, Pageable pageable);
    List<Travel> findByOwnerId(Long userId);



}
