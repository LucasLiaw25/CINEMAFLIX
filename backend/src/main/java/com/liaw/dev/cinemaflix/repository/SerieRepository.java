package com.liaw.dev.cinemaflix.repository;

import com.liaw.dev.cinemaflix.entity.Serie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import java.util.List;
import java.util.Optional;

public interface SerieRepository extends JpaRepository<Serie, Long>, QueryByExampleExecutor<Serie> {
    Optional<Serie> findByTitle(String title);
    List<Serie> findByTitleContainingIgnoreCase(String title);
    List<Serie> findByTop10True();
    Optional<List<Serie>> findByCategories_id(Long id);
    Optional<List<Serie>> findByStreamings_id(Long id);
    List<Serie> findByUser_Id(Long id);
    Page<Serie> findAll(Pageable pageable);
}
