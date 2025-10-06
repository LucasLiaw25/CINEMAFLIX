package com.liaw.dev.cinemaflix.handleException.handle;

import com.liaw.dev.cinemaflix.handleException.languageException.LanguageNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class HandleLanguageException {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(LanguageNotFoundException.class)
    public Message handleLanguageNotFoundException(LanguageNotFoundException e){
        return new Message(
                HttpStatus.NOT_FOUND.value(),
                e.getMessage()
        );
    }
}
