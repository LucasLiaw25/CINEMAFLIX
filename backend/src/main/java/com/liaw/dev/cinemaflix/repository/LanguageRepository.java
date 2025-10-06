package com.liaw.dev.cinemaflix.repository;

import com.liaw.dev.cinemaflix.entity.Language;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LanguageRepository extends JpaRepository<Language, Long> {
}
