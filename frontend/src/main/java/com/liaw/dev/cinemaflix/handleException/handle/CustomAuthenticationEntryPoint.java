package com.liaw.dev.cinemaflix.handleException.handle;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {

        HttpStatus status = HttpStatus.UNAUTHORIZED;
        String message = "Falha na autenticação";

        if (authException instanceof BadCredentialsException){
            status = HttpStatus.CONFLICT;
            message = "Usuário ou senha inválidos";
        }
        else if (authException.getCause()!=null){
            String causeName = authException.getCause().getClass().getSimpleName();
            if (causeName.contains("JWT")||causeName.contains("token")||causeName.contains("Signature")){
                status = HttpStatus.UNAUTHORIZED;
                message = "Token inválido";
            }
        }

        response.setStatus(status.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        Message response_message = new Message(status.value(), message);

        objectMapper.writeValue(response.getWriter(), response_message);

    }
}