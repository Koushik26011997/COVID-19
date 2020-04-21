package com.example.covid_19.states_Apis;

/**
 * Created by JacksonGenerator on 21/4/20.
 */

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)

public class ResponseResources
{
    @JsonProperty("resources")
    private List<ResourcesItem> resources;

    public List<ResourcesItem> getResources() {
        return resources;
    }
}