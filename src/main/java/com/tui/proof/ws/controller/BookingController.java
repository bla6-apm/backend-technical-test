package com.tui.proof.ws.controller;

import com.tui.proof.ws.domain.model.booking.Booking;
import com.tui.proof.ws.domain.model.booking.BookingObject;
import com.tui.proof.ws.service.BookingService;
import com.tui.proof.ws.utils.Utils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.sql.SQLException;
import java.util.List;

@Log4j2
@RestController
@RequestMapping("/public")
public class BookingController {

  @Autowired
  private BookingService bookingService;

  @GetMapping(value = "/v1/bookings", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> getBookings() {
    log.info("START - getBookings()");

    final List<Booking> dataList = bookingService.getBookings();

    if (dataList.isEmpty()) {
      log.warn("END - getBookings() - {}", HttpStatus.NO_CONTENT);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    log.info("END - getBookings() - {}", dataList);
    return new ResponseEntity<>(dataList, HttpStatus.OK);
  }

  @GetMapping(value = "/v1/bookings/draft", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> getDraftBookings() {
    log.info("START - getDraftBookings()");

    final List<Booking> dataList = bookingService.getDraftBookings();

    if (dataList.isEmpty()) {
      log.warn("END - getDraftBookings() - {}", HttpStatus.NO_CONTENT);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    log.info("END - getDraftBookings() - {}", dataList);
    return new ResponseEntity<>(dataList, HttpStatus.OK);
  }

  @GetMapping(value = "/v1/bookings/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> getBooking(@PathVariable("id") final String id) {
    log.info("START - getBooking()");

    final Booking booking = bookingService.getBookingById(id);

    if (booking == null) {
      log.warn("END - getBooking() - {}", HttpStatus.NO_CONTENT);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    log.info("END - getBooking() - {}", booking);
    return new ResponseEntity<>(booking, HttpStatus.OK);
  }

  @PostMapping(value = "/v1/bookings/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> createModulatedBooking(@PathVariable("id") final String id,
                                                  @Valid @RequestBody final com.tui.proof.ws.model.Booking newBooking) {
    log.info("START - createModulatedBooking(): [id={}, newBooking={}]", id, newBooking);

    final Booking existingBooking = bookingService.getBookingById(id);
    if (existingBooking == null) {
      log.warn("END - createModulatedBooking() - booking not exists, id={}", id);
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    Booking inputBooking = Utils.toNewBooking(newBooking);
    inputBooking.setId(existingBooking.getId());

    existingBooking.getData().setId(existingBooking.getData().getId());
    existingBooking.getData().getFlight().setId(existingBooking.getData().getFlight().getId());
    try {
      bookingService.saveBooking(inputBooking);
    } catch (SQLException e) {
      log.warn("END - createModulatedBooking() - error={}", e.getMessage());
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    log.info("END - createModulatedBooking() - {}", newBooking);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @PostMapping(value = "/v1/bookings", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> createModulatedBooking(@Valid @RequestBody final com.tui.proof.ws.model.Booking newBooking) {
    log.info("START - createModulatedBooking(): newBooking={}", newBooking);

    final Booking booking = Utils.toNewBooking(newBooking);
    try {
      bookingService.saveBooking(booking);
      log.info("END - createModulatedBooking()");
    } catch (SQLException e) {
      log.warn("END - createModulatedBooking() - error={}", e.getMessage());
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    log.info("END - createModulatedBooking() - {}", newBooking);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @PostMapping(value = "/v1/bookings/empty", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> createEmptyBooking() {

    try {
      final Booking newBooking = bookingService.saveEmptyBooking(new Booking());
      log.info("END - createEmptyBooking() - {}", newBooking);
      return new ResponseEntity<>(newBooking, HttpStatus.OK);
    } catch (SQLException e) {
      log.warn("END - createEmptyBooking() - error={}", e.getMessage());
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

  }

  @DeleteMapping(value = "/v1/bookings/{id}/flight/{flightId}")
  public ResponseEntity<?> removeFlightFromBooking(@PathVariable("id") final String id,
                                                   @PathVariable("flightId") final String flightId) {
    log.info("START - removePassengerFromBooking(): id={}, flightId={}", id, flightId);

    try {
      Booking booking = bookingService.getBookingById(id);
      booking = Utils.toRemoveBookingInfo(booking, BookingObject.PASSENGER, flightId);
      bookingService.saveBooking(booking);
      log.info("END - removeFlightFromBooking()");
      return new ResponseEntity<>(HttpStatus.CREATED);
    } catch (SQLException e) {
      log.warn("END - removeFlightFromBooking() error={}", e.getMessage());
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

    }
  }

  @DeleteMapping(value = "/v1/bookings/{id}/passenger/{passengerId}")
  public ResponseEntity<?> removePassengerFromBooking(@PathVariable("id") final String id,
                                                   @PathVariable("passengerId") final String passengerId) {
    log.info("START - removePassengerFromBooking(): id={}, passengerId={}", id, passengerId);

    try {
      Booking booking = bookingService.getBookingById(id);
      booking = Utils.toRemoveBookingInfo(booking, BookingObject.PASSENGER, passengerId);
      bookingService.saveBooking(booking);
      log.info("END - removePassengerFromBooking()");
      return new ResponseEntity<>(HttpStatus.CREATED);
    } catch (SQLException e) {
      log.warn("END - removePassengerFromBooking() error={}", e.getMessage());
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

    }
  }

  @DeleteMapping(value = "/v1/bookings/{id}")
  public ResponseEntity<?> removeBooking(@PathVariable("id") final String id) {
    log.info("START - removeBooking(): id={}", id);

    bookingService.removeBooking(bookingService.getBookingById(id));

    log.info("END - removeBooking()");
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

}
