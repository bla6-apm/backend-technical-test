package com.tui.proof.ws.controller;

import com.tui.proof.ws.domain.model.Passenger;
import com.tui.proof.ws.domain.model.booking.Booking;
import com.tui.proof.ws.domain.model.booking.BookingInfo;
import com.tui.proof.ws.domain.model.booking.BookingStatus;
import com.tui.proof.ws.domain.model.flight.Flight;
import com.tui.proof.ws.domain.model.flight.FlightInfo;
import com.tui.proof.ws.domain.model.flight.Pax;
import com.tui.proof.ws.domain.model.flight.Segment;
import com.tui.proof.ws.service.BookingService;
import com.tui.proof.ws.service.FlightService;
import com.tui.proof.ws.utils.Utils;
import lombok.extern.log4j.Log4j2;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@Log4j2
@RunWith(SpringRunner.class)
@SpringBootTest
public class BookingControllerTest {

    @Autowired
    private FlightService flightService;

    @Autowired
    private BookingService bookingService;

    @Test
    public void createNewBookingDraftTest() throws SQLException {
        List<Flight> flightList = flightService.getFlights();
        List<Passenger> passengerList = flightService.getPassengers();
        Flight flight = flightList.get(0);
        List<Passenger> holderList = new ArrayList<>();
        holderList.add(passengerList.get(0));

        BookingInfo bookingInfo = new BookingInfo();
        bookingInfo.setFlight(flight);
        bookingInfo.setPassengers(holderList);

        Booking booking = new Booking();
        booking.setData(bookingInfo);
        booking.setStatus(BookingStatus.DRAFT.getStatus());

        bookingService.saveBooking(booking);
    }

    @Test
    public void confirmBookingTest() throws SQLException {
        List<Booking> bookingList = bookingService.getDraftBookings();
        Optional<Booking> optionalBooking = bookingList.stream().filter(
                item -> item.getStatus() == BookingStatus.DRAFT.getStatus()
        ).findAny();

        if (optionalBooking.isPresent()) {
            bookingService.confirmBooking(optionalBooking.get());
        }

    }

    @Test
    public void getBookingsTest() {
        List<Booking> bookingList = bookingService.getBookings();
        log.info("getBookingsTest - {}", bookingList);
    }

    @Test
    public void getBookingsDraftTest() {
        List<Booking> bookingList = bookingService.getDraftBookings();
        log.info("getBookingsTest - {}", bookingList);
    }

    @Test
    public void getBookingByIdTest() {
        List<Booking> bookingList = bookingService.getBookings();
        log.info("getBookingByIdTest - {}", bookingList);

        Booking bookingOne = bookingList.get(0);
        Booking bookingTwo = bookingService.getBookingById(bookingOne.getId());

        assertThat(bookingOne.getId(), is(bookingTwo.getId()));
    }

    @Test
    public void createModulatedBookingTest() throws SQLException {
        final Booking emptyBooking = bookingService.saveEmptyBooking(new Booking());

        List<Pax> paxList = new ArrayList<>();
        final Pax pax = Pax.builder()
                .children(111)
                .adult(290)
                .infant(96)
                .build();
        paxList.add(pax);

        List<Segment> segments = new ArrayList<>();
        final Segment segment = Segment.builder()
                .connectionDuration(125)
                .departureTime("2021-11-16T20:25-03:00")
                .arrivalTime("2021-11-17T06:00-04:00")
                .destination("YYZ")
                .duration(635)
                .origin("GRU")
                .build();
        segments.add(segment);

        final FlightInfo flightInfo = FlightInfo.builder()
                .availability(230)
                .company("Dalta")
                .duration(862)
                .paxes(paxList)
                .points(20200)
                .segment(segments)
                .id(UUID.randomUUID().toString())
                .build();

        final Flight flight = Flight.builder().data(flightInfo).id(emptyBooking.getData().getFlight().getId()).build();


        List<Passenger> holderList = new ArrayList<>();
        Passenger passenger = Passenger.builder()
                .address("test_address_modulated")
                .country("test_country")
                .email("test_email@example.com")
                .firstName("test_fn_modulated")
                .lastName("test_ln_modulated")
                .postalCode("777777777")
                .telephone("00000")
                .id(UUID.randomUUID().toString())
                .build();
        holderList.add(passenger);

        BookingInfo bookingInfo = new BookingInfo();
        bookingInfo.setFlight(flight);
        bookingInfo.setPassengers(holderList);

        Booking booking = new Booking();
        booking.setData(bookingInfo);
        booking.setStatus(BookingStatus.DRAFT.getStatus());

        Booking toUpdate = Utils.toUpdateBooking(emptyBooking, booking);

        bookingService.saveBooking(toUpdate);
    }

}
