package com.liaw.dev.cinemaflix.mapper;

import com.liaw.dev.cinemaflix.dto.StreamingDTO;
import com.liaw.dev.cinemaflix.entity.Streaming;
import org.springframework.stereotype.Component;

@Component
public class StreamingMapper {

    public Streaming toEntity(StreamingDTO dto){
        return new Streaming(
                dto.getId(),
                dto.getName()
        );
    }

    public StreamingDTO toDTO(Streaming streaming){
        return new StreamingDTO(
                streaming.getId(),
                streaming.getName()
        );
    }

}
