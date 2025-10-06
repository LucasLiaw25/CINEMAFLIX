package com.liaw.dev.cinemaflix.service;

import com.liaw.dev.cinemaflix.dto.StreamingDTO;
import com.liaw.dev.cinemaflix.entity.Streaming;
import com.liaw.dev.cinemaflix.mapper.StreamingMapper;
import com.liaw.dev.cinemaflix.repository.StreamingRepository;
import com.liaw.dev.cinemaflix.validator.StreamingValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StreamingService {

    private final StreamingMapper mapper;
    private final StreamingValidator validator;
    private final StreamingRepository repository;

    public StreamingDTO createStreaming(StreamingDTO dto){
        Streaming streaming = mapper.toEntity(dto);
        validator.createStreamingValidator(streaming);
        repository.save(streaming);
        return mapper.toDTO(streaming);
    }

    public List<StreamingDTO> listStreaming(){
        List<Streaming> streamings = repository.findAll();
        return streamings.stream().map(mapper::toDTO).toList();
    }

    public StreamingDTO getStreamingId(Long id){
        validator.streamingIdValidator(id);
        Streaming streaming = repository.findById(id).get();
        return mapper.toDTO(streaming);
    }

    public List<StreamingDTO> searchStreaming(Long id, String name){
        Streaming streaming = new Streaming(id, name);

        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreCase()
                .withIgnoreNullValues()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example<Streaming> example = Example.of(streaming, matcher);
        List<Streaming> streamings = repository.findAll(example);

        return streamings.stream().map(mapper::toDTO).toList();
    }

    public StreamingDTO updateStreaming(Long id, StreamingDTO dto){
        validator.streamingIdValidator(id);
        Streaming streaming = mapper.toEntity(dto);
        streaming.setId(id);
        repository.save(streaming);
        return mapper.toDTO(streaming);
    }

    public void deleteStreaming(Long id){
        validator.streamingIdValidator(id);
        repository.deleteById(id);
    }

}
