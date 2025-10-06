package com.liaw.dev.cinemaflix.mapper;

import com.liaw.dev.cinemaflix.dto.LanguageDTO;
import com.liaw.dev.cinemaflix.entity.Language;
import org.springframework.stereotype.Component;

@Component
public class LanguageMapper {

    public LanguageDTO toDTO(Language language){
        return new LanguageDTO(
                language.getId(),
                language.getName()
        );
    }

    public Language toEntity(LanguageDTO dto){
        return new Language(
                dto.getId(),
                dto.getName()
        );
    }

}
