package com.backbase.movie.exception;

public class LoginSessionExpiredException extends RuntimeException {
    public LoginSessionExpiredException(String s) {
        super(s);
    }
}
