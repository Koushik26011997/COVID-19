package com.example.covid_19.states_Apis;

/**
 * Created by JacksonGenerator on 7/6/20.
 */

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)

public class ArticlesItem {
    @JsonProperty("publishedAt")
    String publishedAt;
    @JsonProperty("author")
    String author;
    @JsonProperty("urlToImage")
    String urlToImage;
    @JsonProperty("description")
    String description;
    @JsonProperty("source")
    Source source;
    @JsonProperty("title")
    String title;
    @JsonProperty("url")
    String url;
    @JsonProperty("content")
    String content;

    public String getPublishedAt() {
        return publishedAt;
    }

    public String getAuthor() {
        return author;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public String getDescription() {
        return description;
    }

    public Source getSource() {
        return source;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public String getContent() {
        return content;
    }
}