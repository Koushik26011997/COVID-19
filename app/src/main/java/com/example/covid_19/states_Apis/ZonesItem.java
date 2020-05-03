package com.example.covid_19.states_Apis;

/**
 * Created by JacksonGenerator on 3/5/20.
 */

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)

public class ZonesItem 
{
    @JsonProperty("zone")
    String zone;
    @JsonProperty("district")
    String district;
    @JsonProperty("lastupdated")
    String lastupdated;
    @JsonProperty("source")
    String source;
    @JsonProperty("state")
    String state;
    @JsonProperty("districtcode")
    String districtcode;
    @JsonProperty("statecode")
    String statecode;

    public String getZone() {
        return zone;
    }

    public String getDistrict() {
        return district;
    }

    public String getLastupdated() {
        return lastupdated;
    }

    public String getSource() {
        return source;
    }

    public String getState() {
        return state;
    }

    public String getDistrictcode() {
        return districtcode;
    }

    public String getStatecode() {
        return statecode;
    }
}