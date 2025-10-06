package com.liaw.dev.cinemaflix.service;

import com.liaw.dev.cinemaflix.dto.UserDTO;
import com.liaw.dev.cinemaflix.entity.Profile;
import com.liaw.dev.cinemaflix.entity.User;
import com.liaw.dev.cinemaflix.mapper.UserMapper;
import com.liaw.dev.cinemaflix.repository.ProfileRepository;
import com.liaw.dev.cinemaflix.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserMapper mapper;
    private final PasswordEncoder encoder;
    private final UserRepository repository;
    private final ProfileRepository profileRepository;

    public UserDTO createUser(UserDTO dto) {
        User user = mapper.toEntity(dto);
        user.setPassword(encoder.encode(user.getPassword()));
        Profile profile = new Profile();
        profile.setUser(user);
        profile.setName(dto.getName());
        profile.setUsername(dto.getUsername());
        repository.save(user);
        profileRepository.save(profile);
        return mapper.toDto(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException("Usuário ou senha inválidos"));
    }
}
