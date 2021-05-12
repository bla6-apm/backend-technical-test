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
public class Pax implements Serializable {

    private static final long serialVersionUID = -4514136194601142208L;

    @JsonProperty("infant")
    public long infant;

    @JsonProperty("children")
    public long children;

    @JsonProperty("adult")
    public long adult;
}
