package com.liaw.dev.cinemaflix.controller;

import com.liaw.dev.cinemaflix.dto.StreamingDTO;
import com.liaw.dev.cinemaflix.handleException.handle.Message;
import com.liaw.dev.cinemaflix.service.StreamingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/cineflix/streaming/")
public class StreamingController {

    private final StreamingService service;

    @PostMapping
    public ResponseEntity<StreamingDTO> createStreaming(@RequestBody @Valid StreamingDTO dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(
                service.createStreaming(dto)
        );
    }

    @GetMapping
    public ResponseEntity<List<StreamingDTO>> listStreaming(){
        return ResponseEntity.ok(service.listStreaming());
    }

    @GetMapping("{id}")
    public ResponseEntity<StreamingDTO> getStreamingId(@PathVariable Long id){
        return ResponseEntity.ok(service.getStreamingId(id));
    }

    @GetMapping("search")
    public ResponseEntity<List<StreamingDTO>> searchStreaming(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String name
    ){
        return ResponseEntity.ok(
                service.searchStreaming(id, name)
        );
    }

    @PutMapping("{id}")
    public ResponseEntity<StreamingDTO> updateStreaming(@PathVariable Long id, @RequestBody StreamingDTO dto){
        return ResponseEntity.ok(
                service.updateStreaming(id, dto)
        );
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Message> deleteStreaming(@PathVariable Long id){
        return ResponseEntity.ok(
                new Message(200, "Streaming com id: "+id+" deletada com sucesso.")
        );
    }

}
