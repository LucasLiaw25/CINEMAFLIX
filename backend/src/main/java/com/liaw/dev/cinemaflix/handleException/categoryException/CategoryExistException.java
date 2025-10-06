package com.liaw.dev.cinemaflix.handleException.categoryException;

public class CategoryExistException extends RuntimeException {
    public CategoryExistException(String message) {
        super(message);
    }
}
