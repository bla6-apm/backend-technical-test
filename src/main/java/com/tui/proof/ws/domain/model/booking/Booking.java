package com.tui.proof.ws.domain.model.booking;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tui.proof.ws.domain.jsondata.BookingJsonType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Builder
@EqualsAndHashCode
@AllArgsConstructor
@ToString
@Entity
@Table(name = "booking")
@org.hibernate.annotations.TypeDef(name = "BookingJsonType", typeClass = BookingJsonType.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Booking implements Serializable {

    private static final long serialVersionUID = 5983719644332506968L;

    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String id;

    @Type(type = "BookingJsonType")
    @Column(name = "data")
    @JsonProperty("data")
    private BookingInfo data;

    @Column(name = "status")
    @JsonProperty("status")
    private int status;

    public Booking() {
        this.data = new BookingInfo();
    }
}
