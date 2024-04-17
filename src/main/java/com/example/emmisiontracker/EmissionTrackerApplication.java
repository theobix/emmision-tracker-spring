package com.example.emmisiontracker;

import com.example.emmisiontracker.domain.travel.Travel;
import com.example.emmisiontracker.domain.travel.WorldPoint;
import com.example.emmisiontracker.repository.TravelRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import java.time.LocalDate;

@SpringBootApplication
public class EmissionTrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmissionTrackerApplication.class, args);
	}

	/*@Bean
	public CommandLineRunner commandLineRunner(TravelRepository travelRepository) {

		return args -> {
			Travel travel = Travel.builder()
					.emission(200)
					.date(LocalDate.now())
					.start(new WorldPoint())
					.build();

			travelRepository.save(travel);
		};

	}*/
}
