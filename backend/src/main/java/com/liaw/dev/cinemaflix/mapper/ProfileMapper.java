package com.liaw.dev.cinemaflix.mapper;

import com.liaw.dev.cinemaflix.dto.ProfileDTO;
import com.liaw.dev.cinemaflix.entity.Profile;
import org.springframework.stereotype.Component;

@Component
public class ProfileMapper {

    public ProfileDTO toDTO(Profile profile){
        return new ProfileDTO(
                profile.getId(),
                profile.getBio(),
                profile.getName(),
                profile.getUsername(),
                profile.getLocation(),
                profile.getAge(),
                profile.getFavoriteMovie(),
                profile.getFavoriteSerie(),
                profile.getUser()
        );
    }

    public Profile toEntity(ProfileDTO dto){
        return new Profile(
                dto.getId(),
                dto.getBio(),
                dto.getName(),
                dto.getUsername(),
                dto.getLocation(),
                dto.getAge(),
                dto.getFavoriteMovie(),
                dto.getFavoriteSerie(),
                dto.getUser()
        );
    }

}
