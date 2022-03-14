package com.backbase.movie.service;

import com.backbase.movie.dto.request.RateRequestDto;
import com.backbase.movie.dto.response.MovieResponseDto;

import java.util.List;

public interface MovieService {
    void rateMovie(RateRequestDto rateRequestDto);

    List<MovieResponseDto> getTopMovies(int topCount);

    boolean isWonBestPicture(String title);

}
