package com.liaw.dev.cinemaflix.controller;

import com.liaw.dev.cinemaflix.dto.CategoryDTO;
import com.liaw.dev.cinemaflix.handleException.handle.Message;
import com.liaw.dev.cinemaflix.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/cineflix/category/")
public class CategoryController {

    private final CategoryService service;

    @PostMapping
    public ResponseEntity<CategoryDTO> createCategory(@RequestBody @Valid CategoryDTO dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(
                service.createCategory(dto)
        );
    }

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> listCategory(){
        return ResponseEntity.ok(service.listCategory());
    }

    @GetMapping("{id}")
    public ResponseEntity<CategoryDTO> getCategoryId(@PathVariable Long id){
        return ResponseEntity.ok(service.getCategoryId(id));
    }

    @GetMapping("search")
    public ResponseEntity<List<CategoryDTO>> searchCategory(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String name
    ){
        return ResponseEntity.ok(service.searchCategory(id, name));
    }

    @PutMapping("{id}")
    public ResponseEntity<CategoryDTO> updateCategory(@PathVariable Long id, @RequestBody CategoryDTO dto){
        return ResponseEntity.ok(
                service.updateCategory(id, dto)
        );
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Message> deleteCategory(@PathVariable Long id){
        service.deleteCategory(id);
        return ResponseEntity.ok(
                new Message(200, "Categoria com id: "+id+" deletada com sucesso.")
        );
    }

}
