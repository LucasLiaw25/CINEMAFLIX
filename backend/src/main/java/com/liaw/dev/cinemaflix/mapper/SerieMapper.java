package com.liaw.dev.cinemaflix.mapper;

import com.liaw.dev.cinemaflix.dto.SerieDTO;
import com.liaw.dev.cinemaflix.entity.Serie;
import com.liaw.dev.cinemaflix.entity.User;
import org.springframework.stereotype.Component;

@Component
public class SerieMapper {

    public SerieDTO toDTO(Serie serie){
        return new SerieDTO(
                serie.getId(),
                serie.getTitle(),
                serie.getDescription(),
                serie.getSynopsis(),
                serie.getRating(),
                serie.getAge_rating(),
                serie.getPublication_year(),
                serie.getEpisode(),
                serie.getSeason(),
                serie.getTop10(),
                serie.getCategories(),
                serie.getStreamings(),
                serie.getActors(),
                serie.getLanguages(),
                serie.getUser()
        );
    }

    public Serie toEntity(SerieDTO dto){
        return new Serie(
                dto.getId(),
                dto.getTitle(),
                dto.getDescription(),
                dto.getSynopsis(),
                dto.getRating(),
                dto.getAge_rating(),
                dto.getPublication_year(),
                dto.getEpisode(),
                dto.getSeason(),
                dto.getTop10(),
                dto.getCategories(),
                dto.getStreamings(),
                dto.getActors(),
                dto.getLanguages(),
                dto.getUser()
        );
    }

}
