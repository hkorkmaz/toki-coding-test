package com.tokigames.model;

import java.util.function.Function;

public class FlightConverters {

    public static Function<CheapFlight, Flight> cheapFlightToFlight = cheapFlight -> {
        Flight flight = new Flight();
        String[] route = cheapFlight.getRoute().split("-");

        flight.setArrivalTime(cheapFlight.getArrival());
        flight.setDepartureTime(cheapFlight.getDeparture());
        flight.setArrival(route[0]);
        flight.setDeparture(route[1]);
        return flight;
    };


    public static Function<BusinessFlight, Flight> businessFlightToFlight = businessFlight -> {
        Flight flight = new Flight();
        flight.setDeparture(businessFlight.getDeparture());
        flight.setArrival(businessFlight.getArrival());
        flight.setDepartureTime(businessFlight.getDepartureTime());
        flight.setArrivalTime(businessFlight.getArrivalTime());
        return flight;
    };
}
