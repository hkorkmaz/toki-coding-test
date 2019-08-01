package com.tokigames.controller;

import com.tokigames.model.Flight;
import com.tokigames.service.FlightService;
import com.tokigames.util.web.PageParams;
import com.tokigames.util.web.Result;
import com.tokigames.util.web.SortParams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class FlightController {

    @Autowired
    FlightService flightService;

    @RequestMapping("/api/flights")
    ResponseEntity<Result> flights(SortParams sortParams, PageParams pageParams) {
        long start = System.currentTimeMillis();

        List<Flight> flights = flightService.getFlights(sortParams, pageParams);
        log.info("Elapsed time: " + (System.currentTimeMillis() - start));

        return Result.Success()
                .add("sorting", sortParams)
                .add("page", pageParams)
                .add("flights", flights)
                .build();
    }
}
