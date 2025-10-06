package com.liaw.dev.cinemaflix.mapper;

import com.liaw.dev.cinemaflix.dto.UserDTO;
import com.liaw.dev.cinemaflix.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toEntity(UserDTO dto){
        return new User(
                dto.getId(),
                dto.getName(),
                dto.getEmail(),
                dto.getUsername(),
                dto.getPassword(),
                dto.getProfile(),
                dto.getMovies(),
                dto.getSeries()
        );
    }

    public UserDTO toDto(User user){
        return new UserDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getUsername(),
                user.getPassword(),
                user.getProfile(),
                user.getMovies(),
                user.getSeries()
        );
    }

}
