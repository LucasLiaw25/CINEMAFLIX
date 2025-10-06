package com.liaw.dev.cinemaflix.config;

import com.liaw.dev.cinemaflix.dto.JwtUserData;
import com.liaw.dev.cinemaflix.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {

    private final TokenService service;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();

        if (path.startsWith("/api/user/")&&request.getMethod().equals("POST")){
            return true;
        }

        return false;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String header = request.getHeader("Authorization");

        if (Strings.isNotEmpty(header) && header.startsWith("Bearer ")){
            String token = header.substring("Bearer ".length()).trim();
            Optional<JwtUserData> jwtUserData = service.verifyToken(token);

            if (jwtUserData.isPresent()){
                UsernamePasswordAuthenticationToken userAndPass = new UsernamePasswordAuthenticationToken(
                        jwtUserData.get(), null, Collections.emptyList()
                );
                SecurityContextHolder.getContext().setAuthentication(userAndPass);
            }
            filterChain.doFilter(request, response);
        }else {
            filterChain.doFilter(request, response);
        }


    }
}

