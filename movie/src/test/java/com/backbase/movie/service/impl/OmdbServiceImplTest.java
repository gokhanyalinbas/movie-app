package com.backbase.movie.service.impl;

import com.backbase.movie.exception.OmdbAPiCallException;
import com.backbase.movie.model.api.OmdbResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OmdbServiceImplTest {
    @Mock
    private RestTemplate restTemplate;
    private OmdbServiceImpl omdbService;
    private String validUrl;
    private OmdbResponse result;

    @BeforeEach
    void setUp() {
        validUrl = "URL";
        omdbService = new OmdbServiceImpl(restTemplate);
        result = OmdbResponse.builder()
                .additionalInfo("Add")
                .awards("Won")
                .boxOffice("12")
                .response("True")
                .year("2010")
                .title("Title")
                .build();
    }

    @Test
    void getMovieDetails() {
        ResponseEntity<OmdbResponse> responseEntity = new ResponseEntity<>(result, HttpStatus.OK);
        when(restTemplate.getForEntity(any(String.class), eq(OmdbResponse.class), any(HashMap.class))).thenReturn(responseEntity);
        OmdbResponse omdbResponse = omdbService.getMovieDetails("Test");
        assertEquals("True", omdbResponse.getResponse());
    }

    @Test
    void getMovieDetailsWithError() {
        given(restTemplate.getForEntity(any(String.class), eq(OmdbResponse.class), any(HashMap.class))).willAnswer(i -> {
            throw new Exception("Timeout");
        });
        Exception exception = assertThrows(OmdbAPiCallException.class, () -> {
            omdbService.getMovieDetails("Test");
        });
        assertThat(exception).isInstanceOf(OmdbAPiCallException.class);
    }


}