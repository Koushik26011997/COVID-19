package com.example.covid_19.states_Apis;

/**
 * Created by JacksonGenerator on 3/4/20.
 */

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)

public class TestedItem {
    @JsonProperty("testsconductedbyprivatelabs")
    private String testsconductedbyprivatelabs;
    @JsonProperty("totalsamplestested")
    private String totalsamplestested;
    @JsonProperty("source")
    private String source;
    @JsonProperty("updatetimestamp")
    private String updatetimestamp;
    @JsonProperty("totalindividualstested")
    private String totalindividualstested;
    @JsonProperty("totalpositivecases")
    private String totalpositivecases;
}