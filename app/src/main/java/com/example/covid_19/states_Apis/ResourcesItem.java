package com.example.covid_19.states_Apis;

/**
 * Created by JacksonGenerator on 21/4/20.
 */

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonIgnoreProperties(ignoreUnknown = true)

public class ResourcesItem 
{
    @JsonProperty("city")
    String city;
    @JsonProperty("descriptionandorserviceprovided")
    String descriptionandorserviceprovided;
    @JsonProperty("contact")
    String contact;
    @JsonProperty("phonenumber")
    String phonenumber;
    @JsonProperty("state")
    String state;
    @JsonProperty("category")
    String category;
    @JsonProperty("nameoftheorganisation")
    String nameoftheorganisation;

    public String getCity() {
        return city;
    }

    public String getDescriptionandorserviceprovided() {
        return descriptionandorserviceprovided;
    }

    public String getContact() {
        return contact;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public String getState() {
        return state;
    }

    public String getCategory() {
        return category;
    }

    public String getNameoftheorganisation() {
        return nameoftheorganisation;
    }
}