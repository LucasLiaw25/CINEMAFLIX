package com.liaw.dev.cinemaflix.dto;

import com.liaw.dev.cinemaflix.entity.User;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileDTO {
    private Long id;
    private String bio;
    private String name;
    private String username;
    private String location;
    private Integer age;
    private String favoriteMovie;
    private String favoriteSerie;
    private User user;
}
