package com.liaw.dev.cinemaflix.handleException.languageException;

public class LanguageNotFoundException extends RuntimeException {
    public LanguageNotFoundException(String message) {
        super(message);
    }
}
