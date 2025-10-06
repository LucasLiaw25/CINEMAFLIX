package com.liaw.dev.cinemaflix.mapper;

import com.liaw.dev.cinemaflix.dto.ActorDTO;
import com.liaw.dev.cinemaflix.entity.Actor;
import org.springframework.stereotype.Component;

@Component
public class ActorMapper {

    public ActorDTO toDTO(Actor actor){
        return new ActorDTO(
                actor.getId(),
                actor.getName()
        );
    }

    public Actor toEntity(ActorDTO dto){
        return new Actor(
                dto.getId(),
                dto.getName()
        );
    }

}
