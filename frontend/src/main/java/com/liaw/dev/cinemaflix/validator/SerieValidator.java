package com.liaw.dev.cinemaflix.validator;

import com.liaw.dev.cinemaflix.entity.Serie;
import com.liaw.dev.cinemaflix.handleException.serieExcaption.SerieExistException;
import com.liaw.dev.cinemaflix.handleException.serieExcaption.SerieNotFoundException;
import com.liaw.dev.cinemaflix.handleException.serieExcaption.SerieTop10Exception;
import com.liaw.dev.cinemaflix.repository.SerieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SerieValidator {

    private final SerieRepository repository;

    public void serieIdValidator(Long id){
        Optional<Serie> find_serie = repository.findById(id);
        if (find_serie.isEmpty()){
            throw new SerieNotFoundException("Série com id:"+id+" não encontrada");
        }
    }

    public void createSerieValidator(Serie serie){
        Optional<Serie> find_serie = repository.findByTitle(serie.getTitle());
        if (find_serie.isPresent()){
            throw new SerieExistException("Série com título:"+serie.getTitle()+" já cadastrada");
        }
    }

    public void top10Validator(){
        List<Serie> series = repository.findByTop10True();

    }

}
