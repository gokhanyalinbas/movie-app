package com.backbase.movie.service;

import com.backbase.movie.model.api.OmdbResponse;

public interface OmdbService {

    OmdbResponse getMovieDetails(String title);
}
