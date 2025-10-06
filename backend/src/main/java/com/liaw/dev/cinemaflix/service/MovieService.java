package com.liaw.dev.cinemaflix.service;

import com.liaw.dev.cinemaflix.dto.JwtUserData;
import com.liaw.dev.cinemaflix.dto.MovieDTO;
import com.liaw.dev.cinemaflix.entity.Category;
import com.liaw.dev.cinemaflix.entity.Movie;
import com.liaw.dev.cinemaflix.entity.Streaming;
import com.liaw.dev.cinemaflix.entity.User;
import com.liaw.dev.cinemaflix.handleException.categoryException.CategoryNotFoundException;
import com.liaw.dev.cinemaflix.handleException.movieException.MovieNotFoundException;
import com.liaw.dev.cinemaflix.handleException.streamingException.StreamingNotFoundException;
import com.liaw.dev.cinemaflix.mapper.MovieMapper;
import com.liaw.dev.cinemaflix.repository.MovieRepository;
import com.liaw.dev.cinemaflix.repository.UserRepository;
import com.liaw.dev.cinemaflix.validator.MovieValidator;
import com.liaw.dev.cinemaflix.validator.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieMapper mapper;
    private final MovieValidator validator;
    private final MovieRepository repository;
    private final UserValidator userValidator;
    private final UserRepository userRepository;


    @Transactional
    public MovieDTO createMovie(MovieDTO dto){
        Movie movie = mapper.toEntity(dto);
        validator.createMovieValidator(movie);
        Long id = userValidator.getCurrentId();
        User user = userRepository.findById(id).get();
        movie.setUser(user);
        repository.save(movie);
        return mapper.toDTO(movie);
    }

    public MovieDTO updateParcialMovie(Long id, MovieDTO dto){
        validator.movieIdValidator(id);
        Movie movie = repository.findById(id).get();
        if(dto.getUser()!=null) movie.setUser(dto.getUser());
        repository.save(movie);
        return mapper.toDTO(movie);
    }

    public List<MovieDTO> findByUser(){
        Long id = userValidator.getCurrentId();
        List<Movie> movies = repository.findByUser_Id(id);
        return movies.stream().map(mapper::toDTO).toList();
    }

    public List<MovieDTO> findByUserId(Long id){
        userValidator.userIdValidator(id);
        User user = userRepository.findById(id).get();
        List<Movie> movies = repository.findByUser_Id(user.getId());
        return movies.stream().map(mapper::toDTO).toList();
    }

    public Page<Movie> listMovie(int page, int items){
        return repository.findAll(PageRequest.of(page, items));

    }

    public List<MovieDTO> listAllMovie(){
        List<Movie> movies = repository.findAll();
        return movies.stream().map(mapper::toDTO).toList();
    }

    public List<MovieDTO> searchMovie(String title){
        List<Movie> movies = repository.findByTitleContainingIgnoreCase(title);
        return movies.stream().map(mapper::toDTO).toList();
    }

    public List<MovieDTO> top10Movies(){
        List<Movie> movies = repository.findByTop10True();
        return movies.stream().map(mapper::toDTO).toList();
    }

    public List<MovieDTO> findByCategory(Long id){
        Optional<List<Movie>> find_movie = repository.findByCategories_id(id);
        if (find_movie.isPresent()){
            List<Movie> movie = find_movie.get();
            return movie.stream().map(mapper::toDTO).toList();
        }
        throw new CategoryNotFoundException("Filme com categoria de id:"+id+" não encontrado");
    }

    public MovieDTO findById(Long id){
        validator.movieIdValidator(id);
        Movie movie = repository.findById(id).get();
        return mapper.toDTO(movie);

    }

    public MovieDTO addTop10(Long id){
        validator.movieIdValidator(id);
        validator.top10Validator();
        Movie movie = repository.findById(id).get();
        movie.setTop10(true);
        repository.save(movie);
        return mapper.toDTO(movie);
    }

    public MovieDTO removeTop10(Long id){
        validator.movieIdValidator(id);
        validator.top10Validator();
        Movie movie = repository.findById(id).get();
        movie.setTop10(false);
        repository.save(movie);
        return mapper.toDTO(movie);
    }

    public List<MovieDTO> findByStreaming(Long id){
        Optional<List<Movie>> find_movie = repository.findByStreamings_id(id);
        if (find_movie.isPresent()){
            List<Movie> movie = find_movie.get();
            return movie.stream().map(mapper::toDTO).toList();
        }
        throw new StreamingNotFoundException("Filme com streaming de id:"+id+" não encontrado");
    }

    public MovieDTO updateMovie(Long id, MovieDTO dto){
        validator.movieIdValidator(id);
        Movie movie = mapper.toEntity(dto);
        movie.setId(id);
        repository.save(movie);
        return mapper.toDTO(movie);
    }

    public void deleteMovie(Long id){
        validator.movieIdValidator(id);
        repository.deleteById(id);
    }

}
