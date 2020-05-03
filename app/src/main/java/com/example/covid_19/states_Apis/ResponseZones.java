package com.example.covid_19.states_Apis;

/**
 * Created by JacksonGenerator on 3/5/20.
 */

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseZones
{
    @JsonProperty("zones")
    List<ZonesItem> zones;

    public List<ZonesItem> getZones()
    {
        return zones;
    }
}