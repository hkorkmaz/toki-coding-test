package com.tokigames.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class BusinessFlight {

    private String departure;
    private String arrival;
    private Long arrivalTime;
    private Long departureTime;
}