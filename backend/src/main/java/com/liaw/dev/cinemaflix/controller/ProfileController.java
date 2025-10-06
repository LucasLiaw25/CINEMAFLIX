package com.liaw.dev.cinemaflix.controller;

import com.liaw.dev.cinemaflix.dto.ProfileDTO;
import com.liaw.dev.cinemaflix.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/cineflix/profile/")
public class ProfileController {

    private final ProfileService service;

    @PatchMapping
    public ResponseEntity<ProfileDTO> updateProfile(@RequestBody ProfileDTO dto){
        return ResponseEntity.ok(service.updateProfile(dto));
    }

    @GetMapping
    public ResponseEntity<ProfileDTO> getProfile(){
        return ResponseEntity.ok(service.getProfile());
    }

    @GetMapping("{id}")
    public ResponseEntity<ProfileDTO> findById(@PathVariable Long id){
        return ResponseEntity.ok(service.findById(id));
    }

}
