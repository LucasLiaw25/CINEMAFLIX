package com.liaw.dev.cinemaflix.validator;

import com.liaw.dev.cinemaflix.entity.Actor;
import com.liaw.dev.cinemaflix.handleException.actorException.ActorNotFoundException;
import com.liaw.dev.cinemaflix.repository.ActorRepositorty;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ActorValidator {

    private final ActorRepositorty repositorty;

    public void actorIdValidator(Long id){
        Optional<Actor> find_actor = repositorty.findById(id);
        if (find_actor.isEmpty()){
            throw new ActorNotFoundException("Ator com id:"+id+" não encontrado.");
        }
    }

}
