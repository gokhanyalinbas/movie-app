package com.backbase.movie.entity;

import com.backbase.movie.constant.ApplicationConstant;
import com.backbase.movie.model.db.Award;
import com.backbase.movie.model.db.Rate;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.List;


@Document(collection = "movies")
@Builder
@Data
public class Movie {
    @Id
    private String id;
    private String title;
    private String additionalInfo;
    private String year;
    private BigDecimal boxOffice;
    private Award award;
    private List<Rate> rates;

    @JsonIgnore
    public double getAverageRate() {
        return rates.stream()
                .mapToDouble(Rate::getUserRate)
                .average()
                .orElse(ApplicationConstant.EMPTY);
    }

    @JsonIgnore
    public boolean isMovieWonBestPicture() {
        return award.isWon();
    }
}
