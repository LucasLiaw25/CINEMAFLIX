package com.liaw.dev.cinemaflix.repository;

import com.liaw.dev.cinemaflix.entity.Streaming;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import java.util.Optional;

public interface StreamingRepository extends JpaRepository<Streaming, Long>, QueryByExampleExecutor<Streaming> {
    Optional<Streaming> findByName(String name);
}
