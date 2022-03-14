package com.backbase.movie.service.impl;

import com.backbase.movie.entity.Movie;
import com.backbase.movie.repository.MovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.backbase.movie.service.impl.MovieTestUtil.generateMovieByIdAndRate;
import static com.backbase.movie.service.impl.MovieTestUtil.generateMovieList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MovieDaoServiceImplTest {

    @Mock
    private MovieRepository movieRepository;
    private MovieDaoServiceImpl movieDaoService;
    private Movie movie;


    @BeforeEach
    void setUp() {
        movieDaoService = new MovieDaoServiceImpl(movieRepository);
        movie = generateMovieByIdAndRate("1212", 2);
    }


    @Test
    void createMovie() {

        when(movieRepository.save(any(Movie.class))).thenReturn(movie);
        Movie result = movieDaoService.createMovie(movie);
        assertThat(result).isNotNull();
        verify(movieRepository, times(1)).save(any(Movie.class));

    }

    @Test
    void updateMovie() {
        when(movieRepository.save(any(Movie.class))).thenReturn(movie);
        movie.setAdditionalInfo("updated");
        Movie result = movieDaoService.updateMovie(movie);
        assertThat(result).isNotNull();
        assertThat(result.getAdditionalInfo()).isEqualTo("updated");
        verify(movieRepository, times(1)).save(any(Movie.class));

    }

    @Test
    void getMovieByTitle() {
        when(movieRepository.findByTitle(any(String.class))).thenReturn(Optional.of(movie));
        Optional<Movie> result = movieDaoService.getMovieByTitle("Title");
        assertThat(result.get().getId()).isEqualTo("1212");
        assertThat(result.get().getTitle()).isEqualTo("Title");
        verify(movieRepository, times(1)).findByTitle(any(String.class));

    }

    @Test
    void getRecordCount() {
        when(movieRepository.count()).thenReturn(1000L);
        long count = movieDaoService.getRecordCount();
        assertEquals(1000L, count);
        verify(movieRepository, times(1)).count();
    }

    @Test
    void getTopRatedMoviesBySize() {
        List<Movie> movies = generateMovieList();
        when(movieRepository.findAll()).thenReturn(movies);
        List<Movie> result = movieDaoService.getTopRatedMovies(10);
        assertEquals(10, result.size());
        verify(movieRepository, times(1)).findAll();

    }

    @Test
    void getTopRatedMoviesByRateFilter() {
        List<Movie> movies = generateMovieList();
        when(movieRepository.findAll()).thenReturn(movies);
        //removing rate object
        movies.get(0).setRates(new ArrayList<>());
        List<Movie> result = movieDaoService.getTopRatedMovies(10);
        assertEquals(9, result.size());
        verify(movieRepository, times(1)).findAll();

    }

    @Test
    void getTopRatedMoviesSortByRate() {
        List<Movie> movies = generateMovieList();
        when(movieRepository.findAll()).thenReturn(movies);
        //removing rate object
        movies.add(generateMovieByIdAndRate("11", 10));
        List<Movie> result = movieDaoService.getTopRatedMovies(10);
        //After sorted by average rate
        assertEquals("11", result.get(0).getId());
        verify(movieRepository, times(1)).findAll();

    }


}