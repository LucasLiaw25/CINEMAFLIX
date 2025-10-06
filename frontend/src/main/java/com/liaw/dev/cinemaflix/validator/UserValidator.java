package com.liaw.dev.cinemaflix.validator;

import com.liaw.dev.cinemaflix.dto.JwtUserData;
import com.liaw.dev.cinemaflix.entity.User;
import com.liaw.dev.cinemaflix.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserValidator {

    private final UserRepository repository;

    public void userIdValidator(Long id){
        Optional<User> find_user = repository.findById(id);
        if (find_user.isEmpty()){
            throw new UsernameNotFoundException("Usuario não encontrado");
        }
    }

    public Long getCurrentId(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof JwtUserData){
            return ((JwtUserData)principal).id();
        }else{
            throw new UsernameNotFoundException("Usuário não encontrado");
        }
    }

}
