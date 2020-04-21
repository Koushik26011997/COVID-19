package com.example.covid_19.states_Apis;

/**
 * Created by JacksonGenerator on 20/4/20.
 */

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;


public class ResponseTestNumbers {
    @JsonProperty("cases_time_series")
    private List<CasesTimeSeriesItem> casesTimeSeries;
    @JsonProperty("tested")
    private List<TestedItem> tested;
    @JsonProperty("statewise")
    private List<StatewiseItem> statewise;
}