package com.liaw.dev.cinemaflix.controller;

import com.liaw.dev.cinemaflix.dto.UserListDTO;
import com.liaw.dev.cinemaflix.dto.UserListResponse;
import com.liaw.dev.cinemaflix.service.UserListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/cineflix/user-list/")
public class UserListController {

    private final UserListService service;

    @PostMapping("movie/{movieId}")
    public void addMovie(@PathVariable Long movieId){
        service.addMovieToList(movieId);
    }

    @PostMapping("serie/{serieId}")
    public void addSerie(@PathVariable Long serieId){
        service.addSerieToList(serieId);
    }

    @DeleteMapping("movie/{movieId}")
    public void removeMovie(@PathVariable Long movieId){
        service.removeMovieToList(movieId);
    }

    @DeleteMapping("serie/{serieId}")
    public void removeSerie(@PathVariable Long serieId){
        service.removeSerieToList(serieId);
    }

    @GetMapping("all")
    public ResponseEntity<List<UserListDTO>> findUserList(){
        return ResponseEntity.ok(service.findUserList());
    }

    @GetMapping
    public ResponseEntity<UserListResponse> findByUserId(){
        return ResponseEntity.ok(service.findByUserId());
    }
}
