package com.example.covid_19.states_Apis;

/**
 * Created by JacksonGenerator on 7/6/20.
 */

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)

public class ResponseCovidNews {
    @JsonProperty("totalResults")
    Integer totalResults;
    @JsonProperty("articles")
    List<ArticlesItem> articles;
    @JsonProperty("status")
    String status;

    public Integer getTotalResults() {
        return totalResults;
    }

    public List<ArticlesItem> getArticles() {
        return articles;
    }

    public String getStatus() {
        return status;
    }
}