package com.tui.proof.ws.domain.model.flight;

import com.fasterxml.jackson.annotation.JsonProperty;
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
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class FlightInfo implements Serializable {

    private static final long serialVersionUID = 8245378576312968608L;

    @JsonProperty("id")
    private String id;

    @JsonProperty("company")
    private String company;

    @JsonProperty("points")
    private long points;

    @JsonProperty("duration")
    private long duration;

    @JsonProperty("availability")
    private long availability;

    @JsonProperty("paxes")
    private List<Pax> paxes;

    @JsonProperty("segment")
    private List<Segment> segment;

    public FlightInfo() {
        this.id = UUID.randomUUID().toString();
        this.paxes = new ArrayList<>();
        this.segment = new ArrayList<>();
    }
}
