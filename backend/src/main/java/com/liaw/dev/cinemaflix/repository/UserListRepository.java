package com.liaw.dev.cinemaflix.repository;

import com.liaw.dev.cinemaflix.entity.Movie;
import com.liaw.dev.cinemaflix.entity.Serie;
import com.liaw.dev.cinemaflix.entity.User;
import com.liaw.dev.cinemaflix.entity.UserList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserListRepository extends JpaRepository<UserList, Long> {
    List<UserList> findByUser(User user);
    List<UserList> findByUser_id(Long userId);
    Optional<UserList> findByUserAndMovie(User user, Movie movie);
    Optional<UserList> findByUserAndSerie(User user, Serie serie);
    void deleteByUserAndMovie(User user, Movie movie);
    void deleteByUserAndSerie(User user, Serie serie);
}
