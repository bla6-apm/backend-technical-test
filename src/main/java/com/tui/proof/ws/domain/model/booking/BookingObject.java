package com.tui.proof.ws.domain.model.booking;

import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@NoArgsConstructor
@ToString
public enum BookingObject implements Serializable {

    PASSENGER(),
    FLIGHT(),
    ;
}

