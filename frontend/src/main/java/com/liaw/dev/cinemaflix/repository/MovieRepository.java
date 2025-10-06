package com.liaw.dev.cinemaflix.repository;

import com.liaw.dev.cinemaflix.entity.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import java.util.List;
import java.util.Optional;

public interface MovieRepository extends JpaRepository<Movie, Long>, QueryByExampleExecutor<Movie> {
    Optional<Movie> findByTitle(String title);
    Optional<Movie> findByDescription(String description);
    Optional<List<Movie>> findByCategories_id(Long id);
    Optional<List<Movie>> findByStreamings_id(Long id);
    List<Movie> findByTitleContainingIgnoreCase(String title);
    List<Movie> findByTop10True();
    List<Movie> findByUser_Id(Long id);
    Page<Movie> findAll(Pageable pageable);
}
