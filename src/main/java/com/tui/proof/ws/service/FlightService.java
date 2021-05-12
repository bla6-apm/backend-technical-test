package com.tui.proof.ws.service;

import com.tui.proof.ws.domain.model.Passenger;
import com.tui.proof.ws.domain.model.flight.Flight;

import java.util.List;

public interface FlightService {

    List<Flight> getFlights();

    Flight getFlightById(final String id);

    List<Passenger> getPassengers();

    Passenger getPassengerById(final String id);
}
