package com.liaw.dev.cinemaflix.service;

import com.liaw.dev.cinemaflix.dto.ActorDTO;
import com.liaw.dev.cinemaflix.entity.Actor;
import com.liaw.dev.cinemaflix.mapper.ActorMapper;
import com.liaw.dev.cinemaflix.repository.ActorRepositorty;
import com.liaw.dev.cinemaflix.validator.ActorValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ActorService {

    private final ActorMapper mapper;
    private final ActorValidator validator;
    private final ActorRepositorty repositorty;

    public ActorDTO createActor(ActorDTO dto){
        Actor actor = mapper.toEntity(dto);
        repositorty.save(actor);
        return mapper.toDTO(actor);
    }

    public List<ActorDTO> listActor(){
        List<Actor> actors = repositorty.findAll();
        return actors.stream().map(mapper::toDTO).toList();
    }

    public void deleteActor(Long id){
        validator.actorIdValidator(id);
        repositorty.deleteById(id);
    }

}
