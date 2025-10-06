package com.liaw.dev.cinemaflix.controller;

import com.liaw.dev.cinemaflix.dto.LanguageDTO;
import com.liaw.dev.cinemaflix.handleException.handle.Message;
import com.liaw.dev.cinemaflix.service.LanguageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/cineflix/language/")
public class LanguageController {

    private final LanguageService service;

    @PostMapping
    public ResponseEntity<LanguageDTO> createLanguage(@RequestBody LanguageDTO dto){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.createLanguage(dto));
    }

    @GetMapping
    public ResponseEntity<List<LanguageDTO>> listLanguage(){
        return ResponseEntity.ok(service.listLanguage());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Message> deleteLanguage(@PathVariable Long id){
        service.deleteLanguage(id);
        return ResponseEntity.ok(
                new Message(
                        HttpStatus.CREATED.value(),
                        "Língua com id:"+id+" deletada com sucesso"
                )
        );
    }

}
