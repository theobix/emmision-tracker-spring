package com.example.emmisiontracker.repository;

import com.example.emmisiontracker.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    boolean existsByEmail(String email);
    boolean existsByName(String username);

    Optional<User> findByEmail(String email);
    Optional<User> findByName(String username);

}
