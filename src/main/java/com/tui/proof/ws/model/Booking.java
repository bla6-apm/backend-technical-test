package com.tui.proof.ws.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.READ_ONLY;

@Data
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Booking implements Serializable {

    private static final long serialVersionUID = -4403283700847067030L;

    @JsonProperty(value = "id", access = READ_ONLY)
    private String id = UUID.randomUUID().toString();

    @JsonProperty("passengers")
    private List<Passenger> passenger;

    @JsonProperty("flight")
    private Flight flight;
}
