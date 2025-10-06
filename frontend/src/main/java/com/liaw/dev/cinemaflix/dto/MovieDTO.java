package com.liaw.dev.cinemaflix.dto;

import com.liaw.dev.cinemaflix.entity.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieDTO {
    private Long id;

    @NotBlank(message = "Título não pode estar vazio")
    private String title;

    @NotBlank(message = "Descrição não pode estar vazia")
    private String description;
    private String synopsis;
    private Integer duration;
    private Integer rating;
    private Integer age_rating;
    private LocalDate publication_year;
    private Boolean top10 = false;
    private List<Category> categories;
    private List<Streaming> streamings;
    private List<Actor> actors;
    private List<Language> languages;
    private User user;
}
