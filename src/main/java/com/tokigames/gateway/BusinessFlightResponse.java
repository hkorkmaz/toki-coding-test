package com.tokigames.gateway;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tokigames.model.BusinessFlight;

import java.util.ArrayList;
import java.util.List;

public class BusinessFlightResponse {

    @JsonProperty("data")
    private List<BusinessFlight> flights = new ArrayList<>();


    public List<BusinessFlight> getFlights() {
        return flights;
    }

    public void setFlights(List<BusinessFlight> flights) {
        this.flights = flights;
    }
}
