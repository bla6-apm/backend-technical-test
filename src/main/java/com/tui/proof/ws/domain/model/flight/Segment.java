package com.tui.proof.ws.domain.model.flight;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@EqualsAndHashCode
@NoArgsConstructor
@ToString
public class Segment implements Serializable {

    private static final long serialVersionUID = 8346201601621579576L;

    @JsonProperty("duration")
    public long duration;

    @JsonProperty("departureTime")
    public String departureTime;

    @JsonProperty("arrivalTime")
    public String arrivalTime;

    @JsonProperty("origin")
    public String origin;

    @JsonProperty("destination")
    public String destination;

    @JsonProperty("connectionDuration")
    public long connectionDuration;
}