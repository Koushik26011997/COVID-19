package com.example.covid_19.states_Apis;

/**
 * Created by JacksonGenerator on 3/4/20.
 */

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CasesTimeSeriesItem {
    @JsonProperty("date")
     String date;
    @JsonProperty("dailyrecovered")
     String dailyrecovered;
    @JsonProperty("totalconfirmed")
     String totalconfirmed;
    @JsonProperty("totaldeceased")
     String totaldeceased;
    @JsonProperty("dailydeceased")
     String dailydeceased;
    @JsonProperty("totalrecovered")
     String totalrecovered;
    @JsonProperty("dailyconfirmed")
     String dailyconfirmed;

    public String getDate() {
        return date;
    }

    public String getDailyrecovered() {
        return dailyrecovered;
    }

    public String getTotalconfirmed() {
        return totalconfirmed;
    }

    public String getTotaldeceased() {
        return totaldeceased;
    }

    public String getDailydeceased() {
        return dailydeceased;
    }

    public String getTotalrecovered() {
        return totalrecovered;
    }

    public String getDailyconfirmed() {
        return dailyconfirmed;
    }
}