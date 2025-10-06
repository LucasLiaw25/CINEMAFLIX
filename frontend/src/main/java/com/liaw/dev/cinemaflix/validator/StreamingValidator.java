package com.liaw.dev.cinemaflix.validator;

import com.liaw.dev.cinemaflix.entity.Streaming;
import com.liaw.dev.cinemaflix.handleException.streamingException.StreamingExistException;
import com.liaw.dev.cinemaflix.handleException.streamingException.StreamingNotFoundException;
import com.liaw.dev.cinemaflix.repository.StreamingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class StreamingValidator {

    private final StreamingRepository repository;

    public void streamingIdValidator(Long id){
        Optional<Streaming> streaming = repository.findById(id);
        if (streaming.isEmpty()){
            throw new StreamingNotFoundException("Streaming com id: "+id+" não encontrado");
        }
    }

    public void createStreamingValidator(Streaming streaming){
        Optional<Streaming> streaming_find = repository.findByName(streaming.getName());
        if (streaming_find.isPresent()){
            throw new StreamingExistException("Streaming: "+streaming.getName()+" já está cadastrado.");
        }
    }

}
