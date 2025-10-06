package com.liaw.dev.cinemaflix.handleException.handle;

import com.liaw.dev.cinemaflix.handleException.categoryException.CategoryExistException;
import com.liaw.dev.cinemaflix.handleException.categoryException.CategoryNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class HandleCategoryException {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(CategoryNotFoundException.class)
    public Message handleCategoryNotFoundException(CategoryNotFoundException e){
        return new Message(
                HttpStatus.NOT_FOUND.value(),
                e.getMessage()
        );
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(CategoryExistException.class)
    public Message handleCategoryExistException(CategoryExistException e){
        return new Message(
                HttpStatus.CONFLICT.value(),
                e.getMessage()
        );
    }

}
