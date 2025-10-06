package com.liaw.dev.cinemaflix.validator;

import com.liaw.dev.cinemaflix.entity.Movie;
import com.liaw.dev.cinemaflix.handleException.movieException.MovieExistException;
import com.liaw.dev.cinemaflix.handleException.movieException.MovieNotFoundException;
import com.liaw.dev.cinemaflix.handleException.movieException.MovieTop10Exception;
import com.liaw.dev.cinemaflix.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MovieValidator {

    private final MovieRepository repository;

    public void movieIdValidator(Long id){
        Optional<Movie> movie = repository.findById(id);
        if (movie.isEmpty()){
            throw new MovieNotFoundException("Filme com id: "+id+" não encontrado");
        }
    }

    public void createMovieValidator(Movie movie){
        Optional<Movie> title_find = repository.findByTitle(movie.getTitle());
        Optional<Movie> description_find = repository.findByDescription(movie.getDescription());
        if (title_find.isPresent()){
            throw new MovieExistException(
                    "Filme com título: "+movie.getTitle()+" já está cadastrado");
        } else if (description_find.isPresent()) {
            throw new MovieExistException(
                    "File com essa descrição já cadastrado"
            );
        }
    }

    public void top10Validator(){
        List<Movie> movies = repository.findByTop10True();
        if(movies.size()>=10) {
            throw new MovieTop10Exception("Já existe 10 filmes no top 10");
        }
    }

}
