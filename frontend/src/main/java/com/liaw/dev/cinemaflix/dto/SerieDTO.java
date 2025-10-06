package com.liaw.dev.cinemaflix.dto;

import com.liaw.dev.cinemaflix.entity.*;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SerieDTO {
    private Long id;
    private String title;
    private String description;
    private String synopsis;
    private Integer rating;
    private Integer age_rating;
    private LocalDate publication_year;
    private Integer episode;
    private Integer season;
    private Boolean top10 = false;
    private List<Category> categories;
    private List<Streaming> streamings;
    private List<Actor> actors;
    private List<Language> languages;
    private User user;
}
