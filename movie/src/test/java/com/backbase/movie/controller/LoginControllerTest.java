package com.backbase.movie.controller;

import com.backbase.movie.config.jwt.JwtTokenProvider;
import com.backbase.movie.constant.ApplicationConstant;
import com.backbase.movie.dto.request.WebUserDto;
import com.backbase.movie.exception.LoginSessionExpiredException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest({LoginController.class})
class LoginControllerTest {

    @MockBean
    private AuthenticationManager authMngr;
    @MockBean
    private JwtTokenProvider jwtTokenProvider;
    private WebUserDto webUserDto;
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        webUserDto = new WebUserDto("user", "pass");
    }

    @Test
    void signin() throws Exception {
        when(jwtTokenProvider.createToken(any(String.class))).thenReturn("token");

        mockMvc.perform(
                MockMvcRequestBuilders.post("/" + ApplicationConstant.LOGIN)
                        .content(new ObjectMapper().writeValueAsString(webUserDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk());
    }

    @Test
    void signinWithInvalidUser() throws Exception {
        when(authMngr.authenticate(any(Authentication.class))).thenThrow(new BadCredentialsException("Invalid credentials"));

        mockMvc.perform(
                MockMvcRequestBuilders.post("/" + ApplicationConstant.LOGIN)
                        .content(new ObjectMapper().writeValueAsString(webUserDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isUnauthorized());
    }

    @Test
    void signinWithDisabledException() throws Exception {
        when(authMngr.authenticate(any(Authentication.class))).thenThrow(new DisabledException("exception"));

        mockMvc.perform(
                MockMvcRequestBuilders.post("/" + ApplicationConstant.LOGIN)
                        .content(new ObjectMapper().writeValueAsString(webUserDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isUnauthorized());
    }

    @Test
    void sessionExpiredException() throws Exception {
        when(authMngr.authenticate(any(Authentication.class))).thenThrow(new LoginSessionExpiredException("exception"));

        mockMvc.perform(
                MockMvcRequestBuilders.post("/" + ApplicationConstant.LOGIN)
                        .content(new ObjectMapper().writeValueAsString(webUserDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isUnauthorized());
    }

}