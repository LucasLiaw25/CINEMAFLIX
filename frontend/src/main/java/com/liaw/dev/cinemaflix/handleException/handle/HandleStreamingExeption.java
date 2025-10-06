package com.liaw.dev.cinemaflix.handleException.handle;

import com.liaw.dev.cinemaflix.handleException.streamingException.StreamingExistException;
import com.liaw.dev.cinemaflix.handleException.streamingException.StreamingNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class HandleStreamingExeption {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(StreamingNotFoundException.class)
    public Message handleStreamingNotFoundException(StreamingNotFoundException e){
        return new Message(
                HttpStatus.NOT_FOUND.value(),
                e.getMessage()
        );
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(StreamingExistException.class)
    public Message handleStreamingExistException(StreamingExistException e){
        return new Message(
                HttpStatus.CONFLICT.value(),
                e.getMessage()
        );
    }
}
