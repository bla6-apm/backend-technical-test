package com.tui.proof.ws.domain.model.booking;

import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@NoArgsConstructor
@ToString
public enum BookingStatus implements Serializable {

    DRAFT(0),
    CONFIRMED(1),
    ;

    private int status;

    private static final long serialVersionUID = -5598453805368112890L;

    BookingStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public BookingStatus fromDatabase(int status) {
        for(BookingStatus wd: BookingStatus.values()) {
            if(wd.status == status) {
                return wd;
            }
        }
        return null;
    }
}
