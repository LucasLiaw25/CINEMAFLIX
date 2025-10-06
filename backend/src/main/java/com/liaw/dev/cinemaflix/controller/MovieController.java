package com.liaw.dev.cinemaflix.controller;

import com.liaw.dev.cinemaflix.dto.MovieDTO;
import com.liaw.dev.cinemaflix.dto.SerieDTO;
import com.liaw.dev.cinemaflix.entity.Category;
import com.liaw.dev.cinemaflix.entity.Movie;
import com.liaw.dev.cinemaflix.entity.Streaming;
import com.liaw.dev.cinemaflix.handleException.handle.Message;
import com.liaw.dev.cinemaflix.repository.MovieRepository;
import com.liaw.dev.cinemaflix.service.MovieService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/cineflix/movie/")
public class MovieController {

    private final MovieRepository repository;
    private final MovieService service;

    @PostMapping
    public ResponseEntity<MovieDTO> createMovie(@RequestBody @Valid MovieDTO dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(
                service.createMovie(dto)
        );
    }

    @PatchMapping("{id}")
    public ResponseEntity<MovieDTO> updateParcialMovie(@PathVariable Long id, @RequestBody MovieDTO dto){
        return ResponseEntity.ok(service.updateParcialMovie(id, dto));
    }

    @GetMapping
    public ResponseEntity<Page<Movie>> listMovie(
            @RequestParam int page, @RequestParam int items
    ){
        return ResponseEntity.ok(service.listMovie(page, items));
    }

    @GetMapping("all")
    public ResponseEntity<List<MovieDTO>> listAllMovie(){
        return ResponseEntity.ok(service.listAllMovie());
    }

    @GetMapping("user")
    public ResponseEntity<List<MovieDTO>> findByUser(){
        return ResponseEntity.ok(service.findByUser());
    }

    @GetMapping("user/{id}")
    public ResponseEntity<List<MovieDTO>> findByUserId(@PathVariable Long id){
        return ResponseEntity.ok(service.findByUserId(id));
    }

    @GetMapping("search")
    public ResponseEntity<List<MovieDTO>> searchMovie(
            @RequestParam String title
    ){
        return ResponseEntity.ok(
                service.searchMovie(title)
        );
    }

    @GetMapping("top10")
    public ResponseEntity<List<MovieDTO>> top10Movies(){
        return ResponseEntity.ok(service.top10Movies());
    }

    @GetMapping("/search-debug")
    public ResponseEntity<?> searchDebug(@RequestParam(required = false) String title) {
        System.out.println("🐛 DEBUG - Buscando título: '" + title + "'");

        // Busca manual no repository
        List<Movie> allMovies = repository.findAll();
        List<Movie> filtered = allMovies.stream()
                .filter(m -> m.getTitle() != null && m.getTitle().toLowerCase().contains(title.toLowerCase()))
                .collect(Collectors.toList());

        System.out.println("🐛 DEBUG - Resultados manuais: " + filtered.size());

        return ResponseEntity.ok(Map.of(
                "paramReceived", title,
                "manualResults", filtered.size(),
                "allMovies", allMovies.stream().map(Movie::getTitle).collect(Collectors.toList())
        ));
    }

    @GetMapping("category/{id}")
    public ResponseEntity<List<MovieDTO>> findByCategory(@PathVariable Long id){
        return ResponseEntity.ok(service.findByCategory(id));
    }



    @GetMapping("streaming/{id}")
    public ResponseEntity<List<MovieDTO>> findByStreaming(@PathVariable Long id){
        return ResponseEntity.ok(service.findByStreaming(id));
    }

    @PutMapping("{id}")
    public ResponseEntity<MovieDTO> updateMovie(@PathVariable Long id, @RequestBody MovieDTO dto){
        return ResponseEntity.ok(service.updateMovie(id, dto));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Message> deleteMovie(@PathVariable Long id){
        return ResponseEntity.ok(
                new Message(200, "Filme com id: "+id+" deletado com sucesso.")
        );
    }

    @PostMapping("{id}/add/top10")
    public ResponseEntity<MovieDTO> addTop10(@PathVariable Long id){
        return ResponseEntity.ok(service.addTop10(id));
    }

    @PostMapping("{id}/remove/top10")
    public ResponseEntity<MovieDTO> removeTop10(@PathVariable Long id){
        return ResponseEntity.ok(service.removeTop10(id));
    }

    @GetMapping("{id}")
    public ResponseEntity<MovieDTO> findById(@PathVariable Long id){
        return ResponseEntity.ok(service.findById(id));
    }

}
