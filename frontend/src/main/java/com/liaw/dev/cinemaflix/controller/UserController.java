package com.liaw.dev.cinemaflix.controller;

import com.liaw.dev.cinemaflix.dto.LoginDTO;
import com.liaw.dev.cinemaflix.dto.TokenDTO;
import com.liaw.dev.cinemaflix.dto.UserDTO;
import com.liaw.dev.cinemaflix.dto.UsernameResponse;
import com.liaw.dev.cinemaflix.entity.User;
import com.liaw.dev.cinemaflix.service.TokenService;
import com.liaw.dev.cinemaflix.service.UserListService;
import com.liaw.dev.cinemaflix.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/user/")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;
    private final TokenService tokenService;
    private final UserListService listService;
    private final AuthenticationManager authenticationManager;

    @RequestMapping(value = "validate", method = RequestMethod.HEAD)
    public ResponseEntity<Void> validateToken(){
        return ResponseEntity.ok().build();
    }

    @GetMapping("username")
    public UsernameResponse getUsername(){
        String username = listService.getCurrentUsername();
        return new UsernameResponse(username);
    }

    @PostMapping
    public ResponseEntity<TokenDTO> createUser(@RequestBody UserDTO dto){
        service.createUser(dto);

        UsernamePasswordAuthenticationToken userAndPass = new UsernamePasswordAuthenticationToken(
                dto.getUsername(), dto.getPassword()
        );

        Authentication authentication = authenticationManager.authenticate(userAndPass);
        User user = (User) authentication.getPrincipal();
        String token = tokenService.generateToken(user);
        return ResponseEntity.ok(
                new TokenDTO(token)
        );
    }

    @PostMapping("login")
    public ResponseEntity<TokenDTO> login(@RequestBody LoginDTO dto){
        UsernamePasswordAuthenticationToken userAndPass = new UsernamePasswordAuthenticationToken(
                dto.username(), dto.password()
        );

        Authentication authentication = authenticationManager.authenticate(userAndPass);
        User user = (User) authentication.getPrincipal();
        String token = tokenService.generateToken(user);
        return ResponseEntity.ok(
                new TokenDTO(token)
        );

    }

}
