package com.example.covid_19.states_Apis;

/**
 * Created by JacksonGenerator on 7/4/20.
 */

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)

public class DistrictDataItem 
{
    @JsonProperty("district")
    String district;
    @JsonProperty("delta")
    Delta delta;
    @JsonProperty("confirmed")
    Integer confirmed;
    @JsonProperty("lastupdatedtime")
    String lastupdatedtime;

    public String getDistrict() {
        return district;
    }

    public Delta getDelta() {
        return delta;
    }

    public Integer getConfirmed() {
        return confirmed;
    }

    public String getLastupdatedtime() {
        return lastupdatedtime;
    }
}