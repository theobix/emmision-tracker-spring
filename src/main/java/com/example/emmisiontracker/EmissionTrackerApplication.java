package com.example.emmisiontracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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
