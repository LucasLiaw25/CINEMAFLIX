package com.liaw.dev.cinemaflix.repository;

import com.liaw.dev.cinemaflix.entity.Actor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActorRepositorty extends JpaRepository<Actor, Long> {
}
