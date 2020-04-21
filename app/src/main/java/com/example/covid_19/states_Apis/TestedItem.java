package com.example.covid_19.states_Apis;

/**
 * Created by JacksonGenerator on 3/4/20.
 */

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)

public class TestedItem
{
    @JsonProperty("testsconductedbyprivatelabs")
    String testsconductedbyprivatelabs;
    @JsonProperty("totalsamplestested")
    String totalsamplestested;
    @JsonProperty("source")
    String source;
    @JsonProperty("updatetimestamp")
    String updatetimestamp;
    @JsonProperty("totalindividualstested")
    String totalindividualstested;
    @JsonProperty("totalpositivecases")
    String totalpositivecases;

    public String getTestsconductedbyprivatelabs() {
        return testsconductedbyprivatelabs;
    }

    public String getTotalsamplestested() {
        return totalsamplestested;
    }

    public String getSource() {
        return source;
    }

    public String getUpdatetimestamp() {
        return updatetimestamp;
    }

    public String getTotalindividualstested() {
        return totalindividualstested;
    }

    public String getTotalpositivecases() {
        return totalpositivecases;
    }
}