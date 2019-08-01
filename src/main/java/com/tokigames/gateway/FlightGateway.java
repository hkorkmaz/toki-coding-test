package com.tokigames.gateway;

import com.tokigames.model.BusinessFlight;
import com.tokigames.model.CheapFlight;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
@Slf4j
public class FlightGateway {

    private final RestTemplate restTemplate;

    @Value("${flight.api.endpoint}")
    private String flightApiEndpoint;

    public FlightGateway(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @Async("externalApi")
    public CompletableFuture<List<CheapFlight>> getCheapFlights() {
        String url = flightApiEndpoint + "/api/flights/cheap";
        log.info("Getting cheap flights url={}", url);
        CheapFlightResponse flightApiResponse = restTemplate.getForObject(url, CheapFlightResponse.class);
        return CompletableFuture.completedFuture(flightApiResponse.getFlights());
    }

    @Async("externalApi")
    public CompletableFuture<List<BusinessFlight>> getBusinessFlights() {
        String url = flightApiEndpoint + "/api/flights/business";
        log.info("Getting business flights url={}", url);
        BusinessFlightResponse flightApiResponse = restTemplate.getForObject(url, BusinessFlightResponse.class);
        return CompletableFuture.completedFuture(flightApiResponse.getFlights());
    }
}
