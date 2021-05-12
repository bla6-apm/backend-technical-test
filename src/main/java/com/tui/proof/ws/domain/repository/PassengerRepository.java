package com.tui.proof.ws.domain.repository;

import com.tui.proof.ws.domain.model.Passenger;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PassengerRepository extends CrudRepository<Passenger, String> {
}
