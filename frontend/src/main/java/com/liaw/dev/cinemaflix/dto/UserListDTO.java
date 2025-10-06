package com.liaw.dev.cinemaflix.dto;

import com.liaw.dev.cinemaflix.entity.Movie;
import com.liaw.dev.cinemaflix.entity.Serie;
import com.liaw.dev.cinemaflix.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserListDTO {
    private Long id;
    private User user;
    private Movie movie;
    private Serie serie;
    private LocalDateTime addedAt = LocalDateTime.now();
}
