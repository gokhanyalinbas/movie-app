package com.backbase.movie.exception.handler;

import com.backbase.movie.exception.ExceptionResponse;
import com.backbase.movie.exception.LoginSessionExpiredException;
import com.backbase.movie.exception.MovieObjectNotFoundException;
import com.backbase.movie.exception.OmdbAPiCallException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.naming.AuthenticationException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

@RestControllerAdvice
public class MovieExceptionHandler {

    @ExceptionHandler(MovieObjectNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public final ExceptionResponse handleMovieNotFoundException(Exception ex) {
        return new ExceptionResponse("NOT_FOUND", ex.getMessage());
    }


    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public final ExceptionResponse handleAuthenticationException(Exception ex) {
        return new ExceptionResponse("LOGIN_ERROR", ex.getMessage());
    }

    @ExceptionHandler(LoginSessionExpiredException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public final ExceptionResponse handleLoginSessionExpiredException(Exception ex) {
        return new ExceptionResponse("SESSION_EXPIRED", ex.getMessage());
    }

    @ExceptionHandler(OmdbAPiCallException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionResponse handleOmdbAPiCallException(Exception ex) {
        return new ExceptionResponse("INTERNAL_SERVER_ERROR", ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionResponse handleAllOtherExceptions(Exception ex) {
        return new ExceptionResponse("INTERNAL_SERVER_ERROR", ex.getMessage());
    }


    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handleConstraintViolationException(ConstraintViolationException ex) {
        String violations = ex.getConstraintViolations().stream().map(ConstraintViolation::getMessage)
                .collect(Collectors.joining("|"));
        return new ExceptionResponse("validation violation", violations);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        String violations = ex.getBindingResult().getAllErrors().stream().map(ObjectError::getDefaultMessage)
                .collect(Collectors.joining("|"));
        return new ExceptionResponse("validation violation", violations);
    }
}
