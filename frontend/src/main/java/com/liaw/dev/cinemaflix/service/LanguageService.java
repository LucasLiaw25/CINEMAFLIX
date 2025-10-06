package com.liaw.dev.cinemaflix.service;

import com.liaw.dev.cinemaflix.dto.LanguageDTO;
import com.liaw.dev.cinemaflix.entity.Language;
import com.liaw.dev.cinemaflix.mapper.LanguageMapper;
import com.liaw.dev.cinemaflix.repository.LanguageRepository;
import com.liaw.dev.cinemaflix.validator.LanguageValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LanguageService {

    private final LanguageMapper mapper;
    private final LanguageValidator validator;
    private final LanguageRepository repository;

    public LanguageDTO createLanguage(LanguageDTO dto){
        Language language = mapper.toEntity(dto);
        repository.save(language);
        return mapper.toDTO(language);
    }

    public List<LanguageDTO> listLanguage(){
        List<Language> languages = repository.findAll();
        return languages.stream().map(mapper::toDTO).toList();
    }

    public void deleteLanguage(Long id){
        validator.languageIdValidator(id);
        repository.deleteById(id);
    }

}
