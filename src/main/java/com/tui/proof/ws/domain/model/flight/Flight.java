package com.tui.proof.ws.domain.model.flight;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tui.proof.ws.domain.jsondata.FlightJsonType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "flight")
@org.hibernate.annotations.TypeDef(name = "FlightJsonType", typeClass = FlightJsonType.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Flight implements Serializable {

    private static final long serialVersionUID = 8245378576312968608L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    @JsonProperty("id")
    private String id;

    @Type(type = "FlightJsonType")
    @Column(name = "data")
    @JsonProperty("data")
    private FlightInfo data;

    public Flight() {
        this.id = UUID.randomUUID().toString();
        this.data = new FlightInfo();
    }

}
