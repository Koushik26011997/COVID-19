package com.example.covid_19.states_Apis;

/**
 * Created by JacksonGenerator on 3/4/20.
 */

import com.fasterxml.jackson.annotation.JsonProperty;


public class Delta {
    @JsonProperty("recovered")
    Integer recovered;
    @JsonProperty("active")
    Integer active;
    @JsonProperty("confirmed")
    Integer confirmed;
    @JsonProperty("deaths")
    Integer deaths;

    public Integer getRecovered() {
        return recovered;
    }

    public Integer getActive() {
        return active;
    }

    public Integer getConfirmed() {
        return confirmed;
    }

    public Integer getDeaths() {
        return deaths;
    }
}