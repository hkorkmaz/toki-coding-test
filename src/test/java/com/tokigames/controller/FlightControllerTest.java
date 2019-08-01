package com.tokigames.controller;

import com.tokigames.model.Flight;
import com.tokigames.service.FlightService;
import com.tokigames.util.web.FilterParams;
import com.tokigames.util.web.PageParams;
import com.tokigames.util.web.SortParams;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class FlightControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private FlightService flightService;

    @Test
    public void test_aggregation() throws Exception {
        SortParams sortParams = new SortParams();
        PageParams pageParams = new PageParams();
        FilterParams filterParams = new FilterParams();

        Flight f1 = Flight.builder().departure("D1").arrival("A1").departureTime(1L).arrivalTime(1L).build();
        Flight f2 = Flight.builder().departure("D2").arrival("A2").departureTime(1L).arrivalTime(1L).build();
        Flight f3 = Flight.builder().departure("D3").arrival("A3").departureTime(1L).arrivalTime(1L).build();

        List<Flight> flights = Arrays.asList(f1, f2, f3);

        doReturn(flights).when(flightService).getFlights(sortParams, pageParams, filterParams);

        mvc.perform(get("/api/flights")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.flights", hasSize(3)))
                .andExpect(jsonPath("$.page.page", equalTo(1)))
                .andExpect(jsonPath("$.page.pageSize", equalTo(10)))
                .andReturn();
    }

    @Test
    public void test_sorting_and_pagination() throws Exception {
        SortParams sortParams = new SortParams("arrival", "desc");
        PageParams pageParams = new PageParams(2, 2);
        FilterParams filterParams = new FilterParams();

        Flight f1 = Flight.builder().departure("D1").arrival("A1").departureTime(1L).arrivalTime(1L).build();
        Flight f2 = Flight.builder().departure("D2").arrival("A2").departureTime(1L).arrivalTime(1L).build();

        List<Flight> flights = Arrays.asList(f1, f2);

        doReturn(flights).when(flightService).getFlights(sortParams, pageParams, filterParams);

        mvc.perform(get("/api/flights?sortBy=arrival&dir=desc&page=2&pageSize=2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.flights", hasSize(2)))
                .andExpect(jsonPath("$.page.page", equalTo(2)))
                .andExpect(jsonPath("$.page.pageSize", equalTo(2)))
                .andExpect(jsonPath("$.flights[0].arrival", equalTo("A1")))
                .andExpect(jsonPath("$.flights[1].arrival", equalTo("A2")))
                .andReturn();
    }

    @Test
    public void test_sorting_and_pagination_and_filtering() throws Exception {
        SortParams sortParams = new SortParams("arrival", "desc");
        PageParams pageParams = new PageParams(2, 2);
        FilterParams filterParams = new FilterParams("departure:D2");

        Flight f2 = Flight.builder().departure("D2").arrival("A2").departureTime(1L).arrivalTime(2L).build();
        Flight f3 = Flight.builder().departure("D2").arrival("A3").departureTime(1L).arrivalTime(2L).build();

        List<Flight> flights = Arrays.asList(f3, f2);

        doReturn(flights).when(flightService).getFlights(sortParams, pageParams, filterParams);

        mvc.perform(get("/api/flights?sortBy=arrival&dir=desc&page=2&pageSize=2&filterBy=departure:D2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.flights", hasSize(2)))
                .andExpect(jsonPath("$.page.page", equalTo(2)))
                .andExpect(jsonPath("$.page.pageSize", equalTo(2)))
                .andExpect(jsonPath("$.filter.filterBy", equalTo("departure:D2")))
                .andExpect(jsonPath("$.sorting.sortBy", equalTo("arrival")))
                .andExpect(jsonPath("$.flights[0].arrival", equalTo("A3")))
                .andExpect(jsonPath("$.flights[1].arrival", equalTo("A2")))
                .andReturn();
    }

    @Test
    public void test_exception() throws Exception {
        SortParams sortParams = new SortParams();
        PageParams pageParams = new PageParams();
        FilterParams filterParams = new FilterParams();

        doThrow(RuntimeException.class).when(flightService).getFlights(sortParams, pageParams, filterParams);

        mvc.perform(get("/api/flights")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.message", notNullValue()))
                .andReturn();
    }
}
