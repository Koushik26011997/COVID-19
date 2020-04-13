package com.example.covid_19.states_Apis;

/**
 * Created by JacksonGenerator on 3/4/20.
 */

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)

public class ResponseTotalCases {
    @JsonProperty("cases_time_series")
    List<CasesTimeSeriesItem> casesTimeSeries;
    @JsonProperty("tested")
    List<TestedItem> tested;
    @JsonProperty("key_values")
    List<KeyValuesItem> keyValues;
    @JsonProperty("statewise")
    List<StatewiseItem> statewise;

    public List<CasesTimeSeriesItem> getCasesTimeSeries() {
        return casesTimeSeries;
    }

    public List<TestedItem> getTested() {
        return tested;
    }

    public List<KeyValuesItem> getKeyValues() {
        return keyValues;
    }

    public List<StatewiseItem> getStatewise() {
        return statewise;
    }
}