package com.example.covid_19.states_Apis;

/**
 * Created by JacksonGenerator on 3/4/20.
 */

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)

public class StatewiseItem
{
    @JsonProperty("recovered")
    String recovered;
    @JsonProperty("deltadeaths")
    String deltadeaths;
    @JsonProperty("deltarecovered")
    String deltarecovered;
    @JsonProperty("delta")
    Delta delta;
    @JsonProperty("active")
    String active;
    @JsonProperty("deltaconfirmed")
    String deltaconfirmed;
    @JsonProperty("state")
    String state;
    @JsonProperty("statecode")
    String statecode;
    @JsonProperty("confirmed")
    String confirmed;
    @JsonProperty("deaths")
    String deaths;
    @JsonProperty("lastupdatedtime")
    String lastupdatedtime;
    @JsonProperty("statenotes")
    String statenotes;

    public String getStatenotes() {
        return statenotes;
    }

    public String getRecovered() {
        return recovered;
    }

    public String getDeltadeaths() {
        return deltadeaths;
    }

    public String getDeltarecovered() {
        return deltarecovered;
    }

    public Delta getDelta() {
        return delta;
    }

    public String getActive() {
        return active;
    }

    public String getDeltaconfirmed() {
        return deltaconfirmed;
    }

    public String getState() {
        return state;
    }

    public String getStatecode() {
        return statecode;
    }

    public String getConfirmed() {
        return confirmed;
    }

    public String getDeaths() {
        return deaths;
    }

    public String getLastupdatedtime() {
        return lastupdatedtime;
    }
}