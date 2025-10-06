package com.liaw.dev.cinemaflix.handleException.movieException;

public class MovieExistException extends RuntimeException {
    public MovieExistException(String message) {
        super(message);
    }
}
