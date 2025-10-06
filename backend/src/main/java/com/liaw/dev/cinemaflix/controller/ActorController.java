package com.liaw.dev.cinemaflix.controller;

import com.liaw.dev.cinemaflix.dto.ActorDTO;
import com.liaw.dev.cinemaflix.handleException.handle.Message;
import com.liaw.dev.cinemaflix.service.ActorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/cineflix/actor/")
public class ActorController {

    private final ActorService service;

    @PostMapping
    public ResponseEntity<ActorDTO> createActor(@RequestBody ActorDTO dto){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.createActor(dto));
    }

    @GetMapping
    public ResponseEntity<List<ActorDTO>> listActors(){
        return ResponseEntity.ok(service.listActor());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Message> deleteActor(@PathVariable Long id){
        service.deleteActor(id);
        return ResponseEntity.ok(
                new Message(
                        HttpStatus.OK.value(),
                        "Ator com id:"+id+" deletado com sucesso."
                )
        );
    }

}
