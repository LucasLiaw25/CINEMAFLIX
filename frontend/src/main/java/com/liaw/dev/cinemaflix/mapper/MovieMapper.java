package com.liaw.dev.cinemaflix.mapper;

import com.liaw.dev.cinemaflix.dto.MovieDTO;
import com.liaw.dev.cinemaflix.entity.Movie;
import org.springframework.stereotype.Component;

@Component
public class MovieMapper {

    public Movie toEntity(MovieDTO dto){
        return new Movie(
                dto.getId(),
                dto.getTitle(),
                dto.getDescription(),
                dto.getSynopsis(),
                dto.getDuration(),
                dto.getRating(),
                dto.getAge_rating(),
                dto.getPublication_year(),
                dto.getTop10(),
                dto.getCategories(),
                dto.getStreamings(),
                dto.getActors(),
                dto.getLanguages(),
                dto.getUser()
        );
    }

    public MovieDTO toDTO(Movie movie){
        return new MovieDTO(
                movie.getId(),
                movie.getTitle(),
                movie.getDescription(),
                movie.getSynopsis(),
                movie.getDuration(),
                movie.getRating(),
                movie.getAge_rating(),
                movie.getPublication_year(),
                movie.getTop10(),
                movie.getCategories(),
                movie.getStreamings(),
                movie.getActors(),
                movie.getLanguages(),
                movie.getUser()
        );
    }

}
