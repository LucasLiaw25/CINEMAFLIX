package com.liaw.dev.cinemaflix.handleException.streamingException;

public class StreamingNotFoundException extends RuntimeException {
    public StreamingNotFoundException(String message) {
        super(message);
    }
}
