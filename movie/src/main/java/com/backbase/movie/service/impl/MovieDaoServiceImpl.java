package com.backbase.movie.service.impl;

import com.backbase.movie.entity.Movie;
import com.backbase.movie.repository.MovieRepository;
import com.backbase.movie.service.MovieDaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class MovieDaoServiceImpl implements MovieDaoService {
    private final MovieRepository movieRepository;

    @Override
    public Movie createMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    @Override
    @CachePut(value = "movie", key = "#movie.title", condition = "#movie.id!=null")
    public Movie updateMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    @Override
    @Cacheable(value = "movie", key = "#title", unless = "#result  == null")
    public Optional<Movie> getMovieByTitle(String title) {
        return movieRepository.findByTitle(title);
    }

    @Override
    public long getRecordCount() {
        return movieRepository.count();
    }

    @Override
    public List<Movie> getTopRatedMovies(int topCount) {
        return movieRepository.findAll().stream()
                .filter(m -> !m.getRates().isEmpty())
                .sorted(Comparator.comparingDouble(Movie::getAverageRate).reversed())
                .limit(topCount)
                .collect(Collectors.toList());

    }
}
