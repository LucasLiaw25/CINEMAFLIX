package com.liaw.dev.cinemaflix.dto;

import com.liaw.dev.cinemaflix.entity.Movie;
import com.liaw.dev.cinemaflix.entity.Profile;
import com.liaw.dev.cinemaflix.entity.Serie;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String name;
    private String email;
    private String username;
    private String password;
    private Profile profile;
    private List<Movie> movies;
    private List<Serie> series;
}
