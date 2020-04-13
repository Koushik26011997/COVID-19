package com.example.covid_19.states_Apis;

/**
 * Created by JacksonGenerator on 3/4/20.
 */

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)

public class KeyValuesItem {
    @JsonProperty("confirmeddelta")
    private String confirmeddelta;
    @JsonProperty("recovereddelta")
    private String recovereddelta;
    @JsonProperty("deceaseddelta")
    private String deceaseddelta;
    @JsonProperty("statesdelta")
    private String statesdelta;
    @JsonProperty("counterforautotimeupdate")
    private String counterforautotimeupdate;
    @JsonProperty("lastupdatedtime")
    private String lastupdatedtime;
}