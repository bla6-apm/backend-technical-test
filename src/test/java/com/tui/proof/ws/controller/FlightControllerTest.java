package com.tui.proof.ws.controller;

import com.tui.proof.ws.domain.model.Passenger;
import com.tui.proof.ws.domain.model.flight.Flight;
import com.tui.proof.ws.service.FlightService;
import lombok.extern.log4j.Log4j2;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@Log4j2
@RunWith(SpringRunner.class)
@SpringBootTest
public class FlightControllerTest {

    @Autowired
    private FlightService flightService;

    @Test
    public void getFlightsTest() {
        List<Flight> flightList = flightService.getFlights();
        log.info("getFlightsTest - {}", flightList);

        assertThat(flightList.size(), is(3));
    }

    @Test
    public void getFlightByIdTest() {
        List<Flight> flightList = flightService.getFlights();
        log.info("getFlightByIdTest - {}", flightList);

        Flight flightOne = flightList.get(0);
        Flight flightTwo = flightService.getFlightById(flightOne.getId());

        assertThat(flightOne.getId(), is(flightTwo.getId()));
    }


    @Test
    public void getPassengersTest() {
        List<Passenger> passengerList = flightService.getPassengers();
        log.info("getPassengersTest - {}", passengerList);

        assertThat(passengerList.size(), is(3));
    }

    @Test
    public void getPassengerByIdTest() {
        List<Passenger> passengerList = flightService.getPassengers();
        log.info("getPassengerByIdTest - {}", passengerList);

        Passenger passengerOne = passengerList.get(0);
        Passenger passengerTwo = flightService.getPassengerById(passengerOne.getId());

        assertThat(passengerOne.getId(), is(passengerTwo.getId()));
    }
}
