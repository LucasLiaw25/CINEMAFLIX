package com.liaw.dev.cinemaflix.validator;

import com.liaw.dev.cinemaflix.entity.Language;
import com.liaw.dev.cinemaflix.handleException.languageException.LanguageNotFoundException;
import com.liaw.dev.cinemaflix.repository.LanguageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class LanguageValidator {

    private final LanguageRepository repository;

    public void languageIdValidator(Long id){
        Optional<Language> find_language = repository.findById(id);
        if (find_language.isEmpty()){
            throw new LanguageNotFoundException("Língua com id:"+id+" não encontrada.");
        }
    }

}
