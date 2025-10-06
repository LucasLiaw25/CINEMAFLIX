package com.liaw.dev.cinemaflix.service;

import com.liaw.dev.cinemaflix.dto.CategoryDTO;
import com.liaw.dev.cinemaflix.entity.Category;
import com.liaw.dev.cinemaflix.mapper.CategoryMapper;
import com.liaw.dev.cinemaflix.repository.CategoryRepository;
import com.liaw.dev.cinemaflix.validator.CategoryValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryMapper mapper;
    private final CategoryValidator validator;
    private final CategoryRepository repository;

    public CategoryDTO createCategory(CategoryDTO dto){
        Category category = mapper.toEntity(dto);
        validator.createCategoryValidator(category);
        repository.save(category);
        return mapper.toDTO(category);
    }

    public List<CategoryDTO> listCategory(){
        List<Category> categories = repository.findAll();
        return categories.stream().map(mapper::toDTO).toList();
    }

    public List<CategoryDTO> searchCategory(Long id, String name){
        Category category = new Category(id, name);

        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreCase()
                .withIgnoreNullValues()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example<Category> example = Example.of(category, matcher);
        List<Category> categories = repository.findAll(example);
        return categories.stream().map(mapper::toDTO).toList();
    }

    public CategoryDTO getCategoryId(Long id){
        validator.categoryIdValidator(id);
        Category category = repository.findById(id).get();
        return mapper.toDTO(category);
    }

    public CategoryDTO updateCategory(Long id, CategoryDTO dto){
        validator.categoryIdValidator(id);
        Category category = mapper.toEntity(dto);
        category.setId(id);
        repository.save(category);
        return mapper.toDTO(category);
    }

    public void deleteCategory(Long id){
        validator.categoryIdValidator(id);
        repository.deleteById(id);
    }

}
