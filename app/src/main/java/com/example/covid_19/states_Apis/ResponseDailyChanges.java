package com.example.covid_19.states_Apis;

/**
 * Created by JacksonGenerator on 5/4/20.
 */

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseDailyChanges {
    @JsonProperty("cases_time_series")
    List<CasesTimeSeriesItem> casesTimeSeries;

    @JsonProperty("statewise")
    List<StatewiseItem> statewise;

    public List<CasesTimeSeriesItem> getCasesTimeSeries() {
        return casesTimeSeries;
    }

    public List<StatewiseItem> getStatewise() {
        return statewise;
    }
}