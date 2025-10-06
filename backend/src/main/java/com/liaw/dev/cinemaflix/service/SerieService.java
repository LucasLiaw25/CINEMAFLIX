package com.liaw.dev.cinemaflix.service;

import com.liaw.dev.cinemaflix.dto.MovieDTO;
import com.liaw.dev.cinemaflix.dto.SerieDTO;
import com.liaw.dev.cinemaflix.entity.*;
import com.liaw.dev.cinemaflix.handleException.categoryException.CategoryNotFoundException;
import com.liaw.dev.cinemaflix.handleException.streamingException.StreamingNotFoundException;
import com.liaw.dev.cinemaflix.mapper.SerieMapper;
import com.liaw.dev.cinemaflix.repository.SerieRepository;
import com.liaw.dev.cinemaflix.repository.UserRepository;
import com.liaw.dev.cinemaflix.validator.SerieValidator;
import com.liaw.dev.cinemaflix.validator.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SerieService {

    private final SerieMapper mapper;
    private final SerieValidator validator;
    private final UserValidator userValidator;
    private final UserRepository userRepository;
    private final SerieRepository repository;

    @Transactional
    public SerieDTO createSerie(SerieDTO dto){
        Serie serie = mapper.toEntity(dto);
        validator.createSerieValidator(serie);
        Long id = userValidator.getCurrentId();
        User user = userRepository.findById(id).get();
        serie.setUser(user);
        repository.save(serie);
        return mapper.toDTO(serie);
    }

    public List<SerieDTO> searchSerie(String title){
        List<Serie> series = repository.findByTitleContainingIgnoreCase(title);
        return series.stream().map(mapper::toDTO).toList();
    }

    public SerieDTO findById(Long id){
        validator.serieIdValidator(id);
        Serie serie = repository.findById(id).get();
        return mapper.toDTO(serie);
    }

    public SerieDTO updateParcialSerie(Long id, SerieDTO dto){
        validator.serieIdValidator(id);
        Serie serie = repository.findById(id).get();
        if(dto.getTitle()!=null) serie.setTitle(dto.getTitle());
        if(dto.getDescription()!=null) serie.setDescription(dto.getDescription());
        if(dto.getSynopsis()!=null) serie.setSynopsis(dto.getSynopsis());
        if(dto.getRating()!=null) serie.setRating(dto.getRating());
        if(dto.getAge_rating()!=null) serie.setAge_rating(dto.getAge_rating());
        if(dto.getPublication_year()!=null) serie.setPublication_year(dto.getPublication_year());
        if(dto.getEpisode()!=null) serie.setEpisode(dto.getEpisode());
        if(dto.getSeason()!=null) serie.setSeason(dto.getSeason());
        if(dto.getUser()!=null) serie.setUser(serie.getUser());
        repository.save(serie);
        return mapper.toDTO(serie);
    }

    public List<SerieDTO> findByUser(){
        Long id = userValidator.getCurrentId();
        List<Serie> series = repository.findByUser_Id(id);
        return series.stream().map(mapper::toDTO).toList();
    }

    public List<SerieDTO> findByUserId(Long id){
        userValidator.userIdValidator(id);
        User user = userRepository.findById(id).get();
        List<Serie> series = repository.findByUser_Id(id);
        return series.stream().map(mapper::toDTO).toList();
    }

    public List<SerieDTO> top10Series(){
        List<Serie> series = repository.findByTop10True();
        return series.stream().map(mapper::toDTO).toList();
    }

    public List<SerieDTO> findByCategory(Long id){
        Optional<List<Serie>> find_series = repository.findByCategories_id(id);
        if (find_series.isPresent()){
            List<Serie> series = find_series.get();
            return series.stream().map(mapper::toDTO).toList();
        }
        throw new CategoryNotFoundException("Categoria com id:"+id+" não encontrada");
    }

    public List<SerieDTO> findByStreaming(Long id){
        Optional<List<Serie>> find_series = repository.findByStreamings_id(id);
        if (find_series.isPresent()){
            List<Serie> series = find_series.get();
            return series.stream().map(mapper::toDTO).toList();
        }
        throw new StreamingNotFoundException("Streaming com id:"+id+" não encontrado");
    }

    public SerieDTO updateSerie(Long id, SerieDTO dto){
        validator.serieIdValidator(id);
        Serie serie = mapper.toEntity(dto);
        serie.setId(id);
        repository.save(serie);
        return mapper.toDTO(serie);
    }

    public Page<Serie> listSerie(int page, int items){
        return repository.findAll(PageRequest.of(page, items));
    }

    public List<SerieDTO> listAllSerie(){
        List<Serie> series = repository.findAll();
        return series.stream().map(mapper::toDTO).toList();
    }

    public void deleteSerie(Long id){
        validator.serieIdValidator(id);
        repository.deleteById(id);
    }

}
