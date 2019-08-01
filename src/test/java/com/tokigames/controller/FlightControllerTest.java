package com.tokigames.controller;

import com.tokigames.model.Flight;
import com.tokigames.service.FlightService;
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

        Flight f1 = new Flight("A1", "D1", 1L, 2L);
        Flight f2 = new Flight("A2", "D2", 1L, 2L);
        Flight f3 = new Flight("A3", "D3", 1L, 2L);

        List<Flight> flights = Arrays.asList(f1, f2, f3);

        doReturn(flights).when(flightService).getFlights(sortParams, pageParams);

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

        Flight f1 = new Flight("A1", "D1", 1L, 2L);
        Flight f2 = new Flight("A2", "D2", 1L, 2L);

        List<Flight> flights = Arrays.asList(f1, f2);

        doReturn(flights).when(flightService).getFlights(sortParams, pageParams);

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
    public void test_exception() throws Exception {
        SortParams sortParams = new SortParams();
        PageParams pageParams = new PageParams();

        doThrow(RuntimeException.class).when(flightService).getFlights(sortParams, pageParams);

        mvc.perform(get("/api/flights")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.message", notNullValue()))
                .andReturn();
    }
}
