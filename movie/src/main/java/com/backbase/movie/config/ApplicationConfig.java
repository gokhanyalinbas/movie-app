package com.backbase.movie.config;

import com.backbase.movie.constant.ApplicationConstant;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class ApplicationConfig {

    @Bean
    public RestTemplate getRestTemplate(RestTemplateBuilder restTemplateBuilder) {

        return restTemplateBuilder
                .setConnectTimeout(Duration.ofSeconds(ApplicationConstant.TIME_OUT))
                .setReadTimeout(Duration.ofSeconds(ApplicationConstant.TIME_OUT))
                .build();
    }

}