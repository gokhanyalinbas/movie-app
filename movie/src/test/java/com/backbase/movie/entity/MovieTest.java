package com.backbase.movie.entity;

import com.backbase.movie.model.db.Award;
import com.backbase.movie.model.db.Rate;
import com.backbase.movie.model.db.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MovieTest {
    private Movie movie;

    @BeforeEach
    void init() {
        User user = User.builder()
                .userId("10")
                .userName("User")
                .build();
        Rate rate = Rate.builder()
                .userRate(2)
                .user(user)
                .build();
        Award award = getAward(true);
        movie = Movie.builder()
                .additionalInfo("Additional Info")
                .title("Title")
                .year("2020")
                .rates(Arrays.asList(rate, rate, rate))
                .award(award)
                .boxOffice(BigDecimal.valueOf(120))
                .id("1212")
                .build();
    }

    @Test
    void getAverageRate() {
        assertEquals(2, movie.getAverageRate());
    }

    @Test
    void isMovieWonBestPicture() {
        assertEquals(true, movie.isMovieWonBestPicture());
        Award award = getAward(false);
        movie.setAward(award);
        assertEquals(false, movie.isMovieWonBestPicture());
    }

    private Award getAward(boolean b) {
        return Award.builder()
                .category("Best Picture")
                .won(b)
                .build();
    }
}