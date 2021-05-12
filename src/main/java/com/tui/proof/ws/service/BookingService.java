package com.tui.proof.ws.service;

import com.tui.proof.ws.domain.model.booking.Booking;

import java.sql.SQLException;
import java.util.List;

public interface BookingService {

    List<Booking> getBookings();

    void saveBooking(final Booking booking) throws SQLException;

    Booking saveEmptyBooking(final Booking booking) throws SQLException;

    void confirmBooking(final Booking booking) throws SQLException;

    List<Booking> getDraftBookings();

    Booking getBookingById(final String id);

    void removeBooking(final Booking booking);
}
