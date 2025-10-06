package com.liaw.dev.cinemaflix.handleException.handle;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class HandleCommonException {

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(NoResourceFoundException.class)
    public Message handleNoResourceFoundException(NoResourceFoundException e){
        return new Message(
                HttpStatus.CONFLICT.value(),
                "URL: "+e.getResourcePath()+" não existe"
        );
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(MethodArgumentNotValidException.class)
        public Map<String, String> handleValidationExceptions(
                MethodArgumentNotValidException e) {

            Map<String, String> errors = new HashMap<>();

            e.getBindingResult().getAllErrors().forEach(error -> {
                String fieldName = ((FieldError) error).getField();

                String errorMessage = error.getDefaultMessage();

                errors.put(fieldName, errorMessage);
            });
        return errors;
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(UsernameNotFoundException.class)
    public Message handleUsernameNotFounfException(UsernameNotFoundException e){
        return new Message(
                HttpStatus.CONFLICT.value(),
                e.getMessage()
        );
    }


}
