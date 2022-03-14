package com.backbase.movie.config.jwt;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;


@ExtendWith(MockitoExtension.class)
class JwtTokenProviderTest {


    JwtTokenProvider jwtTokenProvider = new JwtTokenProvider("test", 10);

    @Test
    void createToken() {
        assertNotNull(jwtTokenProvider.createToken("admin"));
    }
}