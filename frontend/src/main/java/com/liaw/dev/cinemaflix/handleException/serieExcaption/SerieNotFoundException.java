package com.liaw.dev.cinemaflix.handleException.serieExcaption;

public class SerieNotFoundException extends RuntimeException {
    public SerieNotFoundException(String message) {
        super(message);
    }
}
