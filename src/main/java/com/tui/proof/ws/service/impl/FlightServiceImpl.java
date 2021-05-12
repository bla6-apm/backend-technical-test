package com.tui.proof.ws.service.impl;

import com.tui.proof.ws.domain.model.Passenger;
import com.tui.proof.ws.domain.model.flight.Flight;
import com.tui.proof.ws.domain.repository.BookingRepository;
import com.tui.proof.ws.domain.repository.FlightRepository;
import com.tui.proof.ws.domain.repository.PassengerRepository;
import com.tui.proof.ws.service.FlightService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Log4j2
@Service
public class FlightServiceImpl implements FlightService {

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private PassengerRepository passengerRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Override
    public List<Flight> getFlights() {
        try {
            Iterable<Flight> dataList = flightRepository.findAll();
            log.info(dataList);
            return (List<Flight>) dataList;
        } catch (Exception e) {
            log.error("getFlights() - Didn't find any record - {}", e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public Flight getFlightById(final String id) {
        Optional<Flight> data = flightRepository.findById(id);
        log.info(data);

        return data.orElse(null);
    }

    @Override
    public List<Passenger> getPassengers() {
        try {
            Iterable<Passenger> dataList = passengerRepository.findAll();
            log.info(dataList);
            return (List<Passenger>) dataList;
        } catch (Exception e) {
            log.error("getPassengers() - Didn't find any record - {}", e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public Passenger getPassengerById(final String id) {
        Optional<Passenger> data = passengerRepository.findById(id);
        log.info(data);

        return data.orElse(null);
    }
}
