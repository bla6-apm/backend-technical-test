package com.tui.proof.ws.domain.repository;

import com.tui.proof.ws.domain.model.flight.Flight;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlightRepository extends CrudRepository<Flight, String> {

   /* @Query("SELECT new com.tui.proof.ws.domain.model.flight.Flight(o.id, o.data) FROM Flight o")
    List<Flight> getFlights();*/

}
