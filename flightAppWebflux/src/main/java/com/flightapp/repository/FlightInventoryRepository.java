/*
 * package com.flightapp.repository;
 * 
 * import com.flightapp.entity.FlightInventory; import
 * org.springframework.data.jpa.repository.JpaRepository;
 * 
 * 
 * import java.time.LocalDateTime; import java.util.List;
 * 
 * public interface FlightInventoryRepository extends
 * JpaRepository<FlightInventory, Long> {
 * 
 * List<FlightInventory> findByFromPlaceAndToPlaceAndDepartureDateTimeBetween(
 * String fromPlace, String toPlace, LocalDateTime start, LocalDateTime end); }
 */

package com.flightapp.repository;

import com.flightapp.entity.FlightInventory;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import java.time.LocalDateTime;

public interface FlightInventoryRepository 
        extends ReactiveMongoRepository<FlightInventory, String> {

	@Query("{'fromPlace': ?0, 'toPlace': ?1, 'departureDateTime': { $gte: ?2, $lte: ?3 } }")
	Flux<FlightInventory> findFlightsForDay(
	        String fromPlace,
	        String toPlace,
	        LocalDateTime startOfDay,
	        LocalDateTime endOfDay
	);

    Flux<FlightInventory> findByAirline(String airline);

    
}

