package com.liaw.dev.cinemaflix.service;

import com.liaw.dev.cinemaflix.dto.*;
import com.liaw.dev.cinemaflix.entity.Movie;
import com.liaw.dev.cinemaflix.entity.Serie;
import com.liaw.dev.cinemaflix.entity.User;
import com.liaw.dev.cinemaflix.entity.UserList;
import com.liaw.dev.cinemaflix.mapper.MovieMapper;
import com.liaw.dev.cinemaflix.mapper.SerieMapper;
import com.liaw.dev.cinemaflix.mapper.UserListMapper;
import com.liaw.dev.cinemaflix.repository.MovieRepository;
import com.liaw.dev.cinemaflix.repository.SerieRepository;
import com.liaw.dev.cinemaflix.repository.UserListRepository;
import com.liaw.dev.cinemaflix.repository.UserRepository;
import com.liaw.dev.cinemaflix.validator.MovieValidator;
import com.liaw.dev.cinemaflix.validator.SerieValidator;
import com.liaw.dev.cinemaflix.validator.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserListService {

    private final UserValidator userValidator;
    private final SerieValidator serieValidator;
    private final MovieValidator movieValidator;
    private final UserRepository userRepository;
    private final MovieMapper movieMapper;
    private final SerieMapper serieMapper;
    private final MovieRepository movieRepository;
    private final SerieRepository serieRepository;
    private final UserListMapper mapper;
    private final UserListRepository userListRepository;

    public String getCurrentUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else if (principal instanceof JwtUserData) {
            return ((JwtUserData) principal).username();
        } else {
            return principal.toString();
        }
    }

    private User getAuthenticatedUser(){
        String username = getCurrentUsername();
        return userRepository.findByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException("Usuario não encontrado"));
    }

    public void addMovieToList(Long movieId){
        movieValidator.movieIdValidator(movieId);

        User user = getAuthenticatedUser();
        Movie movie = movieRepository.findById(movieId).get();

        userListRepository.findByUserAndMovie(user, movie)
                .ifPresentOrElse(
                        ul->{},
                        ()->{
                            UserList ul = new UserList();
                            ul.setUser(user);
                            ul.setMovie(movie);
                            userListRepository.save(ul);
                        }
                );

    }

    public void addSerieToList(Long serieId){
        serieValidator.serieIdValidator(serieId);
        User user = getAuthenticatedUser();

        Serie serie = serieRepository.findById(serieId).get();

        userListRepository.findByUserAndSerie(user, serie)
                .ifPresentOrElse(
                        ul->{},
                        ()->{
                            UserList ul = new UserList();
                            ul.setUser(user);
                            ul.setSerie(serie);
                            userListRepository.save(ul);
                        }
                );
    }

    public UserListResponse findByUserId(){
        Long id = userValidator.getCurrentId();
        List<UserList> list = userListRepository.findByUser_id(id);
        List<MovieDTO> movies = list.stream()
                .map(UserList::getMovie)
                .filter(Objects::nonNull)
                .map(movieMapper::toDTO)
                .toList();
        List<SerieDTO> series = list.stream()
                .map(UserList::getSerie)
                .filter(Objects::nonNull) // ignora os nulos
                .map(serieMapper::toDTO)
                .toList();

        return UserListResponse.builder()
                .movies(movies)
                .series(series)
                .build();
    }

    @Transactional
    public void removeMovieToList(Long movieId){
        User user = getAuthenticatedUser();
        movieValidator.movieIdValidator(movieId);

        Movie movie = movieRepository.findById(movieId).get();

        userListRepository.deleteByUserAndMovie(user, movie);
    }

    @Transactional
    public void removeSerieToList(Long serieId){
        serieValidator.serieIdValidator(serieId);
        User user = getAuthenticatedUser();

        Serie serie = serieRepository.findById(serieId).get();

        userListRepository.deleteByUserAndSerie(user, serie);
    }

    public List<UserListDTO> findUserList(){
        List<UserList> userLists = userListRepository.findAll();
        return userLists.stream().map(mapper::toDTO).toList();
    }

}
