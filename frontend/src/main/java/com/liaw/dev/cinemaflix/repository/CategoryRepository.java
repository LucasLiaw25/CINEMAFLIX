package com.liaw.dev.cinemaflix.repository;

import com.liaw.dev.cinemaflix.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long>, QueryByExampleExecutor<Category> {
    Optional<Category> findByName(String name);
}
