package com.backbase.movie.service.impl;

import com.backbase.movie.dto.request.RateRequestDto;
import com.backbase.movie.dto.response.MovieResponseDto;
import com.backbase.movie.entity.Movie;
import com.backbase.movie.exception.MovieObjectNotFoundException;
import com.backbase.movie.model.api.OmdbResponse;
import com.backbase.movie.service.MovieDaoService;
import com.backbase.movie.service.OmdbService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.backbase.movie.service.impl.MovieTestUtil.generateMovieByIdAndRate;
import static com.backbase.movie.service.impl.MovieTestUtil.generateMovieList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MovieServiceImplTest {
    @Mock
    private MovieDaoService movieDaoService;
    @Mock
    private OmdbService omdbService;
    private MovieServiceImpl movieService;
    private Movie movie;
    private OmdbResponse omdbResponse;
    private OmdbResponse omdbResponseTest;

    @BeforeEach
    void setUp() {
        movieService = new MovieServiceImpl(movieDaoService, omdbService);
        movie = generateMovieByIdAndRate("1212", 2);
        omdbResponseTest = OmdbResponse.builder()
                .additionalInfo("Test")
                .awards("Won")
                .boxOffice("120")
                .response("True")
                .year("2010")
                .title("Test")
                .build();
        ;
        omdbResponse = OmdbResponse.builder()
                .additionalInfo("Add")
                .awards("Won")
                .boxOffice("N/A")
                .response("True")
                .year("2010")
                .title("Title")
                .build();
    }


    @Test
    void rateMovie() {
        when(movieDaoService.getMovieByTitle(any(String.class))).thenReturn(Optional.of(movie));
        RateRequestDto rateRequestDto = new RateRequestDto();
        rateRequestDto.setTitle("Title");
        rateRequestDto.setUserId("1");
        rateRequestDto.setUserName("User");
        rateRequestDto.setUserRate(10);
        movieService.rateMovie(rateRequestDto);
        verify(movieDaoService, times(1)).updateMovie(any(Movie.class));
    }

    @Test
    void getTopMovies() {
        List<Movie> movies = generateMovieList();
        movies.get(9).setTitle("Test");
        when(movieDaoService.getTopRatedMovies(10)).thenReturn(movies);
        when(omdbService.getMovieDetails("Test")).thenReturn(omdbResponseTest);
        when(omdbService.getMovieDetails("Title")).thenReturn(omdbResponse);
        List<MovieResponseDto> result = movieService.getTopMovies(10);
        assertEquals(10, result.size());
        assertEquals("Test", result.get(0).getTitle());
        verify(movieDaoService, times(1)).getTopRatedMovies(10);

    }

    @Test
    void isWonBestPictureFromDB() {
        when(movieDaoService.getMovieByTitle(any(String.class))).thenReturn(Optional.of(movie));
        assertEquals(true, movieService.isWonBestPicture("Test"));

    }

    @Test
    void isWonBestPictureFromApi() {
        when(movieDaoService.getMovieByTitle(any(String.class))).thenReturn(Optional.empty());
        OmdbResponse omdbResponseWithError = OmdbResponse.builder()
                .response("False")
                .build();
        when(omdbService.getMovieDetails(any(String.class))).thenReturn(omdbResponseWithError);
        Exception exception = assertThrows(MovieObjectNotFoundException.class, () -> {
            movieService.isWonBestPicture("Title");
        });
        assertThat(exception).isInstanceOf(MovieObjectNotFoundException.class);

    }

    @Test
    void isWonBestPictureWithNotFoundMovieObject() {
        when(movieDaoService.getMovieByTitle(any(String.class))).thenReturn(Optional.empty());
        when(omdbService.getMovieDetails(any(String.class))).thenReturn(omdbResponse);
        assertEquals(true, movieService.isWonBestPicture("Test"));

    }


}