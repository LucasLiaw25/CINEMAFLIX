package com.liaw.dev.cinemaflix.handleException.handle;

import com.liaw.dev.cinemaflix.handleException.serieExcaption.SerieExistException;
import com.liaw.dev.cinemaflix.handleException.serieExcaption.SerieNotFoundException;
import com.liaw.dev.cinemaflix.handleException.serieExcaption.SerieTop10Exception;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class HandleSerieException {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(SerieNotFoundException.class)
    public Message handleSerieNotFoundException(SerieNotFoundException e){
        return new Message(
                HttpStatus.NOT_FOUND.value(),
                e.getMessage()
        );
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(SerieExistException.class)
    public Message handleSerieExistException(SerieExistException e){
        return new Message(
                HttpStatus.CONFLICT.value(),
                e.getMessage()
        );
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(SerieTop10Exception.class)
    public Message handleSerieTop10Exception(SerieExistException e){
        return new Message(
                HttpStatus.CONTINUE.value(),
                e.getMessage()
        );
    }

}
