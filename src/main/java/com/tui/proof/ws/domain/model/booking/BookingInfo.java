package com.tui.proof.ws.domain.model.booking;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tui.proof.ws.domain.model.Passenger;
import com.tui.proof.ws.domain.model.flight.Flight;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@EqualsAndHashCode
@AllArgsConstructor
@ToString
public class BookingInfo implements Serializable {

    private static final long serialVersionUID = -3940211612942467893L;

    @JsonProperty("id")
    private String id;

    @JsonProperty("flight")
    private Flight flight;

    @JsonProperty("passengers")
    private List<Passenger> passengers;

    public BookingInfo() {
        this.id = UUID.randomUUID().toString();
        this.flight = new Flight();
        this.passengers = new ArrayList<>();
    }
}
