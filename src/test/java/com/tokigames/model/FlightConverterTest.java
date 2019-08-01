package com.tokigames.model;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class FlightConverterTest {

    @Test
    public void test_businessflight_convertion() {
        BusinessFlight b1 = new BusinessFlight("A1", "D1", 999L, 1000L);
        Flight result = FlightConverters.businessFlightToFlight.apply(b1);

        assertEquals("A1", result.getArrival());
        assertEquals("D1", result.getDeparture());
        assertEquals(999L, result.getArrivalTime().longValue());
        assertEquals(1000L, result.getDepartureTime().longValue());
    }

    @Test
    public void test_cheapflight_convertion() {
        CheapFlight c1 = new CheapFlight("A1-D1", 999L, 1000L);
        Flight result = FlightConverters.cheapFlightToFlight.apply(c1);

        assertEquals("A1", result.getArrival());
        assertEquals("D1", result.getDeparture());
        assertEquals(999L, result.getArrivalTime().longValue());
        assertEquals(1000L, result.getDepartureTime().longValue());
    }
}