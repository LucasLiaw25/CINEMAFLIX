package com.liaw.dev.cinemaflix.mapper;

import com.liaw.dev.cinemaflix.dto.CategoryDTO;
import com.liaw.dev.cinemaflix.entity.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public Category toEntity(CategoryDTO dto){
        return new Category(
                dto.getId(),
                dto.getName()
        );
    }

    public CategoryDTO toDTO(Category category){
        return new CategoryDTO(
                category.getId(),
                category.getName()
        );
    }

}
