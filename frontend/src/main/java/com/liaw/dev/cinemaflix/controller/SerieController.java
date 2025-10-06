package com.liaw.dev.cinemaflix.controller;

import com.liaw.dev.cinemaflix.dto.SerieDTO;
import com.liaw.dev.cinemaflix.entity.Serie;
import com.liaw.dev.cinemaflix.handleException.handle.Message;
import com.liaw.dev.cinemaflix.service.SerieService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/cineflix/serie/")
public class SerieController {

    private final SerieService service;

    @PostMapping
    public ResponseEntity<SerieDTO> createSerie(@RequestBody SerieDTO dto){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.createSerie(dto));
    }

    @GetMapping
    public ResponseEntity<Page<Serie>> listSerie(
            @RequestParam int page, @RequestParam int items
    ){
        return ResponseEntity.ok(service.listSerie(page, items));
    }

    @GetMapping("all")
    public ResponseEntity<List<SerieDTO>> listAllSerie(){
        return ResponseEntity.ok(service.listAllSerie());
    }

    @GetMapping("top10")
    public ResponseEntity<List<SerieDTO>> top10Series(){
        return ResponseEntity.ok(service.top10Series());
    }

    @PatchMapping("{id}")
    public ResponseEntity<SerieDTO> updateParcialSerie(@PathVariable Long id, @RequestBody SerieDTO dto){
        return ResponseEntity.ok(service.updateParcialSerie(id, dto));
    }

    @GetMapping("category/{id}")
    public ResponseEntity<List<SerieDTO>> findByCategory(@PathVariable Long id){
        return ResponseEntity.ok(service.findByCategory(id));
    }

    @GetMapping("user")
    public ResponseEntity<List<SerieDTO>> findByUser(){
        return ResponseEntity.ok(service.findByUser());
    }

    @GetMapping("user/{id}")
    public ResponseEntity<List<SerieDTO>> findByUserId(@PathVariable Long id){
        return ResponseEntity.ok(service.findByUserId(id));
    }

    @GetMapping("streaming/{id}")
    public ResponseEntity<List<SerieDTO>> findByStreaming(@PathVariable Long id){
        return ResponseEntity.ok(service.findByStreaming(id));
    }

    @GetMapping("search")
    public ResponseEntity<List<SerieDTO>> searchSerie(
            @RequestParam String title
    ){
        return ResponseEntity.ok(service.searchSerie(title));
    }

    @PutMapping("{id}")
    public ResponseEntity<SerieDTO> updateSerie(@PathVariable Long id, @RequestBody SerieDTO dto){
        return ResponseEntity.ok(service.updateSerie(id, dto));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Message> deleteSerie(@PathVariable Long id){
        service.deleteSerie(id);
        return ResponseEntity.ok(
                new Message(
                        HttpStatus.OK.value(),
                        "Série com id:"+id+" deletada com sucesso"
                        )
        );
    }

    @GetMapping("{id}")
    public ResponseEntity<SerieDTO> findById(@PathVariable Long id){
        return ResponseEntity.ok(service.findById(id));
    }

}
