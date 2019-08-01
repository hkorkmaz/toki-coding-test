package com.tokigames.model;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class FlightConverterTest {

    @Test
    public void test_businessflight_convertion() {
        BusinessFlight b1 = BusinessFlight.builder().departure("D1").arrival("A1").arrivalTime(999L).departureTime(1000L).build();
        Flight result = FlightConverters.businessFlightToFlight.apply(b1);

        assertEquals("D1", result.getDeparture());
        assertEquals("A1", result.getArrival());
        assertEquals(999L, result.getArrivalTime().longValue());
        assertEquals(1000L, result.getDepartureTime().longValue());
    }

    @Test
    public void test_cheapflight_convertion() {
        CheapFlight c1 = CheapFlight.builder().route("D1-A1").arrival(999L).departure(1000L).build();
        Flight result = FlightConverters.cheapFlightToFlight.apply(c1);

        assertEquals("D1", result.getDeparture());
        assertEquals("A1", result.getArrival());
        assertEquals(999L, result.getArrivalTime().longValue());
        assertEquals(1000L, result.getDepartureTime().longValue());
    }
}