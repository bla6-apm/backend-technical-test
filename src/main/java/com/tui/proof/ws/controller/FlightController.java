package com.tui.proof.ws.controller;

import com.tui.proof.ws.domain.model.Passenger;
import com.tui.proof.ws.domain.model.flight.Flight;
import com.tui.proof.ws.service.FlightService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.condition.RequestMethodsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
@RestController
@RequestMapping("/public")
public class FlightController {

    @Autowired
    private FlightService flightService;

    @Autowired
    WebApplicationContext applicationContext;


    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> main(){
        RequestMappingHandlerMapping mapping = applicationContext.getBean(RequestMappingHandlerMapping.class);
        Map<RequestMappingInfo, HandlerMethod> map = mapping.getHandlerMethods();

        List<Map<String, String>> list = new ArrayList<>();
        for (Map.Entry<RequestMappingInfo, HandlerMethod> m : map.entrySet()) {
            Map<String, String> map1 = new HashMap<>();
            RequestMappingInfo info = m.getKey();
            HandlerMethod method = m.getValue();
            PatternsRequestCondition p = info.getPatternsCondition();
            for (String url : p.getPatterns()) {
                map1.put("url", url);
            }
            map1.put ("className", method.getMethod (). getDeclaringClass (). getName ());
            map1.put ("method", method.getMethod (). getName ()); // method name
            RequestMethodsRequestCondition methodsCondition = info.getMethodsCondition();
            for (RequestMethod requestMethod : methodsCondition.getMethods()) {
                map1.put("type", requestMethod.toString());
            }

            list.add(map1);
        }

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping(value = "/v1/flights", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getFlights() throws IOException {
        log.info("START - getFlights()");

        final List<Flight> dataList = flightService.getFlights();

        if (dataList.isEmpty()) {
            log.info("END - getFlights() - {}", HttpStatus.NO_CONTENT);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        log.info("END - getFlights() - {}", dataList);
        return new ResponseEntity<>(dataList, HttpStatus.OK);
    }

    @GetMapping(value = "/v1/flights/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getFlight(@PathVariable("id") final String id) {
        log.info("START - getFlight()");

        Flight flight = flightService.getFlightById(id);

        if (flight == null) {
            log.info("END - getFlight() - {}", HttpStatus.NO_CONTENT);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        log.info("END - getFlight() - {}", flight);
        return new ResponseEntity<>(flight, HttpStatus.OK);
    }

    @GetMapping(value = "/v1/passengers", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getPassengers() {
        log.info("START - getPassengers()");

        final List<Passenger> dataList = flightService.getPassengers();

        if (dataList.isEmpty()) {
            log.info("END - getPassengers() - {}", HttpStatus.NO_CONTENT);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        log.info("END - getPassengers() - {}", dataList);
        return new ResponseEntity<>(dataList, HttpStatus.OK);
    }

    @GetMapping(value = "/v1/passengers/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getPassenger(@PathVariable("id") final String id) {
        log.info("START - getPassenger()");

        Passenger flight = flightService.getPassengerById(id);

        if (flight == null) {
            log.info("END - getPassenger() - {}", HttpStatus.NO_CONTENT);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }


        log.info("END - getPassenger() - {}", flight);
        return new ResponseEntity<>(flight, HttpStatus.OK);
    }

}
