package com.liaw.dev.cinemaflix.handleException.handle;

import com.liaw.dev.cinemaflix.handleException.profileException.ProfileNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class HandleProfileException {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ProfileNotFoundException.class)
    public Message handleProfileNotFoundException(ProfileNotFoundException e){
        return new Message(
                HttpStatus.NOT_FOUND.value(),
                e.getMessage()
        );
    }
}
