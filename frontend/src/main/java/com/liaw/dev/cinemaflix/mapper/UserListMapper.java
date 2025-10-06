package com.liaw.dev.cinemaflix.mapper;

import com.liaw.dev.cinemaflix.dto.UserListDTO;
import com.liaw.dev.cinemaflix.entity.UserList;
import org.springframework.stereotype.Component;

@Component
public class UserListMapper {

    public UserListDTO toDTO(UserList list){
        return new UserListDTO(
                list.getId(),
                list.getUser(),
                list.getMovie(),
                list.getSerie(),
                list.getAddedAt()
        );
    }

    public UserList toEntity(UserListDTO dto){
        return new UserList(
                dto.getId(),
                dto.getUser(),
                dto.getMovie(),
                dto.getSerie(),
                dto.getAddedAt()
        );
    }

}
