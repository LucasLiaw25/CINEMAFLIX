package com.liaw.dev.cinemaflix.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
public class UserListResponse {
    List<MovieDTO> movies;
    List<SerieDTO> series;
}
