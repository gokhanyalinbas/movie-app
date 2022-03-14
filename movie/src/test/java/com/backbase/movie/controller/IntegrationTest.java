package com.backbase.movie.controller;

import com.backbase.movie.MovieAppApplication;
import com.backbase.movie.constant.ApplicationConstant;
import com.backbase.movie.dto.request.RateRequestDto;
import com.backbase.movie.dto.request.WebUserDto;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = MovieAppApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class IntegrationTest {

    private final String host = "http://localhost:";
    String token = "";
    @Value("${login.username}")
    String user;
    @Value("${login.password}")
    String password;
    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeEach
    void setToken() throws JSONException {
        WebUserDto webUserDto = new WebUserDto(user, password);
        ResponseEntity<String> responseEntity = this.restTemplate
                .postForEntity(host + port + "/login", webUserDto, String.class);
        token = "Bearer " + new JSONObject(responseEntity.getBody()).getString("token");
    }


    @Test
    void IntegrationTest() {
        RateRequestDto rateRequestDto = new RateRequestDto(5, "1", "Gokhan", "Oz");
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);

        HttpEntity<RateRequestDto> request = new HttpEntity<>(rateRequestDto, headers);
        ResponseEntity responseEntity = this.restTemplate
                .postForEntity(host + port + "/" + ApplicationConstant.REQUEST_MAPPING + ApplicationConstant.RATE, request, RateRequestDto.class);
        assertEquals(200, responseEntity.getStatusCodeValue());

    }

}
