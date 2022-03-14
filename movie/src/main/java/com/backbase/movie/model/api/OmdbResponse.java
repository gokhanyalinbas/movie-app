package com.backbase.movie.model.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OmdbResponse implements Serializable {

    @JsonProperty("Awards")
    private String awards;
    @JsonProperty("Title")
    private String title;
    @JsonProperty("Plot")
    private String additionalInfo;
    @JsonProperty("Year")
    private String year;
    @JsonProperty("BoxOffice")
    private String boxOffice;
    @JsonProperty("Response")
    private String response;
    @JsonProperty("Error")
    private String error;
}
