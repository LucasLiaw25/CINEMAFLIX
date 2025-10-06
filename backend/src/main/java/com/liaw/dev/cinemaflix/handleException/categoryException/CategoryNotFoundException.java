package com.liaw.dev.cinemaflix.handleException.categoryException;

public class CategoryNotFoundException extends RuntimeException {
    public CategoryNotFoundException(String message) {
        super(message);
    }
}
