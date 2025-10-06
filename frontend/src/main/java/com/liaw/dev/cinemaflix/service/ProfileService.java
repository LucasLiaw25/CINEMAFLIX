package com.liaw.dev.cinemaflix.service;

import com.liaw.dev.cinemaflix.dto.JwtUserData;
import com.liaw.dev.cinemaflix.dto.ProfileDTO;
import com.liaw.dev.cinemaflix.entity.Profile;
import com.liaw.dev.cinemaflix.entity.User;
import com.liaw.dev.cinemaflix.handleException.profileException.ProfileNotFoundException;
import com.liaw.dev.cinemaflix.mapper.ProfileMapper;
import com.liaw.dev.cinemaflix.repository.ProfileRepository;
import com.liaw.dev.cinemaflix.repository.UserRepository;
import com.liaw.dev.cinemaflix.validator.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileMapper mapper;
    private final ProfileRepository repository;
    private final UserRepository userRepository;
    private final UserValidator userValidator;

    private void profileIdValidator(Long id){
        Optional<Profile> find_profile = repository.findById(id);
        if (find_profile.isEmpty()){
            throw new ProfileNotFoundException("Perfil com id:"+id+" não encontrado");
        }
    }

    public ProfileDTO findById(Long id){
        userValidator.userIdValidator(id);
        Profile profile = repository.findByUser_id(id).get();
        return mapper.toDTO(profile);
    }

    public Profile getProfileForCurrentUser(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(()->new RuntimeException("Usuario não encontrado"));
        return repository.findByUser(user)
                .orElseThrow(()->new RuntimeException("Usuario não encontrado"));
    }

    public ProfileDTO updateProfile(ProfileDTO dto){
        Profile profile = repository.findByUser_id(userValidator.getCurrentId())
                .orElseThrow(()->new UsernameNotFoundException("Usuário não encontrado"));

        if(dto.getBio()!=null) profile.setBio(dto.getBio());
        if (dto.getName()!=null)profile.setName(dto.getName());
        if(dto.getUsername()!=null)profile.setUsername(dto.getUsername());
        if(dto.getLocation()!=null) profile.setLocation(dto.getLocation());
        if(dto.getAge()!=null)profile.setAge(dto.getAge());
        if(dto.getFavoriteMovie()!=null)profile.setFavoriteMovie(dto.getFavoriteMovie());
        if(dto.getFavoriteSerie()!=null)profile.setFavoriteSerie(dto.getFavoriteSerie());

        repository.save(profile);
        return mapper.toDTO(profile);
    }
    
    public ProfileDTO getProfile(){
        Long id = userValidator.getCurrentId();
        Profile profile = repository.findByUser_id(id)
                .orElseThrow(()->new UsernameNotFoundException("Usuário não encontrado"));
        return mapper.toDTO(profile);
    }

}
