package com.backbase.movie.service.impl;

import com.backbase.movie.constant.ApplicationConstant;
import com.backbase.movie.dto.request.RateRequestDto;
import com.backbase.movie.dto.response.MovieResponseDto;
import com.backbase.movie.entity.Movie;
import com.backbase.movie.exception.MovieObjectNotFoundException;
import com.backbase.movie.model.api.OmdbResponse;
import com.backbase.movie.service.MovieDaoService;
import com.backbase.movie.service.MovieService;
import com.backbase.movie.service.OmdbService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.backbase.movie.util.MovieServiceUtil.*;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final MovieDaoService movieDaoService;
    private final OmdbService omdbService;

    @Override
    public void rateMovie(RateRequestDto rateRequestDto) {
        String title = rateRequestDto.getTitle();
        Movie movie = getMovie(title);
        movie.getRates().add(mapRateDtoToRate(rateRequestDto));
        movieDaoService.updateMovie(movie);
    }


    @Override
    public List<MovieResponseDto> getTopMovies(int topCount) {
        return movieDaoService.getTopRatedMovies(topCount).stream()
                .map(m -> setBoxOffice(m))
                .sorted(Comparator.comparing(Movie::getBoxOffice).reversed())
                .map(i -> mapMovieToResponse(i))
                .collect(Collectors.toList());
    }


    @Override
    public boolean isWonBestPicture(String title) {
        Movie movie = getMovie(title);
        return movie.isMovieWonBestPicture();
    }

    private Movie getMovie(String title) {
        Optional<Movie> movie = movieDaoService.getMovieByTitle(title);
        // If movie not found in DB, get it from API and save it.
        if (!movie.isPresent()) {
            movie = Optional.of(getMovieFromApi(title));
            movieDaoService.createMovie(movie.get());
        }
        return movie.get();
    }

    private Movie getMovieFromApi(String title) {
        //find movie and save it into the DB
        OmdbResponse omdbResponse = omdbService.getMovieDetails(title);
        if (omdbResponse.getResponse().equals(ApplicationConstant.FALSE))
            throw new MovieObjectNotFoundException("Movie not found ! Title :" + title);
        return mapOmdbResponseToMovie(omdbResponse);
    }

    private Movie setBoxOffice(Movie movie) {

        movie.setBoxOffice(getMovieFromApi(movie.getTitle()).getBoxOffice());
        return movie;

    }

}
