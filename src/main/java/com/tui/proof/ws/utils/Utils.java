package com.tui.proof.ws.utils;

import com.tui.proof.ws.domain.model.Passenger;
import com.tui.proof.ws.domain.model.booking.Booking;
import com.tui.proof.ws.domain.model.booking.BookingInfo;
import com.tui.proof.ws.domain.model.booking.BookingObject;
import com.tui.proof.ws.domain.model.booking.BookingStatus;
import com.tui.proof.ws.domain.model.flight.Flight;
import com.tui.proof.ws.domain.model.flight.FlightInfo;
import lombok.extern.log4j.Log4j2;

import java.util.stream.Collectors;

@Log4j2
public class Utils {

    public static Booking toNewBooking(com.tui.proof.ws.model.Booking input) {
        log.info("toNewBooking().start - input={}", input);
        Booking output = new Booking();
        output.setStatus(BookingStatus.DRAFT.getStatus());

        BookingInfo info = new BookingInfo();
        info.setFlight(toFlight(input.getFlight()));
        info.setPassengers(input.getPassenger().stream().map(Utils::toPassenger).collect(Collectors.toList()));

        output.setData(info);

        log.info("toNewBooking().end - output={}", output);
        return output;
    }

    private static Passenger toPassenger(com.tui.proof.ws.model.Passenger input) {
        log.info("toPassenger().start - input={}", input);
        Passenger output = new Passenger();

        output.setId(input.getId());
        output.setAddress(input.getAddress());
        output.setCountry(input.getCountry());
        output.setEmail(input.getEmail());
        output.setFirstName(input.getFirstName());
        output.setLastName(input.getLastName());
        output.setPostalCode(input.getPostalCode());
        output.setTelephone(input.getTelephone());

        log.info("toPassenger().end - output={}", output);
        return output;
    }

    private static Flight toFlight(com.tui.proof.ws.model.Flight input) {
        log.info("toFlight().start - input={}", input);
        Flight output = new Flight();

        output.setId(input.getId());

        FlightInfo info = new FlightInfo();
        info.setAvailability(input.getData().getAvailability());
        info.setCompany(input.getData().getCompany());
        info.setDuration(input.getData().getDuration());
        info.setPaxes(input.getData().getPaxes());
        info.setPoints(input.getData().getPoints());
        info.setSegment(input.getData().getSegment());

        output.setData(info);

        log.info("toFlight().end - output={}", output);
        return output;
    }

    public static Booking toRemoveBookingInfo(final Booking input, final  BookingObject toUpdate, final String id) {
        log.info("toRemoveBookingInfo().start - [input={}, toUpdate={}, id={}]", input, toUpdate, id);
        Booking output = new Booking();
        output.setStatus(BookingStatus.DRAFT.getStatus());

        BookingInfo info = new BookingInfo();
        if (toUpdate.equals(BookingObject.FLIGHT))
            if (id.equals(input.getData().getFlight().getId()))
                info.setFlight(null);
        else
            info.setFlight(input.getData().getFlight());

        if (toUpdate.equals(BookingObject.PASSENGER))
            input.getData().getPassengers().removeIf( item -> item.getId().equals(id));
        else
            info.setPassengers(input.getData().getPassengers());

        output.setData(info);
        log.info("toRemoveBookingInfo().end - output={}", output);
        return output;
    }

    public static Booking toUpdateBooking(Booking existingBooking, final Booking newBookingInfo) {
        log.info("toUpdateBooking().start - Copy booking from {} to {}", newBookingInfo, existingBooking);

        BookingInfo bookingInfo = existingBooking.getData();
        bookingInfo.setPassengers(newBookingInfo.getData().getPassengers());
        bookingInfo.setFlight(newBookingInfo.getData().getFlight());

        existingBooking.setData(bookingInfo);

        log.info("toUpdateBooking().end - Booking to return {}", existingBooking);
        return existingBooking;
    }
}
