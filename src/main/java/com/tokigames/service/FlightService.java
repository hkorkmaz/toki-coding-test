package com.tokigames.service;

import com.tokigames.gateway.FlightGateway;
import com.tokigames.model.BusinessFlight;
import com.tokigames.model.CheapFlight;
import com.tokigames.model.Flight;
import com.tokigames.util.web.PageParams;
import com.tokigames.util.web.SortParams;
import com.tokigames.util.web.Sorter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.tokigames.model.FlightConverters.businessFlightToFlight;
import static com.tokigames.model.FlightConverters.cheapFlightToFlight;

@Service
@Slf4j
public class FlightService {

    @Autowired
    private FlightGateway flightGateway;

    private Sorter<Flight> flightSorter;

    public FlightService() {
        this.flightSorter = new Sorter<Flight>()
                .add("arrival", Comparator.comparing(Flight::getArrival))
                .add("departure", Comparator.comparing(Flight::getDeparture));
    }

    public List<Flight> getFlights(SortParams sortParams, PageParams pageParams) {
        CompletableFuture<List<CheapFlight>> cheapFlightsF = flightGateway.getCheapFlights();
        CompletableFuture<List<BusinessFlight>> businessFlightsF = flightGateway.getBusinessFlights();

        CompletableFuture.allOf(cheapFlightsF, businessFlightsF).join();

        Stream<Flight> cheapFlights = cheapFlightsF.join().stream().map(cheapFlightToFlight);
        Stream<Flight> businessFlights = businessFlightsF.join().stream().map(businessFlightToFlight);

        List<Flight> result = Stream.concat(cheapFlights, businessFlights)
                .sorted(flightSorter.sortBy(sortParams))
                .skip(pageParams.skip())
                .limit(pageParams.limit())
                .collect(Collectors.toList());

        return result;
    }
}