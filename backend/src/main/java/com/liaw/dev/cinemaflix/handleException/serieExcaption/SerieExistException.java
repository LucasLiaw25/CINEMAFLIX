package com.liaw.dev.cinemaflix.handleException.serieExcaption;

public class SerieExistException extends RuntimeException {
    public SerieExistException(String message) {
        super(message);
    }
}
