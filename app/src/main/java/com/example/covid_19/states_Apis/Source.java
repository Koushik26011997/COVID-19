package com.example.covid_19.states_Apis;

/**
 * Created by JacksonGenerator on 7/6/20.
 */

import com.fasterxml.jackson.annotation.JsonProperty;


public class Source {
    @JsonProperty("name")
    String name;

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    @JsonProperty("id")
    String id;
}