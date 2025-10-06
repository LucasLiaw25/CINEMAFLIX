package com.liaw.dev.cinemaflix.handleException.streamingException;

public class StreamingExistException extends RuntimeException {
    public StreamingExistException(String message) {
        super(message);
    }
}
