package com.tokigames.model;

import java.util.function.Function;

public class FlightConverters {

    public static Function<CheapFlight, Flight> cheapFlightToFlight = cheapFlight -> {
        String[] route = cheapFlight.getRoute().split("-");

        return Flight.builder()
                .departure(route[0])
                .arrival(route[1])
                .departureTime(cheapFlight.getDeparture())
                .arrivalTime(cheapFlight.getArrival())
                .build();
    };


    public static Function<BusinessFlight, Flight> businessFlightToFlight = businessFlight -> {
        return Flight.builder()
                .departure(businessFlight.getDeparture())
                .arrival(businessFlight.getArrival())
                .departureTime(businessFlight.getDepartureTime())
                .arrivalTime(businessFlight.getArrivalTime())
                .build();
    };
}
