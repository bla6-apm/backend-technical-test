package com.tui.proof.ws.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tui.proof.ws.domain.model.flight.FlightInfo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.READ_ONLY;

@Data
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Flight implements Serializable {

    private static final long serialVersionUID = 8245378576312968608L;

    @JsonProperty(value = "id", access = READ_ONLY)
    private String id = UUID.randomUUID().toString();

    @JsonProperty("data")
    private FlightInfo data;
}
