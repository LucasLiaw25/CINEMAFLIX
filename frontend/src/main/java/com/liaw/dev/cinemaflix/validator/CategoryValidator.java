package com.liaw.dev.cinemaflix.validator;

import com.liaw.dev.cinemaflix.entity.Category;
import com.liaw.dev.cinemaflix.entity.Movie;
import com.liaw.dev.cinemaflix.handleException.categoryException.CategoryExistException;
import com.liaw.dev.cinemaflix.handleException.categoryException.CategoryNotFoundException;
import com.liaw.dev.cinemaflix.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CategoryValidator {

    private final CategoryRepository repository;

    public void categoryIdValidator(Long id){
        Optional<Category> category = repository.findById(id);
        if (category.isEmpty()){
            throw new CategoryNotFoundException("Categoria com id: "+id+" não encontrada");
        }
    }

    public void createCategoryValidator(Category category){
        Optional<Category> category_find = repository.findByName(category.getName());
        if (category_find.isPresent()){
            throw new CategoryExistException("Categoria: "+category.getName()+" já está cadastrado.");
        }
    }

}
