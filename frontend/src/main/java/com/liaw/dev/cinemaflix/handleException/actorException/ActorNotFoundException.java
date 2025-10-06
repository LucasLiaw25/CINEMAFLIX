package com.liaw.dev.cinemaflix.handleException.actorException;

public class ActorNotFoundException extends RuntimeException {
    public ActorNotFoundException(String message) {
        super(message);
    }
}
