package com.tokigames.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class BusinessFlight {

    private String arrival;
    private String departure;
    private Long arrivalTime;
    private Long departureTime;
}