package com.backbase.movie.service.impl;

import com.backbase.movie.entity.Movie;
import com.backbase.movie.model.db.Award;
import com.backbase.movie.model.db.Rate;
import com.backbase.movie.model.db.User;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class MovieTestUtil {

    public static List<Movie> generateMovieList() {
        List<Movie> movies = new ArrayList<>();
        movies.add(generateMovieByIdAndRate("1", 2));
        movies.add(generateMovieByIdAndRate("2", 2));
        movies.add(generateMovieByIdAndRate("3", 2));
        movies.add(generateMovieByIdAndRate("4", 2));
        movies.add(generateMovieByIdAndRate("5", 2));
        movies.add(generateMovieByIdAndRate("6", 2));
        movies.add(generateMovieByIdAndRate("7", 2));
        movies.add(generateMovieByIdAndRate("8", 2));
        movies.add(generateMovieByIdAndRate("9", 2));
        movies.add(generateMovieByIdAndRate("10", 2));

        return movies;
    }

    public static Movie generateMovieByIdAndRate(String id, int userRate) {
        User user = User.builder()
                .userId("10")
                .userName("User")
                .build();
        Rate rate = Rate.builder()
                .userRate(userRate)
                .Id("1")
                .user(user)
                .build();
        Award award = getAward(true);
        List<Rate> rates = new ArrayList<>();
        rates.add(rate);
        rates.add(rate);
        rates.add(rate);
        return Movie.builder()
                .additionalInfo("Additional Info")
                .title("Title")
                .year("2020")
                .rates(rates)
                .award(award)
                .boxOffice(BigDecimal.valueOf(120))
                .id(id)
                .build();

    }

    private static Award getAward(boolean b) {
        return Award.builder()
                .category("Best Picture")
                .won(b)
                .build();
    }
}
