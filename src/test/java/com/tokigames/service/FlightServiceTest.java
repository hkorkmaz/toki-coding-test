package com.tokigames.service;

import com.tokigames.gateway.FlightGateway;
import com.tokigames.model.BusinessFlight;
import com.tokigames.model.CheapFlight;
import com.tokigames.model.Flight;
import com.tokigames.util.web.FilterParams;
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
        FilterParams filterParams = new FilterParams();

        BusinessFlight b1 = BusinessFlight.builder().departure("D1").arrival("A1").arrivalTime(1L).departureTime(1L).build();
        BusinessFlight b2 = BusinessFlight.builder().departure("D2").arrival("A2").arrivalTime(1L).departureTime(1L).build();

        CheapFlight c1 = CheapFlight.builder().route("A1-D1").arrival(1L).departure(1L).build();
        CheapFlight c2 = CheapFlight.builder().route("A2-D2").arrival(1L).departure(1L).build();

        List<BusinessFlight> bussinessFlights = Arrays.asList(b1, b2);
        List<CheapFlight> cheapFlights = Arrays.asList(c1, c2);

        doReturn(CompletableFuture.completedFuture(bussinessFlights)).when(flightGateway).getBusinessFlights();
        doReturn(CompletableFuture.completedFuture(cheapFlights)).when(flightGateway).getCheapFlights();


        List<Flight> result = flightService.getFlights(sortParams, pageParams, filterParams);

        assertEquals(4, result.size());
    }

    @Test
    public void test_pagination() {
        SortParams sortParams = new SortParams();
        PageParams pageParams = new PageParams(1, 2);
        FilterParams filterParams = new FilterParams();

        BusinessFlight b1 = BusinessFlight.builder().departure("D1").arrival("A1").arrivalTime(1L).departureTime(1L).build();
        BusinessFlight b2 = BusinessFlight.builder().departure("D2").arrival("A2").arrivalTime(1L).departureTime(1L).build();

        CheapFlight c1 = CheapFlight.builder().route("A1-D1").arrival(1L).departure(1L).build();
        CheapFlight c2 = CheapFlight.builder().route("A2-D2").arrival(1L).departure(1L).build();

        List<BusinessFlight> bussinessFlights = Arrays.asList(b1, b2);
        List<CheapFlight> cheapFlights = Arrays.asList(c1, c2);

        doReturn(CompletableFuture.completedFuture(bussinessFlights)).when(flightGateway).getBusinessFlights();
        doReturn(CompletableFuture.completedFuture(cheapFlights)).when(flightGateway).getCheapFlights();


        List<Flight> result = flightService.getFlights(sortParams, pageParams, filterParams);

        assertEquals(2, result.size());
    }

    @Test
    public void test_sorting() {
        SortParams sortParams = new SortParams("departure", "desc");
        PageParams pageParams = new PageParams();
        FilterParams filterParams = new FilterParams();

        BusinessFlight b1 = BusinessFlight.builder().departure("Yozgat").arrival("Erzurum").departureTime(1L).arrivalTime(1L).build();
        BusinessFlight b2 = BusinessFlight.builder().departure("Sivas").arrival("Istanbul").departureTime(1L).arrivalTime(1L).build();

        CheapFlight c1 = CheapFlight.builder().route("Bursa-Ankara").departure(1L).arrival(1L).build();
        CheapFlight c2 = CheapFlight.builder().route("Antalya-Istanbul").departure(1L).arrival(1L).build();

        List<BusinessFlight> bussinessFlights = Arrays.asList(b1, b2);
        List<CheapFlight> cheapFlights = Arrays.asList(c1, c2);

        doReturn(CompletableFuture.completedFuture(bussinessFlights)).when(flightGateway).getBusinessFlights();
        doReturn(CompletableFuture.completedFuture(cheapFlights)).when(flightGateway).getCheapFlights();


        List<Flight> result = flightService.getFlights(sortParams, pageParams, filterParams);

        assertEquals(4, result.size());
        assertEquals("Yozgat", result.get(0).getDeparture());
        assertEquals("Sivas", result.get(1).getDeparture());
        assertEquals("Bursa", result.get(2).getDeparture());
        assertEquals("Antalya", result.get(3).getDeparture());
    }

    @Test
    public void test_filtering() {
        SortParams sortParams = new SortParams();
        PageParams pageParams = new PageParams();
        FilterParams filterParams = new FilterParams("arrival:Erzurum");

        BusinessFlight b1 = BusinessFlight.builder().departure("Yozgat").arrival("Erzurum").departureTime(1L).arrivalTime(1L).build();
        BusinessFlight b2 = BusinessFlight.builder().departure("Sivas").arrival("Istanbul").departureTime(1L).arrivalTime(1L).build();

        CheapFlight c1 = CheapFlight.builder().route("Bursa-Ankara").departure(1L).arrival(1L).build();
        CheapFlight c2 = CheapFlight.builder().route("Antalya-Istanbul").departure(1L).arrival(1L).build();

        List<BusinessFlight> bussinessFlights = Arrays.asList(b1, b2);
        List<CheapFlight> cheapFlights = Arrays.asList(c1, c2);

        doReturn(CompletableFuture.completedFuture(bussinessFlights)).when(flightGateway).getBusinessFlights();
        doReturn(CompletableFuture.completedFuture(cheapFlights)).when(flightGateway).getCheapFlights();


        List<Flight> result = flightService.getFlights(sortParams, pageParams, filterParams);

        assertEquals(1, result.size());
        assertEquals("Yozgat", result.get(0).getDeparture());
        assertEquals("Erzurum", result.get(0).getArrival());
    }

    @Test
    public void test_sorting_and_pagination() {
        SortParams sortParams = new SortParams("departure", "desc");
        PageParams pageParams = new PageParams(1, 3);
        FilterParams filterParams = new FilterParams();

        BusinessFlight b1 = BusinessFlight.builder().departure("Yozgat").arrival("Erzurum").departureTime(1L).arrivalTime(1L).build();
        BusinessFlight b2 = BusinessFlight.builder().departure("Sivas").arrival("Istanbul").departureTime(1L).arrivalTime(1L).build();

        CheapFlight c1 = CheapFlight.builder().route("Bursa-Ankara").departure(1L).arrival(1L).build();
        CheapFlight c2 = CheapFlight.builder().route("Antalya-Istanbul").departure(1L).arrival(1L).build();

        List<BusinessFlight> bussinessFlights = Arrays.asList(b1, b2);
        List<CheapFlight> cheapFlights = Arrays.asList(c1, c2);

        doReturn(CompletableFuture.completedFuture(bussinessFlights)).when(flightGateway).getBusinessFlights();
        doReturn(CompletableFuture.completedFuture(cheapFlights)).when(flightGateway).getCheapFlights();

        List<Flight> result = flightService.getFlights(sortParams, pageParams, filterParams);

        assertEquals(3, result.size());
        assertEquals("Yozgat", result.get(0).getDeparture());
        assertEquals("Sivas", result.get(1).getDeparture());
        assertEquals("Bursa", result.get(2).getDeparture());
    }

    @Test
    public void test_sorting_and_pagination_and_filter() {
        SortParams sortParams = new SortParams("departure", "desc");
        PageParams pageParams = new PageParams(1, 3);
        FilterParams filterParams = new FilterParams();

        BusinessFlight b1 = BusinessFlight.builder().departure("Yozgat").arrival("Erzurum").departureTime(1L).arrivalTime(1L).build();
        BusinessFlight b2 = BusinessFlight.builder().departure("Sivas").arrival("Istanbul").departureTime(1L).arrivalTime(1L).build();

        CheapFlight c1 = CheapFlight.builder().route("Bursa-Ankara").departure(1L).arrival(1L).build();
        CheapFlight c2 = CheapFlight.builder().route("Antalya-Istanbul").departure(1L).arrival(1L).build();

        List<BusinessFlight> bussinessFlights = Arrays.asList(b1, b2);
        List<CheapFlight> cheapFlights = Arrays.asList(c1, c2);

        doReturn(CompletableFuture.completedFuture(bussinessFlights)).when(flightGateway).getBusinessFlights();
        doReturn(CompletableFuture.completedFuture(cheapFlights)).when(flightGateway).getCheapFlights();

        List<Flight> result = flightService.getFlights(sortParams, pageParams, filterParams);

        assertEquals(3, result.size());
        assertEquals("Yozgat", result.get(0).getDeparture());
        assertEquals("Sivas", result.get(1).getDeparture());
        assertEquals("Bursa", result.get(2).getDeparture());
    }

    @Test(expected = Exception.class)
    public void test_exception_case() {
        SortParams sortParams = new SortParams();
        PageParams pageParams = new PageParams();
        FilterParams filterParams = new FilterParams();

        CheapFlight c1 = CheapFlight.builder().route("Bursa-Ankara").arrival(1L).departure(1L).build();
        CheapFlight c2 = CheapFlight.builder().route("Antalya-Istanbul").arrival(1L).departure(1L).build();

        List<CheapFlight> cheapFlights = Arrays.asList(c1, c2);

        doThrow(Exception.class).when(flightGateway).getBusinessFlights();
        doReturn(CompletableFuture.completedFuture(cheapFlights)).when(flightGateway).getCheapFlights();

        flightService.getFlights(sortParams, pageParams, filterParams);
    }

}