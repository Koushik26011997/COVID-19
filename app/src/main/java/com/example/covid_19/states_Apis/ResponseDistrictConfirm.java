package com.example.covid_19.states_Apis;

/**
 * Created by JacksonGenerator on 7/4/20.
 */

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)

public class ResponseDistrictConfirm
{
    @JsonProperty("districtData")

    List<DistrictDataItem> districtData;

    @JsonProperty("state")
    String state;

    public List<DistrictDataItem> getDistrictData()
    {
        return districtData;
    }

    public String getState()
    {
        return state;
    }
}