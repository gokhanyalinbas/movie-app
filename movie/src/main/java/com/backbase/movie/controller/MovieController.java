package com.backbase.movie.controller;

import com.backbase.movie.constant.ApplicationConstant;
import com.backbase.movie.dto.request.RateRequestDto;
import com.backbase.movie.dto.response.MovieResponseDto;
import com.backbase.movie.service.MovieService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(ApplicationConstant.REQUEST_MAPPING)
@RequiredArgsConstructor
@EnableCaching
public class MovieController {

    private final MovieService movieService;

    @PostMapping(ApplicationConstant.RATE)
    @ApiOperation("Rate movie by title")
    public ResponseEntity rateMovie(@Valid @RequestBody final RateRequestDto rateRequestDto) {
        movieService.rateMovie(rateRequestDto);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping(ApplicationConstant.AWARD)
    @ApiOperation("Is movie won oscar ?")
    public ResponseEntity<Boolean> isMovieWonOscar(@RequestParam final String title) {
        return new ResponseEntity<>(movieService.isWonBestPicture(title), HttpStatus.OK);
    }

    @GetMapping(ApplicationConstant.TOP_RATED)
    @ApiOperation("Get top rated movies ordered by boxOffice")
    public ResponseEntity<List<MovieResponseDto>> getTopRatedMovies() {
        return new ResponseEntity<>(movieService.getTopMovies(ApplicationConstant.TOP_COUNT), HttpStatus.OK);
    }
}
