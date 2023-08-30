package com.card.management.service;

import com.card.management.configuration.exception.BadRequestException;
import com.card.management.dto.CurrentUserDTO;
import com.card.management.entity.User;
import com.card.management.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service

public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
    private final UserRepository userRepository;
    private final Logger logger = LoggerFactory.getLogger(UserDetailsService.class);

    public UserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        final User user = userRepository.findUserByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Invalid login request"));

        final CurrentUserDTO currentUserDTO = new CurrentUserDTO(user.getEmail(), user.getPassword(), user.isEnabled(), true, true, true, getAuthorities(user));
        currentUserDTO.setUserType(user.getUserType());
        currentUserDTO.setId(user.getId());

        return currentUserDTO;
    }

    public Collection<? extends GrantedAuthority> getAuthorities(User user) {
        return List.of(new SimpleGrantedAuthority(String.valueOf(user.getUserType())));
    }

}
