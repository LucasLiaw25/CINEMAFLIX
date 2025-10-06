package com.liaw.dev.cinemaflix.handleException.handle;

import com.liaw.dev.cinemaflix.handleException.movieException.MovieExistException;
import com.liaw.dev.cinemaflix.handleException.movieException.MovieNotFoundException;
import com.liaw.dev.cinemaflix.handleException.movieException.MovieTop10Exception;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class HandleMovieException {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(MovieNotFoundException.class)
    public Message handleMovieNotFoundException(MovieNotFoundException e){
        return new Message(
                HttpStatus.NOT_FOUND.value(),
                e.getMessage()
        );
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(MovieExistException.class)
    public Message handleMovieExistException(MovieExistException e){
        return new Message(
                HttpStatus.CONFLICT.value(),
                e.getMessage()
        );
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(MovieTop10Exception.class)
    public Message handleMovieTop10Exception(MovieTop10Exception e){
        return new Message(
                HttpStatus.CONFLICT.value(),
                e.getMessage()
        );
    }
}
