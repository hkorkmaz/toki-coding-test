package com.tokigames.service;

import com.tokigames.gateway.FlightGateway;
import com.tokigames.model.BusinessFlight;
import com.tokigames.model.CheapFlight;
import com.tokigames.model.Flight;
import com.tokigames.util.web.PageParams;
import com.tokigames.util.web.SortParams;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

@RunWith(MockitoJUnitRunner.class)
public class FlightServiceTest {

    @Mock
    private FlightGateway flightGateway;

    @InjectMocks
    private FlightService flightService;

    @Test
    public void test_aggregation() {
        SortParams sortParams = new SortParams();
        PageParams pageParams = new PageParams();

        BusinessFlight b1 = new BusinessFlight("A1", "D1", 1L, 1L);
        BusinessFlight b2 = new BusinessFlight("A2", "D2", 1L, 1L);

        CheapFlight c1 = new CheapFlight("A1-D1", 1L, 1L);
        CheapFlight c2 = new CheapFlight("A2-D2", 1L, 1L);

        List<BusinessFlight> bussinessFlights = Arrays.asList(b1, b2);
        List<CheapFlight> cheapFlights = Arrays.asList(c1, c2);

        doReturn(CompletableFuture.completedFuture(bussinessFlights)).when(flightGateway).getBusinessFlights();
        doReturn(CompletableFuture.completedFuture(cheapFlights)).when(flightGateway).getCheapFlights();


        List<Flight> result = flightService.getFlights(sortParams, pageParams);

        assertEquals(4, result.size());
    }

    @Test
    public void test_pagination() {
        SortParams sortParams = new SortParams();
        PageParams pageParams = new PageParams(1, 2);

        BusinessFlight b1 = new BusinessFlight("A1", "D1", 1L, 1L);
        BusinessFlight b2 = new BusinessFlight("A2", "D2", 1L, 1L);

        CheapFlight c1 = new CheapFlight("A1-D1", 1L, 1L);
        CheapFlight c2 = new CheapFlight("A2-D2", 1L, 1L);

        List<BusinessFlight> bussinessFlights = Arrays.asList(b1, b2);
        List<CheapFlight> cheapFlights = Arrays.asList(c1, c2);

        doReturn(CompletableFuture.completedFuture(bussinessFlights)).when(flightGateway).getBusinessFlights();
        doReturn(CompletableFuture.completedFuture(cheapFlights)).when(flightGateway).getCheapFlights();


        List<Flight> result = flightService.getFlights(sortParams, pageParams);

        assertEquals(2, result.size());
    }

    @Test
    public void test_sorting() {
        SortParams sortParams = new SortParams("arrival", "desc");
        PageParams pageParams = new PageParams();

        BusinessFlight b1 = new BusinessFlight("Yozgat", "Erzurum", 1L, 1L);
        BusinessFlight b2 = new BusinessFlight("Sivas", "Istanbul", 1L, 1L);

        CheapFlight c1 = new CheapFlight("Bursa-Ankara", 1L, 1L);
        CheapFlight c2 = new CheapFlight("Antalya-Istanbul", 1L, 1L);

        List<BusinessFlight> bussinessFlights = Arrays.asList(b1, b2);
        List<CheapFlight> cheapFlights = Arrays.asList(c1, c2);

        doReturn(CompletableFuture.completedFuture(bussinessFlights)).when(flightGateway).getBusinessFlights();
        doReturn(CompletableFuture.completedFuture(cheapFlights)).when(flightGateway).getCheapFlights();


        List<Flight> result = flightService.getFlights(sortParams, pageParams);

        assertEquals(4, result.size());
        assertEquals("Yozgat", result.get(0).getArrival());
        assertEquals("Sivas", result.get(1).getArrival());
        assertEquals("Bursa", result.get(2).getArrival());
        assertEquals("Antalya", result.get(3).getArrival());
    }

    @Test
    public void test_sorting_and_pagination() {
        SortParams sortParams = new SortParams("arrival", "desc");
        PageParams pageParams = new PageParams(1, 3);

        BusinessFlight b1 = new BusinessFlight("Yozgat", "Erzurum", 1L, 1L);
        BusinessFlight b2 = new BusinessFlight("Sivas", "Istanbul", 1L, 1L);

        CheapFlight c1 = new CheapFlight("Bursa-Ankara", 1L, 1L);
        CheapFlight c2 = new CheapFlight("Antalya-Istanbul", 1L, 1L);

        List<BusinessFlight> bussinessFlights = Arrays.asList(b1, b2);
        List<CheapFlight> cheapFlights = Arrays.asList(c1, c2);

        doReturn(CompletableFuture.completedFuture(bussinessFlights)).when(flightGateway).getBusinessFlights();
        doReturn(CompletableFuture.completedFuture(cheapFlights)).when(flightGateway).getCheapFlights();

        List<Flight> result = flightService.getFlights(sortParams, pageParams);

        assertEquals(3, result.size());
        assertEquals("Yozgat", result.get(0).getArrival());
        assertEquals("Sivas", result.get(1).getArrival());
        assertEquals("Bursa", result.get(2).getArrival());
    }

    @Test(expected = Exception.class)
    public void test_exception_case() {
        SortParams sortParams = new SortParams();
        PageParams pageParams = new PageParams();

        CheapFlight c1 = new CheapFlight("Bursa-Ankara", 1L, 1L);
        CheapFlight c2 = new CheapFlight("Antalya-Istanbul", 1L, 1L);

        List<CheapFlight> cheapFlights = Arrays.asList(c1, c2);

        doThrow(Exception.class).when(flightGateway).getBusinessFlights();
        doReturn(CompletableFuture.completedFuture(cheapFlights)).when(flightGateway).getCheapFlights();

        flightService.getFlights(sortParams, pageParams);
    }

}