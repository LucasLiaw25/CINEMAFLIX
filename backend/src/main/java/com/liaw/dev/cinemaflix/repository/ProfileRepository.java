package com.liaw.dev.cinemaflix.repository;

import com.liaw.dev.cinemaflix.entity.Profile;
import com.liaw.dev.cinemaflix.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
    Optional<Profile> findByUser(User user);
    Optional<Profile> findByUser_id(Long id);
}
