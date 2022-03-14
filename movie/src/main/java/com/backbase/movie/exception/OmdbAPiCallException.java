package com.backbase.movie.exception;

public class OmdbAPiCallException extends RuntimeException {
    public OmdbAPiCallException(String message) {
        super(message);
    }
}
