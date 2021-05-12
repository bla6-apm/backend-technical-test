package com.tui.proof.ws.domain.repository;

import com.tui.proof.ws.domain.model.booking.Booking;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends CrudRepository<Booking, String> {

    @Query("SELECT o FROM Booking o WHERE o.status=:status")
    List<Booking> getDraftedBookings(@Param("status") final int status);

    @Query("SELECT o FROM Booking o WHERE o.id=:id")
    List<Booking> getBookingById(@Param("id") final String id);
}
