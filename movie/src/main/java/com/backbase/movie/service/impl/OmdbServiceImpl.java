package com.backbase.movie.service.impl;


import com.backbase.movie.constant.ApplicationConstant;
import com.backbase.movie.exception.OmdbAPiCallException;
import com.backbase.movie.model.api.OmdbResponse;
import com.backbase.movie.service.OmdbService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class OmdbServiceImpl implements OmdbService {
    private final RestTemplate restTemplate;

    @Override
    public OmdbResponse getMovieDetails(String title) {
        OmdbResponse response;
        try {
            String URL = UriComponentsBuilder.fromHttpUrl(ApplicationConstant.API_URL)
                    .queryParam("t", "{t}")
                    .queryParam("apikey", "{apikey}")
                    .encode()
                    .toUriString();
            Map<String, String> params = new HashMap<>();
            params.put("t", title);
            params.put("apikey", ApplicationConstant.APIKEY);
            response = restTemplate.getForEntity(URL, OmdbResponse.class, params).getBody();
        } catch (Exception exception) {
            throw new OmdbAPiCallException(exception.getMessage());
        }
        return response;
    }
}
