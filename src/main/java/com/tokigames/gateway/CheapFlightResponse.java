package com.tokigames.gateway;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tokigames.model.CheapFlight;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CheapFlightResponse {

    @JsonProperty("data")
    private List<CheapFlight> flights = new ArrayList<>();
}
