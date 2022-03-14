package com.backbase.movie.controller;

import com.backbase.movie.constant.ApplicationConstant;
import com.backbase.movie.dto.request.RateRequestDto;
import com.backbase.movie.dto.response.MovieResponseDto;
import com.backbase.movie.exception.MovieObjectNotFoundException;
import com.backbase.movie.exception.OmdbAPiCallException;
import com.backbase.movie.service.MovieService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest({MovieController.class})
class MovieControllerTest {

    @MockBean
    private MovieService movieService;
    @Autowired
    private MockMvc mockMvc;

    private RateRequestDto rateRequestDto;

    @BeforeEach
    void setUp() {
        rateRequestDto = new RateRequestDto(5, "1", "Gokhan", "Oz");
    }

    @Test
    @WithMockUser("user")
    void rateMovie() throws Exception {

        doNothing().when(movieService).rateMovie(any(RateRequestDto.class));

        mockMvc.perform(
                MockMvcRequestBuilders.post("/" + ApplicationConstant.REQUEST_MAPPING + ApplicationConstant.RATE)
                        .content(new ObjectMapper().writeValueAsString(rateRequestDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk());

    }

    @Test
    @WithMockUser("user")
    void rateMovieWithInvalidRate() throws Exception {

        rateRequestDto.setUserRate(11);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/" + ApplicationConstant.REQUEST_MAPPING + ApplicationConstant.RATE)
                        .content(new ObjectMapper().writeValueAsString(rateRequestDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isBadRequest());

    }

    @Test
    @WithMockUser("user")
    void rateMovieWithInvalidTitle() throws Exception {

        rateRequestDto.setTitle(null);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/" + ApplicationConstant.REQUEST_MAPPING + ApplicationConstant.RATE)
                        .content(new ObjectMapper().writeValueAsString(rateRequestDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isBadRequest());

    }

    @Test
    @WithMockUser("user")
    void isMovieWonOscar() throws Exception {

        when(movieService.isWonBestPicture(any(String.class))).thenReturn(true);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/" + ApplicationConstant.REQUEST_MAPPING + ApplicationConstant.AWARD)
                        .param("title", "Oz")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(content().string("true"));

    }

    @Test
    @WithMockUser("user")
    void NotFoundMovieInDb() throws Exception {

        when(movieService.isWonBestPicture(any(String.class))).thenThrow(new MovieObjectNotFoundException("Not Found"));
        mockMvc.perform(
                MockMvcRequestBuilders.get("/" + ApplicationConstant.REQUEST_MAPPING + ApplicationConstant.AWARD)
                        .param("title", "Oz")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").exists())
                .andExpect(jsonPath("$.reason").value("Not Found"));

    }

    @Test
    @WithMockUser("user")
    void omdbApiException() throws Exception {

        when(movieService.isWonBestPicture(any(String.class))).thenThrow(new OmdbAPiCallException("Api Error"));
        mockMvc.perform(
                MockMvcRequestBuilders.get("/" + ApplicationConstant.REQUEST_MAPPING + ApplicationConstant.AWARD)
                        .param("title", "Oz")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.status").exists())
                .andExpect(jsonPath("$.reason").value("Api Error"));

    }

    @Test
    @WithMockUser("user")
    void anyOtherExceptions() throws Exception {

        when(movieService.isWonBestPicture(any(String.class))).thenThrow(new NumberFormatException("Application Error"));
        mockMvc.perform(
                MockMvcRequestBuilders.get("/" + ApplicationConstant.REQUEST_MAPPING + ApplicationConstant.AWARD)
                        .param("title", "Oz")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.status").exists())
                .andExpect(jsonPath("$.reason").value("Application Error"));


    }

    @Test
    @WithMockUser("user")
    void constraintViolationException() throws Exception {

        ConstraintViolationException violationException = mock(ConstraintViolationException.class);
        ConstraintViolationException exception = mock(ConstraintViolationException.class);
        when(exception.getMessage()).thenReturn("violationException");
        when(violationException.getCause()).thenReturn(exception);
        when(movieService.isWonBestPicture(any(String.class))).thenThrow(violationException);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/" + ApplicationConstant.REQUEST_MAPPING + ApplicationConstant.AWARD)
                        .param("title", "Oz")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.reason").exists());

    }


    @Test
    @WithMockUser("user")
    void getTopRatedMovies() throws Exception {

        MovieResponseDto movieResponseDto = MovieResponseDto.builder()
                .year("2010")
                .title("Title")
                .rate(5)
                .id("id00")
                .additionalInfo("Test")
                .boxOffice("120.00")
                .build();
        List<MovieResponseDto> movieList = new ArrayList<>();
        movieList.add(movieResponseDto);
        movieList.add(movieResponseDto);
        movieList.add(movieResponseDto);
        movieList.add(movieResponseDto);
        movieList.add(movieResponseDto);
        movieList.add(movieResponseDto);
        movieList.add(movieResponseDto);
        movieList.add(movieResponseDto);
        movieList.add(movieResponseDto);
        movieList.add(movieResponseDto);

        when(movieService.getTopMovies(any(Integer.class))).thenReturn(movieList);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/" + ApplicationConstant.REQUEST_MAPPING + ApplicationConstant.TOP_RATED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(content().string(new ObjectMapper().writeValueAsString(movieList)));
    }
}