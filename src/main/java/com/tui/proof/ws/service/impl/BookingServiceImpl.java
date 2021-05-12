package com.tui.proof.ws.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tui.proof.ws.domain.model.booking.Booking;
import com.tui.proof.ws.domain.model.booking.BookingStatus;
import com.tui.proof.ws.domain.model.flight.Flight;
import com.tui.proof.ws.domain.repository.BookingRepository;
import com.tui.proof.ws.domain.repository.FlightRepository;
import com.tui.proof.ws.service.BookingService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Log4j2
@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private BookingRepository bookingRepository;

    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public List<Booking> getBookings() {
        List<Booking> dataList = (List<Booking>) bookingRepository.findAll();
        log.info(dataList);

        return dataList;
    }

    @Override
    public void saveBooking(Booking booking) throws SQLException {
        try {
            log.info("Saving booking: booking={}", booking);
            bookingRepository.save(booking);
        } catch (Exception e) {
            log.error("saveNewBooking() - Something went wrong saving a new Booking - error={}, booking={}", e.getMessage(), booking);
            throw new java.sql.SQLException("saveNewBooking() - Something went wrong saving a new Booking: error={}", e.getMessage());
        }
    }

    @Override
    public Booking saveEmptyBooking(Booking booking) throws SQLException {
        try {
            log.info("Saving booking: booking={}", booking);
            return bookingRepository.save(booking);
        } catch (Exception e) {
            log.error("saveNewBooking() - Something went wrong saving an empty Booking - error={}, booking={}", e.getMessage(), booking);
            throw new java.sql.SQLException("saveNewBooking() - Something went wrong saving an empty Booking: error={}", e.getMessage());
        }
    }

    @Override
    public void confirmBooking(Booking booking) throws SQLException {
        try {
            booking.setStatus(BookingStatus.CONFIRMED.getStatus());

            Optional<Flight> optional = flightRepository.findById(booking.getData().getFlight().getId());
            if (!optional.isPresent())
                return;

            // Update Availability in Flight object
            Flight flight = optional.get();
            long newAvailability = flight.getData().getAvailability() - booking.getData().getPassengers().size();
            flight.getData().setAvailability(newAvailability);
            flightRepository.save(flight);

            bookingRepository.save(booking);
        } catch (Exception e) {
            log.error("updateBooking() - Something went wrong updating an existing Booking - error={}, booking={}", e.getMessage(), booking);
            throw new java.sql.SQLException("updateBooking() - Something went wrong updating an existing Booking: error={}", e.getMessage());
        }
    }

    @Override
    public List<Booking> getDraftBookings() {
        List<Booking> dataList = bookingRepository.getDraftedBookings(BookingStatus.DRAFT.getStatus());
        log.info(dataList);

        return dataList;
    }

    @Override
    public Booking getBookingById(String id) {
        List<Booking> dataList = bookingRepository.getBookingById(id);
        log.info(dataList);

        return !dataList.isEmpty() ? dataList.get(0) : new Booking();
    }

    @Override
    public void removeBooking(final Booking booking) {
        log.info("removeBooking() - booking={}", booking);
        bookingRepository.delete(booking);
    }
}
